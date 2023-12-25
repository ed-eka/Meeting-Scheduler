package com.metrodata.clientapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.metrodata.clientapp.model.response.RoomResponse;

@Service
public class RoomService {
    private final RestTemplate restTemplate;

    @Value("${app.baseUrl}/room")
    private String url;

    @Autowired
    public RoomService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RoomResponse> getAll(){
        return restTemplate
                .exchange(url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<RoomResponse>>() {
                        }).getBody();
    }
}
