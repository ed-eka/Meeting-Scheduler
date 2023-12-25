package com.metrodata.serverapp.service;

import java.util.List;
import java.util.Map;

import com.metrodata.serverapp.model.request.MeetingRequest;
import com.metrodata.serverapp.model.response.MeetingResponse;

public interface MeetingService {
    List<MeetingResponse> getAll();
    List<MeetingResponse> getAllByPerson(long id);
    MeetingResponse getById(long id);
    List<Map<String, Object>> getScheduleByInvitation(long id);
    MeetingResponse getByDateStart(long id);
    List<Map<String, Object>> getAllMeetingByStatus(long id);
    int updateStatus(long id);
    
    MeetingResponse create(MeetingRequest meetingRequest);
    MeetingResponse update(long id, MeetingRequest meetingRequest);
    MeetingResponse delete(long id);
}
