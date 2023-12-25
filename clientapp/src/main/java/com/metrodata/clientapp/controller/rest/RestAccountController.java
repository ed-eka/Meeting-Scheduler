package com.metrodata.clientapp.controller.rest;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metrodata.clientapp.model.request.AccountRequest;
import com.metrodata.clientapp.model.response.AccountResponse;
import com.metrodata.clientapp.service.AccountService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class RestAccountController {
    private AccountService accountService;

    @GetMapping
    public List<AccountResponse> getAll(){
        return accountService.getAll();
    }

    @GetMapping("/{id}")
    public AccountResponse getById(@PathVariable long id){
        return accountService.getById(id);
    }

    @PutMapping("/{id}")
    public AccountResponse update(@PathVariable long id, @RequestBody AccountRequest accountRequest){
        return accountService.update(id, accountRequest);
    }

    @DeleteMapping("/{id}")
    public AccountResponse delete(@PathVariable long id){
        return accountService.delete(id);
    }
}
