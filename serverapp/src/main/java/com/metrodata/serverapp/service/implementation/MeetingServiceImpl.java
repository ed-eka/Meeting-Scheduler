package com.metrodata.serverapp.service.implementation;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.metrodata.serverapp.entity.Invitation;
import com.metrodata.serverapp.entity.Meeting;
import com.metrodata.serverapp.entity.Person;
import com.metrodata.serverapp.entity.Room;
import com.metrodata.serverapp.entity.Status;
import com.metrodata.serverapp.exception.CustomException;
import com.metrodata.serverapp.model.request.InvitationRequest;
import com.metrodata.serverapp.model.request.MeetingRequest;
import com.metrodata.serverapp.model.response.MeetingResponse;
import com.metrodata.serverapp.model.response.StatusResponse;
import com.metrodata.serverapp.repository.InvitationRepository;
import com.metrodata.serverapp.repository.MeetingRepository;
import com.metrodata.serverapp.repository.PersonRepository;
import com.metrodata.serverapp.repository.RoomRepository;
import com.metrodata.serverapp.repository.StatusRepository;
import com.metrodata.serverapp.service.MeetingService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class MeetingServiceImpl implements MeetingService{
    private MeetingRepository meetingRepository;
    private RoomRepository roomRepository;
    private StatusRepository statusRepository;
    private PersonRepository personRepository;
    private InvitationRepository invitationRepository;
    InvitationServiceImpl invitationServiceImpl;

    @Override
    public List<MeetingResponse> getAll() {
        return meetingRepository.findAll().stream().map(meeting -> {
            return mappingMeetingToMeetingResponse(meeting);
        }).collect(Collectors.toList());
    }

    @Override
    public List<MeetingResponse> getAllByPerson(long id) {
        return meetingRepository.getByPerson(id).stream().map(meeting -> {
            return mappingMeetingToMeetingResponse(meeting);
        }).collect(Collectors.toList());
    }
    

    @Override
    public MeetingResponse getById(long id) {
        Meeting meeting = meetingRepository.findById(id)
            .orElseThrow(() -> new CustomException(
                "Meeting with given id "+id+" not found",
                "MEETING_NOT_FOUND",
                404
            ));
        return mappingMeetingToMeetingResponse(meeting);
    }

    //Get meeting by participant
    @Override
    public List<Map<String, Object>> getScheduleByInvitation(long id){
        return invitationRepository.findAll().stream()
        .filter(inv -> inv.getParticipant().getId() == id && inv.getMeeting().getStatus().getId() != 4L)
        .map(i -> {
            final Map<String, Object> object = new HashMap<>();
            final MeetingResponse meetingResponse;
            meetingResponse = mappingMeetingToMeetingResponse(i.getMeeting());

            object.put("id", i.getId());
            object.put("meeting", meetingResponse);
            return object;
        }).collect(Collectors.toList());
    }

    //Get Person Meeting Schedule Group By Status
    @Override
    public List<Map<String, Object>> getAllMeetingByStatus(long id){
        List<Map<String, Object>> object = meetingRepository.countByStatus(id);

        return object;
    }

    //Get 1 Meeting Schedule
    @Override
    public MeetingResponse getByDateStart(long id){
        if(meetingRepository.getByDateStart(id) != null){
            return mappingMeetingToMeetingResponse(meetingRepository.getByDateStart(id));
        }
        return null;
    }

    @Override
    public MeetingResponse create(MeetingRequest meetingRequest) {
        Meeting meeting = meetingRepository.save(mappingMeetingRequestToMeeting(meetingRequest, new Meeting()));
        MeetingResponse response = mappingMeetingToMeetingResponse(meeting);
        
        InvitationRequest invitationRequest = new InvitationRequest();
        invitationRequest.setMeetingId(response.getId());
        long participantId = Long.parseLong(response.getInitiator().get("id").toString());
        invitationRequest.setParticipantId(participantId);
        invitationRequest.setStatusId(6);

        invitationRepository.save(mappingInvitationRequestToInvitation(invitationRequest, new Invitation()));
        return response;
    }

    @Override
    public MeetingResponse update(long id, MeetingRequest meetingRequest) {
        MeetingResponse oldData = getById(id);

        Meeting meeting = mappingMeetingResponseToMeeting(oldData);

        meeting = mappingMeetingRequestToMeeting(meetingRequest, meeting);

        meetingRepository.save(meeting);

        return mappingMeetingToMeetingResponse(meeting);
    }

    @Override
    public int updateStatus(long id){
        return meetingRepository.updateStatus(id);
    }

    @Override
    public MeetingResponse delete(long id) {
        MeetingResponse meeting = getById(id);
        invitationRepository.deleteByMeetingId(id);
        meetingRepository.deleteById(id);
        return meeting;
    }

    public MeetingResponse mappingMeetingToMeetingResponse(Meeting meeting){
        MeetingResponse meetingResponse = new MeetingResponse();
        BeanUtils.copyProperties(meeting, meetingResponse);

        //Date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd HH:mm:ss");
        meetingResponse.setDateTimeStart(meeting.getDateTimeStart().format(formatter));
        meetingResponse.setDateTimeEnd(meeting.getDateTimeEnd().format(formatter));

        //Set room if not null
        if(meeting.getVenue() != null){
            meetingResponse.setVenue(getVenue(meeting.getVenue()));
        }
        
        if(meeting.getVenue() == null && (meeting.getLink() != null || !meeting.getLink().equals(""))){
            meetingResponse.setIsOnline("Online");
        }else if(meeting.getVenue() != null && (meeting.getLink() == null || meeting.getLink().equals(""))){
            meetingResponse.setIsOnline("Offline");
        }else if(meeting.getVenue() != null && (meeting.getLink() != null || !meeting.getLink().equals(""))){
                meetingResponse.setIsOnline("Hybrid");
            }

        //Set initiator & status
        if(meeting.getInitiator() != null && meeting.getStatus() != null){
            StatusResponse statusResponse = new StatusResponse();
            
            meetingResponse.setInitiator(getInitiator(meeting.getInitiator()));

            BeanUtils.copyProperties(meeting.getStatus(), statusResponse);
            meetingResponse.setStatus(statusResponse);
        }
        
        //Set Invitation List
        if(meeting.getInvitations() != null){
            meetingResponse.setInvitations(getInvitation(meeting.getInvitations()));
        }

        return meetingResponse;
    }

    public Meeting mappingMeetingRequestToMeeting(MeetingRequest meetingRequest, Meeting meeting){
        log.info("Meeting Request : "+meetingRequest);

        BeanUtils.copyProperties(meetingRequest, meeting);

        //Date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-M-yyyy HH:mm:ss");
        meeting.setDateTimeStart(meetingRequest.getDateTimeStart());
        meeting.setDateTimeEnd(meetingRequest.getDateTimeEnd());

        //Set venue
        if(meetingRequest.getVenueId() == null){
            meeting.setVenue(null);
            // meeting.setOnline(true);
        }else{
            meeting.setVenue(roomRepository.findById(meetingRequest.getVenueId()).get());
        }

        //Set status id
        Status status = statusRepository.findById(meetingRequest.getStatusId()).get();

        //Set initiator id
        Person initiator = personRepository.findById(meetingRequest.getInitiatorId()).get();
        
        meeting.setStatus(status);
        meeting.setInitiator(initiator);

        return meeting;
    }

    public Meeting mappingMeetingResponseToMeeting(MeetingResponse meetingResponse){
        Meeting meeting = new Meeting();
        BeanUtils.copyProperties(meetingResponse, meeting);
        return meeting;
    }

    //Get room for meeting
    public Map<String, Object> getVenue(Room room) {
        Map<String, Object> object = new HashMap<>();
        object.put("id", room.getId());
        object.put("name", room.getName());
        return object;
    }

    //Get Initiator data
    public Map<String, Object> getInitiator(Person person) {
        Map<String, Object> object = new HashMap<>();
        object.put("id", person.getId());
        object.put("name", person.getName());
        return object;
    }

    //Get invitation list
    public List<Map<String, Object>> getInvitation(List<Invitation> invitations) {
        return invitations.stream().map(i -> {
                    final Map<String, Object> object = new HashMap<>();
                    object.put("id", i.getId());
                    object.put("participant_id", i.getParticipant().getId());
                    object.put("participant_name", i.getParticipant().getName());
                    object.put("status", i.getStatus().getName());
                    return object;
                }).collect(Collectors.toList());
    }

    //Save invitation for initiator
    public Invitation mappingInvitationRequestToInvitation(InvitationRequest invitationRequest, Invitation invitation){
        BeanUtils.copyProperties(invitationRequest, invitation);
        Meeting meeting = meetingRepository.findById(invitationRequest.getMeetingId()).get();
        Person participant = personRepository.findById(invitationRequest.getParticipantId()).get();
        Status status = statusRepository.findById(6L).get();
        invitation.setMeeting(meeting);
        invitation.setParticipant(participant);
        invitation.setStatus(status);
        return invitation;
    }
}
