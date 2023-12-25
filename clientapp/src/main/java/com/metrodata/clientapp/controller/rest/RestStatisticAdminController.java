package com.metrodata.clientapp.controller.rest;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metrodata.clientapp.model.response.InvitationResponse;
import com.metrodata.clientapp.model.response.MeetingResponse;
import com.metrodata.clientapp.model.response.RoomResponse;
import com.metrodata.clientapp.service.StatisticService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("statistic/api")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class RestStatisticAdminController {
    private StatisticService statisticService;

    @GetMapping("/meeting")
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    public List<MeetingResponse> getAllMeeting(){
        return statisticService.getAllMeeting();
    }

    @GetMapping("/room")
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    public List<RoomResponse> getRoom(){
        return statisticService.getAllRoom();
    }

    @GetMapping("/invitation")
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    public List<InvitationResponse> getInvitation(){
        return statisticService.getAllInvitation();
    }
}
