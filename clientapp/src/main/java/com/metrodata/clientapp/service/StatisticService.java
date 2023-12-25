package com.metrodata.clientapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.metrodata.clientapp.model.response.InvitationResponse;
import com.metrodata.clientapp.model.response.MeetingResponse;
import com.metrodata.clientapp.model.response.RoomResponse;


@Service
public class StatisticService {
    private RestTemplate restTemplate;

    @Value("${app.baseUrl}")
    private String url;

    @Autowired
    public StatisticService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<MeetingResponse> getAllMeeting(){
        return restTemplate.exchange(
            url + "/meeting",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<MeetingResponse>>(){}
        ).getBody();
    }
    
    public List<RoomResponse> getAllRoom(){
        return restTemplate.exchange(
            url + "/room/history",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<RoomResponse>>(){}
        ).getBody();
    }

    public List<InvitationResponse> getAllInvitation(){
        return restTemplate.exchange(
            url + "/meeting/invitation",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<InvitationResponse>>(){}
        ).getBody();
    }
}
