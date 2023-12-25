package com.metrodata.serverapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metrodata.serverapp.model.request.Email;
import com.metrodata.serverapp.service.EmailService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/email")
@AllArgsConstructor
public class EmailController {

    private EmailService emailService;

    @PostMapping("/invitation")
    public ResponseEntity<Email> invitationMessage(@RequestBody Email emailRequest){
        return new ResponseEntity<>(emailService.invitationMessage(emailRequest), HttpStatus.OK);
    }

}