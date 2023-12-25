package com.metrodata.clientapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.metrodata.clientapp.model.request.AccountRequest;
import com.metrodata.clientapp.model.response.AccountResponse;

@Service
public class AccountService {
    private RestTemplate restTemplate;

    @Autowired
    public AccountService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Value("${app.baseUrl}/account")
    private String url;

    public List<AccountResponse> getAll() {
        return restTemplate
                .exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<AccountResponse>>() {
                        }
                ).getBody();
    }

    public AccountResponse getById(long id){
        return restTemplate
                .exchange(
                        url + "/" + id,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<AccountResponse>() {
                        }
                ).getBody();
    }

    public AccountResponse getByUsername(String username){
        return restTemplate
                .exchange(
                        url + "/" + username,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<AccountResponse>() {
                        }
                ).getBody();
    }

    public AccountResponse update(long id, AccountRequest accountRequest){
        return restTemplate
                .exchange(
                        url + "/" + id,
                        HttpMethod.PUT,
                        new HttpEntity(accountRequest),
                        new ParameterizedTypeReference<AccountResponse>() {
                        }
                ).getBody();
    }

    public AccountResponse delete(long id){
        return restTemplate
                .exchange(
                        url + "/" + id,
                        HttpMethod.DELETE,
                        null,
                        new ParameterizedTypeReference<AccountResponse>() {
                        }
                ).getBody();
    }
}