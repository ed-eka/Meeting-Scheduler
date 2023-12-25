package com.metrodata.serverapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.metrodata.serverapp.model.request.LoginRequest;
import com.metrodata.serverapp.model.response.LoginResponse;
import com.metrodata.serverapp.service.LoginService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class LoginController {
    
    private LoginService loginService;

    @PostMapping("/login")
    private ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(loginService.login(loginRequest), HttpStatus.OK);
    }

    
}