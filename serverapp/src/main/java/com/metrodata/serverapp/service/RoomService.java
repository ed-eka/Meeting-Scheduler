package com.metrodata.serverapp.service;

import java.util.List;

import com.metrodata.serverapp.model.request.RoomRequest;
import com.metrodata.serverapp.model.response.RoomResponse;

public interface RoomService {
    List<RoomResponse> getAll();
    List<RoomResponse> getAllMeetingInRoom();
    RoomResponse getById(long id);
    RoomResponse create(RoomRequest roomRequest);
    RoomResponse update(long id, RoomRequest roomRequest);
    RoomResponse delete(long id);
}
