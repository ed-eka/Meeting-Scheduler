package com.metrodata.serverapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metrodata.serverapp.model.request.MeetingRequest;
import com.metrodata.serverapp.model.response.MeetingResponse;
import com.metrodata.serverapp.service.MeetingService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/meeting")
@PreAuthorize("hasRole('USER')")
public class MeetingController {
    private MeetingService meetingService;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<List<MeetingResponse>> getAll(){
        return new ResponseEntity<>(meetingService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/person/{id}")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<List<MeetingResponse>> getAllByPerson(@PathVariable long id){
        return new ResponseEntity<>(meetingService.getAllByPerson(id), HttpStatus.OK);
    }

    @GetMapping("/participant/{id}")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<List<Map<String, Object>>> getAllByInvitation(@PathVariable long id){
        return new ResponseEntity<>(meetingService.getScheduleByInvitation(id), HttpStatus.OK);
    }

    @GetMapping("/date/{id}")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<MeetingResponse> getByDateStart(@PathVariable long id){
        return new ResponseEntity<>(meetingService.getByDateStart(id), HttpStatus.OK);
    }

    @GetMapping("/status/{id}")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<List<Map<String, Object>>> getAllMeetingByStatus(@PathVariable long id){
        return new ResponseEntity<>(meetingService.getAllMeetingByStatus(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<MeetingResponse> getById(@PathVariable long id){
        return new ResponseEntity<>(meetingService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_USER')")
    public ResponseEntity<MeetingResponse> create(@RequestBody MeetingRequest meetingRequest){
        return new ResponseEntity<>(meetingService.create(meetingRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public ResponseEntity<MeetingResponse> update(@PathVariable long id, @RequestBody MeetingRequest meetingRequest){
        return new ResponseEntity<>(meetingService.update(id, meetingRequest), HttpStatus.OK);
    }

    @PostMapping("/status/{id}")
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public ResponseEntity<Integer> updateStatus(@PathVariable long id){
        return new ResponseEntity<>(meetingService.updateStatus(id), HttpStatus.OK);
    }
}
