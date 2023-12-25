package com.metrodata.serverapp.service.implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.metrodata.serverapp.entity.Meeting;
import com.metrodata.serverapp.entity.Room;
import com.metrodata.serverapp.exception.CustomException;
import com.metrodata.serverapp.model.request.RoomRequest;
import com.metrodata.serverapp.model.response.RoomResponse;
import com.metrodata.serverapp.repository.RoomRepository;
import com.metrodata.serverapp.service.RoomService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository;

    @Override
    public List<RoomResponse> getAll() {
        return roomRepository.findAll().stream().map(r -> {
            return mappingRoomToRoomResponse(r);
        }).collect(Collectors.toList());
    }

    @Override
    public List<RoomResponse> getAllMeetingInRoom(){
        return roomRepository.findAll().stream().map(r -> {
            return mappingRoomToRoomResponseHistory(r);
        }).collect(Collectors.toList());
    }

    @Override
    public RoomResponse getById(long id) {
        Room room = roomRepository.findById(id)
        .orElseThrow(() -> new CustomException(
            "Room with given id "+id+" not found!",
            "ROOM_NOT_FOUND",
            404
        ));
        return mappingRoomToRoomResponse(room);
    }
    
    @Override
    public RoomResponse create(RoomRequest roomRequest) {
        if(roomRepository.existsByName(roomRequest.getName())){
            throw new CustomException(
                "Room with given name already exists",
                "ROOM_NAME_EXIST",
                400);
        }
        Room room = roomRepository.save(mappingRoomRequestToRoom(roomRequest, new Room()));
        return mappingRoomToRoomResponse(room);
    }

    @Override
    public RoomResponse update(long id, RoomRequest roomRequest) {
        RoomResponse oldData = getById(id);

        Room room = mappingRoomResponseToRoom(oldData);
        room = mappingRoomRequestToRoom(roomRequest, room);

        roomRepository.save(room);
        return mappingRoomToRoomResponse(room);
    }

    @Override
    public RoomResponse delete(long id) {
        RoomResponse roomResponse = getById(id);
        roomRepository.deleteById(id);
        return roomResponse;
    }

    private RoomResponse mappingRoomToRoomResponse(Room room){
        RoomResponse roomResponse = new RoomResponse();
        roomResponse.setId(room.getId());
        roomResponse.setName(room.getName());

        // Get all meeting list
        if(room.getMeeting() != null){
            roomResponse.setMeeting(getMeeting(room.getMeeting()));
        }
        
        return roomResponse;
    }

    private RoomResponse mappingRoomToRoomResponseHistory(Room room){
        RoomResponse roomResponse = new RoomResponse();
        roomResponse.setId(room.getId());
        roomResponse.setName(room.getName());

        // Get all meeting list
        if(room.getMeeting() != null){
            roomResponse.setMeeting(getAllMeeting(room.getMeeting()));
        }
        
        return roomResponse;
    }

    private Room mappingRoomRequestToRoom(RoomRequest roomRequest, Room room){
        BeanUtils.copyProperties(roomRequest, room);
        return room;
    }

    private Room mappingRoomResponseToRoom(RoomResponse roomResponse){
        Room room = new Room();
        BeanUtils.copyProperties(roomResponse, room);
        return room;
    }
    
    //Get Meeting List
    public List<Map<String, Object>> getMeeting(List<Meeting> meeting) {
        return meeting.stream().filter(m -> m.getStatus().getId() == 1).map(i -> {
                    final Map<String, Object> object = new HashMap<>();
                    object.put("id", i.getId());
                    object.put("title", i.getTitle());
                    object.put("description", i.getDescription());
                    object.put("status", i.getStatus().getName());
                    return object;
                }).collect(Collectors.toList());
    }

    //Get All Meeting History
    public List<Map<String, Object>> getAllMeeting(List<Meeting> meeting) {
        return meeting.stream().filter(m -> m.getStatus().getId() != 4).map(i -> {
                    final Map<String, Object> object = new HashMap<>();
                    object.put("id", i.getId());
                    object.put("title", i.getTitle());
                    object.put("description", i.getDescription());
                    object.put("status", i.getStatus().getName());
                    return object;
                }).collect(Collectors.toList());
    }
}
