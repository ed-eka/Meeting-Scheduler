package com.metrodata.clientapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.metrodata.clientapp.model.request.PersonRequest;
import com.metrodata.clientapp.model.request.RegistrationRequest;
import com.metrodata.clientapp.model.response.PersonResponse;

@Service
public class PersonService {
    private RestTemplate restTemplate;

    @Autowired
    public PersonService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Value("${app.baseUrl}/person")
    private String url;

    public List<PersonResponse> getAll(){
        return restTemplate
                .exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<PersonResponse>>() {
                        }).getBody();
    }

    public PersonResponse getById(long id){
        return restTemplate
                .exchange(
                        url + "/" + id, 
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PersonResponse>() {}).getBody();
    }

    public List<PersonResponse> getByVisibility(){
        return restTemplate
                .exchange(
                        url+"/visible",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<PersonResponse>>() {
                        }).getBody();
    }

    public PersonResponse create(PersonRequest personRequest){
        return restTemplate
                .exchange(
                        url,
                        HttpMethod.POST,
                        new HttpEntity<>(personRequest),
                        new ParameterizedTypeReference<PersonResponse>() {
                        }
                ).getBody();
    }

    public PersonResponse getByUsername(String username){
        return restTemplate
                .exchange(
                        url + "/" + username,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PersonResponse>() {
                        }
                ).getBody();
    }

    public PersonResponse update(long id, PersonRequest personRequest){
        return restTemplate
                .exchange(
                        url + "/" + id,
                        HttpMethod.PUT,
                        new HttpEntity(personRequest),
                        new ParameterizedTypeReference<PersonResponse>() {}).getBody();
    }

    public PersonResponse delete(long id){
        return restTemplate
                .exchange(
                        url + "/" + id,
                        HttpMethod.DELETE,
                        null,
                        new ParameterizedTypeReference<PersonResponse>() {}).getBody();
    }

    public Boolean registration(RegistrationRequest registrationRequest){
        try {
            ResponseEntity<PersonResponse> response = restTemplate.exchange(
                    url + "/registration",
                    HttpMethod.POST,
                    new HttpEntity<>(registrationRequest),
                    new ParameterizedTypeReference<PersonResponse>() {});
            if (response.getStatusCode() == HttpStatus.CREATED){
                return true;
            }
        } catch (Exception e){
            return false;
        }
        return false;
    }
}
