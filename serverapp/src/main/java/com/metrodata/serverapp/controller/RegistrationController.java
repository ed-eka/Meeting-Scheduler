package com.metrodata.serverapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.metrodata.serverapp.model.request.RegistrationRequest;
import com.metrodata.serverapp.model.response.PersonResponse;
import com.metrodata.serverapp.service.RegistrationService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/person/registration")
public class RegistrationController {

    private RegistrationService registrationService;
    
    @PostMapping
    public ResponseEntity<PersonResponse> registration(@RequestBody RegistrationRequest registrationRequest) {
        return new ResponseEntity<>(registrationService.registration(registrationRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "confirm")
    public ResponseEntity<String> confirmAccount(@RequestParam("token") String token) {
        return new ResponseEntity<>(registrationService.confirmToken(token), HttpStatus.OK);
    }
}