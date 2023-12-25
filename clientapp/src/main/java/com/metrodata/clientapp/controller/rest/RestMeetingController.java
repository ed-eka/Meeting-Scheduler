package com.metrodata.clientapp.controller.rest;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metrodata.clientapp.model.request.MeetingRequest;
import com.metrodata.clientapp.model.response.MeetingResponse;
import com.metrodata.clientapp.service.AuthService;
import com.metrodata.clientapp.service.MeetingService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("meeting/api/list")
@AllArgsConstructor
public class RestMeetingController {
    private MeetingService meetingService;
    private AuthService authService;

    @GetMapping
    public List<MeetingResponse> getAll(){
        return meetingService.getAll();
    }

    @GetMapping("/participant")
    public List<MeetingResponse> getByPerson(){
        System.out.println("Idparticp : "+authService.getId());
        return meetingService.getAllByPerson(authService.getId());
    }

    @GetMapping("/{id}")
    public MeetingResponse getById(@PathVariable long id){
        return meetingService.getById(id);
    }

    @PostMapping
    public MeetingResponse create(@RequestBody MeetingRequest meetingRequest){
        return meetingService.create(meetingRequest);
    }

    @PutMapping("/{id}")
    public MeetingResponse update(@PathVariable long id, @RequestBody MeetingRequest meetingRequest){
        return meetingService.update(id, meetingRequest);
    }

    @DeleteMapping("/{id}")
    public MeetingResponse delete(@PathVariable long id){
        return meetingService.delete(id);
    }

}
