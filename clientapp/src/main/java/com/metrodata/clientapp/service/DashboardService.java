package com.metrodata.clientapp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.metrodata.clientapp.model.response.MeetingResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DashboardService {
    private RestTemplate restTemplate;

    @Value("${app.baseUrl}")
    private String url;

    @Autowired
    public DashboardService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> getAllMeetingByPerson(long id){
        return restTemplate.exchange(
            url + "/meeting/participant/"+id,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Map<String, Object>>>(){
            }).getBody();
    }

    public MeetingResponse getByDateStart(long id){
        return restTemplate.exchange(
            url + "/meeting/date/"+id,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<MeetingResponse>(){}).getBody();
    }

    public List<Map<String, Object>> getByStatus(long id){
        return restTemplate.exchange(
            url + "/meeting/status/" + id,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Map<String,Object>>>(){}).getBody();
    }

    
}
