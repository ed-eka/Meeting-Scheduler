package com.metrodata.serverapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.metrodata.serverapp.model.request.InvitationRequest;
import com.metrodata.serverapp.model.response.InvitationResponse;
import com.metrodata.serverapp.service.InvitationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/meeting/invitation")
@AllArgsConstructor
public class InvitationController {
    private InvitationService invitationService;

    @GetMapping
    ResponseEntity<List<InvitationResponse>> getAll(){
        return new ResponseEntity<>(invitationService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<InvitationResponse> getById(@PathVariable long id){
        return new ResponseEntity<>(invitationService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<InvitationResponse> create(@RequestBody InvitationRequest invitationRequest){
        return new ResponseEntity<>(invitationService.create(invitationRequest), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<InvitationResponse> update(@PathVariable long id, @RequestBody InvitationRequest invitationRequest){
        return new ResponseEntity<>(invitationService.update(id, invitationRequest), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    ResponseEntity<InvitationResponse> delete(@PathVariable long id){
        return new ResponseEntity<>(invitationService.delete(id), HttpStatus.OK);
    }

    @GetMapping(path = "reject")
    public ResponseEntity<String> rejectInvite(@RequestParam("token") String rejectToken){
        return new ResponseEntity<>(invitationService.rejectInvite(rejectToken), HttpStatus.OK);
    }

    @GetMapping(path = "confirm")
    public ResponseEntity<String> confirmInvite(@RequestParam("token") String confirmToken){
        return new ResponseEntity<>(invitationService.confirmInvite(confirmToken), HttpStatus.OK);
    }
}