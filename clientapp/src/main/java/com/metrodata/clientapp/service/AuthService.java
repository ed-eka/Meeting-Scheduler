package com.metrodata.clientapp.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.metrodata.clientapp.model.request.LoginRequest;
import com.metrodata.clientapp.model.response.LoginResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {
    
    private RestTemplate restTemplate;

    @Value("${app.baseUrl}")
    private String url;

    private long idAccount;

    @Autowired
    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Boolean login(LoginRequest loginRequest) {
        try {
            ResponseEntity<LoginResponse> response = restTemplate.exchange(
                url + "/login", 
                HttpMethod.POST, 
                new HttpEntity<>(loginRequest),
                new ParameterizedTypeReference<LoginResponse>() {
                });
            
            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("Account info : {}", response.getBody());
                setPrinciple(response.getBody(), loginRequest.getPassword());
                setId(response.getBody().getId());
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    // public

    public void setPrinciple(LoginResponse response, String password) {
        Collection<SimpleGrantedAuthority> authorities = response.getAuthorities()
            .stream()
            .map(authorize -> new SimpleGrantedAuthority(authorize))
            .collect(Collectors.toList());
        
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            response.getUsername(), password, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public void setId(long id) {
        this.idAccount = id;
    }

    public long getId(){
        return idAccount;
    }
}
