package com.metrodata.serverapp.service.implementation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metrodata.serverapp.entity.ConfirmationToken;
import com.metrodata.serverapp.entity.Invitation;
import com.metrodata.serverapp.entity.Meeting;
import com.metrodata.serverapp.entity.Person;
import com.metrodata.serverapp.entity.Room;
import com.metrodata.serverapp.entity.Status;
import com.metrodata.serverapp.exception.CustomException;
import com.metrodata.serverapp.model.request.Email;
import com.metrodata.serverapp.model.request.InvitationRequest;
import com.metrodata.serverapp.model.response.AccountResponse;
import com.metrodata.serverapp.model.response.InvitationResponse;
import com.metrodata.serverapp.model.response.MeetingResponse;
import com.metrodata.serverapp.model.response.PersonResponse;
import com.metrodata.serverapp.model.response.RoomResponse;
import com.metrodata.serverapp.model.response.StatusResponse;
import com.metrodata.serverapp.repository.InvitationRepository;
import com.metrodata.serverapp.repository.MeetingRepository;
import com.metrodata.serverapp.repository.PersonRepository;
import com.metrodata.serverapp.repository.StatusRepository;
import com.metrodata.serverapp.service.EmailService;
import com.metrodata.serverapp.service.InvitationService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private InvitationRepository invitationRepository;
    private MeetingRepository meetingRepository;
    private PersonRepository personRepository;
    private StatusRepository statusRepository;
    private EmailService emailService;
    private ConfirmationTokenService confirmationTokenService;

    @Override
    public List<InvitationResponse> getAll() {
        return invitationRepository.findAll()
                .stream()
                .map(Invitation -> {
                    return mappingInvitationToInvitationResponse(Invitation);
                })
                .collect(Collectors.toList());
    }

    @Override
    public InvitationResponse getById(long id) {
        Invitation findInvitation = invitationRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        "Invitation with given id " + id + " not found",
                        "INVITATION_NOT_FOUND",
                        404
                ));
        log.info("getByID with given id : {} success", id);
        return mappingInvitationToInvitationResponse(findInvitation);
    }

    @Override
    public InvitationResponse create(InvitationRequest invitationRequest) {
        Invitation invitation = invitationRepository.save(mappingInvitationRequestToInvitation(invitationRequest, new Invitation()));
        log.info("Invitation Request: "+invitationRequest);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(token);
        confirmationToken.setCreatedDate(LocalDateTime.now());
        confirmationToken.setInvitation(invitation);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String linkConfirm = "http://localhost:8088/meeting/invitation/confirm?token=" + token;
        String linkReject = "http://localhost:8088/meeting/invitation/reject?token=" + token;

        Email email = new Email();
        email.setTo(invitation.getParticipant().getEmail());
        email.setSubject("Please verify your attendance");

        email.setName(invitation.getParticipant().getName());
        email.setTitle(invitation.getMeeting().getTitle());

        if (invitation.getMeeting().getLink() != null) {
            if (invitation.getMeeting().getVenue() != null) {
                email.setLocation(invitation.getMeeting().getLink());
                email.setLocation(invitation.getMeeting().getVenue().getName());
            } else {
                email.setLocation(invitation.getMeeting().getLink());
            }
        } else {
            email.setLocation(invitation.getMeeting().getVenue().getName());
        }

//        DateTimeFormatter formatterino = DateTimeFormatter.ofPattern("yyyy-M-dd HH:mm:ss");
//        email.setDateStart(invitation.getMeeting().getDateTimeStart().format(formatterino).toString());
//        email.setDateEnd(invitation.getMeeting().getDateTimeEnd().format(formatterino).toString());
        email.setDateStart(invitation.getMeeting().getDateTimeStart().toString());
        email.setDateEnd(invitation.getMeeting().getDateTimeEnd().toString());
        email.setInitiator(invitation.getMeeting().getInitiator().getEmail());
        
        List<Person> persons = invitation.getMeeting().getInvitations()
                            .stream()
                            .map(t -> t.getParticipant())
                            .collect(Collectors.toList());
        email.setParticipants(persons);

        email.setInitName(invitation.getMeeting().getInitiator().getName());
        email.setInitEmail(invitation.getMeeting().getInitiator().getEmail());
        email.setInitPhone(invitation.getMeeting().getInitiator().getPhoneNumber());
        email.setLinkConfirm(linkConfirm);
        email.setLinkReject(linkReject);

        emailService.invitationMessage(email);

        return  mappingInvitationToInvitationResponse(invitation);
    }

    @Override
    public InvitationResponse update(long id, InvitationRequest invitationRequest) {
        InvitationResponse oldInvitation = getById(id);

        Invitation updateInvitation = mappingInvitationResponseToInvitation(oldInvitation);

        updateInvitation = mappingInvitationRequestToInvitation(invitationRequest, updateInvitation);

        invitationRepository.save(updateInvitation);
        log.info("Update with given id: {} success", id);
        return mappingInvitationToInvitationResponse(updateInvitation);

    }

    @Override
    public InvitationResponse delete(long id) {
        InvitationResponse invitationResponse = getById(id);
        invitationRepository.deleteById(id);
        log.info("Delete with given id : {} success", id);
        return invitationResponse;
    }

    @Override
    @Transactional
    public String confirmInvite(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new CustomException(
                                "Token not found",
                                "TOKEN_NOT_FOUND",
                                404
                        ));
        if (confirmationToken.getConfirmDate() != null) {
            throw new CustomException(
                    "Already respond to this meeting",
                    "MEETING_ALREADY_CONFIRMED",
                    400
            );
        }

        confirmationTokenService.setConfirmedDate(token);

        // if (confirmationToken.getConfirmDate().isAfter(confirmationToken.getInvitation().getMeeting().getDateTimeStart().toInstant(ZoneOffset.ofHours(7)).atZone(ZoneId.systemDefault()).toLocalDateTime()) || confirmationToken.getConfirmDate().isEqual(confirmationToken.getInvitation().getMeeting().getDateTimeStart().toInstant(ZoneOffset.ofHours(7)).atZone(ZoneId.systemDefault()).toLocalDateTime())) {
        //     throw new CustomException(
        //             "The meeting already started or finished",
        //             "MEETING_STARTED_OR_FINISHED",
        //             400
        //     );
        // }

        invitationRepository.confirmInvite(confirmationToken.getInvitation().getParticipant().getId());

        return "Confirm Invitation";
    }

    @Override
    @Transactional
    public String rejectInvite(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new CustomException(
                                "Token not found",
                                "TOKEN_NOT_FOUND",
                                404
                        ));
        if (confirmationToken.getConfirmDate() != null) {
            throw new CustomException(
                    "Already respond to this meeting",
                    "MEETING_ALREADY_CONFIRMED",
                    400
            );
        }

        confirmationTokenService.setConfirmedDate(token);

        // if (confirmationToken.getConfirmDate().isAfter(confirmationToken.getInvitation().getMeeting().getDateTimeStart().toInstant(ZoneOffset.ofHours(7)).atZone(ZoneId.systemDefault()).toLocalDateTime()) || confirmationToken.getConfirmDate().isEqual(confirmationToken.getInvitation().getMeeting().getDateTimeStart().toInstant(ZoneOffset.ofHours(7)).atZone(ZoneId.systemDefault()).toLocalDateTime())) {
        //     throw new CustomException(
        //             "The meeting already started or finished",
        //             "MEETING_STARTED_OR_FINISHED",
        //             400
        //     );
        // }

        invitationRepository.rejectInvite(confirmationToken.getInvitation().getParticipant().getId());

        return "Reject Invitation";
    }

    public InvitationResponse mappingInvitationToInvitationResponse(Invitation invitation){
        InvitationResponse invitationResponse = new InvitationResponse();
        BeanUtils.copyProperties(invitation, invitationResponse);

        if (invitation.getMeeting() != null){
            MeetingResponse meetingResponse = new MeetingResponse();
            BeanUtils.copyProperties(invitation.getMeeting(), meetingResponse);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd HH:mm:ss");
            meetingResponse.setDateTimeStart(invitation.getMeeting().getDateTimeStart().format(formatter));
            meetingResponse.setDateTimeEnd(invitation.getMeeting().getDateTimeStart().format(formatter));
            if (invitation.getMeeting().getVenue() != null){
                RoomResponse roomResponse = new RoomResponse();
                BeanUtils.copyProperties(invitation.getMeeting().getVenue(), roomResponse);
                meetingResponse.setVenue(getVenue(invitation.getMeeting().getVenue()));
            }
            if (invitation.getMeeting().getLink() != null) {
                meetingResponse.setLink(invitation.getMeeting().getLink());
            }
            if (invitation.getMeeting().getStatus() != null){
                StatusResponse statusResponse = new StatusResponse();
                BeanUtils.copyProperties(invitation.getMeeting().getStatus(), statusResponse);
                meetingResponse.setStatus(statusResponse);
            }
            if (invitation.getMeeting().getInitiator() != null){
                meetingResponse.setInitiator(getInitiator(invitation.getMeeting().getInitiator()));
            }
            if (invitation.getMeeting().getInvitations() != null){
                meetingResponse.setInvitations(getInvitation(invitation.getMeeting().getInvitations()));
            }
            invitationResponse.setMeeting(meetingResponse);
        }

        if (invitation.getParticipant() != null){
            PersonResponse personResponse = new PersonResponse();
            BeanUtils.copyProperties(invitation.getParticipant(), personResponse);
            if (invitation.getParticipant().getAccount() != null) {
                AccountResponse accountResponse = new AccountResponse();
                BeanUtils.copyProperties(invitation.getParticipant().getAccount(), accountResponse);
                personResponse.setAccount(accountResponse);
            }
            invitationResponse.setParticipant(personResponse);
        }

        if (invitation.getStatus() != null){
            StatusResponse statusResponse = new StatusResponse();
            BeanUtils.copyProperties(invitation.getStatus(), statusResponse);
            invitationResponse.setStatus(statusResponse);
        }
        return invitationResponse;
    }

    public Map<String, Object> getVenue(Room room) {
        Map<String, Object> object = new HashMap<>();
        object.put("id", room.getId());
        object.put("name", room.getName());
        return object;
    }

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

    public Invitation mappingInvitationRequestToInvitation(InvitationRequest invitationRequest, Invitation invitation){
        BeanUtils.copyProperties(invitationRequest, invitation);
        Meeting meeting = meetingRepository.findById(invitationRequest.getMeetingId()).get();
        Person participant = personRepository.findById(invitationRequest.getParticipantId()).get();
        Status status = statusRepository.findById(5L).get();
        invitation.setMeeting(meeting);
        invitation.setParticipant(participant);
        invitation.setStatus(status);
        return invitation;
    }

    public Invitation mappingInvitationResponseToInvitation(InvitationResponse invitationResponse){
        Invitation invitation = new Invitation();
        BeanUtils.copyProperties(invitationResponse, invitation);
        return invitation;
    }

//  Using Map<K,V>
//  To Use: Comment out this one and comment in all of the code above
//  Then, comment out and in the respective service class
//    @Override
//    public List<Map<String, Object>> getAll2() {
//        return invitationRepository.findAll()
//                .stream()
//                .map(Invitation -> {
//                    final Map<String, Object> invitation = new HashMap<>();
//                    invitation.put("Id", Invitation.getId());
//                    invitation.put("Meeting", Invitation.getMeeting());
//                    invitation.put("Participant", Invitation.getParticipant());
//                    invitation.put("Status", Invitation.getStatus());
//                    return invitation;
//                })
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Map<String, Object> getById2(long id) {
//        Invitation findInvitation = invitationRepository.findById(id)
//                .orElseThrow(() -> new CustomException(
//                        "Invitation with given id " + id + " not found",
//                        "INVITATION_NOT_FOUND",
//                        404
//                ));
//        Map<String, Object> foundInvitation = new HashMap<>();
//        foundInvitation.put("Id", findInvitation.getId());
//        foundInvitation.put("Meeting", findInvitation.getMeeting());
//        foundInvitation.put("Participant", findInvitation.getParticipant());
//        foundInvitation.put("Status", findInvitation.getStatus());
//
//        return foundInvitation;
//    }
}