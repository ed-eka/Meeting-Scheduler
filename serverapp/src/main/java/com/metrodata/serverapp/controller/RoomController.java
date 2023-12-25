package com.metrodata.serverapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metrodata.serverapp.model.request.RoomRequest;
import com.metrodata.serverapp.model.response.RoomResponse;
import com.metrodata.serverapp.service.RoomService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/room")
@PreAuthorize("hasRole('USER')")
public class RoomController {
    private RoomService roomService;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<List<RoomResponse>> getAll(){
        return new ResponseEntity<>(roomService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/history")
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    public ResponseEntity<List<RoomResponse>> getAllHistory(){
        return new ResponseEntity<>(roomService.getAllMeetingInRoom(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<RoomResponse> getById(@PathVariable long id){
        return new ResponseEntity<>(roomService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    ResponseEntity<RoomResponse> create(@RequestBody RoomRequest roomRequest){
        return new ResponseEntity<>(roomService.create(roomRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    ResponseEntity<RoomResponse> update(@PathVariable long id, @RequestBody RoomRequest roomRequest){
        return new ResponseEntity<>(roomService.update(id, roomRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    ResponseEntity<RoomResponse> delete(@PathVariable long id){
        return new ResponseEntity<>(roomService.delete(id), HttpStatus.OK);
    }
}
