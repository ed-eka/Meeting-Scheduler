package com.metrodata.serverapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metrodata.serverapp.model.request.AccountRequest;
import com.metrodata.serverapp.model.response.AccountResponse;
import com.metrodata.serverapp.service.AccountService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
//@PreAuthorize("permitAll()")
//@PreAuthorize("hasRole('ADMIN')")
public class AccountController{

    private AccountService accountService;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    public ResponseEntity<List<AccountResponse>> getAll(){
        return new ResponseEntity<>(accountService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<AccountResponse> getById(@PathVariable long id){
        return new ResponseEntity<>(accountService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<AccountResponse> getByUsername(@PathVariable String username){
        return new ResponseEntity<>(accountService.getByUsername(username), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    public ResponseEntity<AccountResponse> update(@PathVariable long id, @RequestBody AccountRequest accountRequest){
        return new ResponseEntity<>(accountService.update(id, accountRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    public ResponseEntity<AccountResponse> delete(@PathVariable long id){
        return new ResponseEntity<>(accountService.delete(id), HttpStatus.OK);
    }
}