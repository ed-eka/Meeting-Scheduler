package com.metrodata.clientapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.metrodata.clientapp.model.request.MeetingRequest;
import com.metrodata.clientapp.model.response.MeetingResponse;

@Service
public class MeetingService {
    private final RestTemplate restTemplate;

    @Value("${app.baseUrl}/meeting")
    private String url;

    @Autowired
    public MeetingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<MeetingResponse> getAll(){
        return restTemplate
                .exchange(url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<MeetingResponse>>() {
                        }).getBody();
    }

    public List<MeetingResponse> getAllByPerson(long id){
        return restTemplate
                .exchange(url+"/person/"+id,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<MeetingResponse>>() {
                        }).getBody();
    }

    public MeetingResponse getById(long id){
        return restTemplate
                .exchange(url +"/"+ id,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<MeetingResponse>(){
                        }).getBody();
    }

    public MeetingResponse create(MeetingRequest meetingRequest){
        return restTemplate
                .exchange(url,
                        HttpMethod.POST,
                        new HttpEntity(meetingRequest),
                        new ParameterizedTypeReference<MeetingResponse>(){
                        }).getBody();
    }

    public MeetingResponse update(long id, MeetingRequest meetingRequest) {
        return restTemplate
                .exchange(url +"/"+ id,
                        HttpMethod.PUT,
                        new HttpEntity<>(meetingRequest),
                        new ParameterizedTypeReference<MeetingResponse>(){
                        }).getBody();
    }

    public Integer updateStatus(long id) {
        return restTemplate
                .exchange(url +"/status/"+ id,
                        HttpMethod.POST,
                        null,
                        new ParameterizedTypeReference<Integer>(){
                        }).getBody();
    }

    public MeetingResponse delete(long id){
        return restTemplate
                .exchange(url +"/"+ id,
                        HttpMethod.DELETE,
                        null,
                        new ParameterizedTypeReference<MeetingResponse>(){
                        }).getBody();
    }
}
