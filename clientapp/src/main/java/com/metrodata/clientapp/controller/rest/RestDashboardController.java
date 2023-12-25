package com.metrodata.clientapp.controller.rest;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metrodata.clientapp.model.response.MeetingResponse;
import com.metrodata.clientapp.service.AuthService;
import com.metrodata.clientapp.service.DashboardService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/dashboard")
@AllArgsConstructor
@PreAuthorize("hasRole('USER')")
public class RestDashboardController {
    private DashboardService dashboardService;
    private AuthService authService;

    @GetMapping("/meeting/participant")
    @PreAuthorize("hasAuthority('READ_USER')")
    public List<Map<String, Object>> getAllMeetingByParticipant(){
        return dashboardService.getAllMeetingByPerson(authService.getId());
    }

    @GetMapping("/meeting/date")
    @PreAuthorize("hasAuthority('READ_USER')")
    public MeetingResponse getByDateStart(){
        return dashboardService.getByDateStart(authService.getId());
    }

    @GetMapping("/meeting/status")
    @PreAuthorize("hasAuthority('READ_USER')")
    public List<Map<String, Object>> getByStatus(){
        return dashboardService.getByStatus(authService.getId());
    }

}
