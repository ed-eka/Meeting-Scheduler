package com.metrodata.clientapp.controller.rest;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metrodata.clientapp.model.request.PersonRequest;
import com.metrodata.clientapp.model.response.PersonResponse;
import com.metrodata.clientapp.model.response.ProfileResponse;

import com.metrodata.clientapp.service.AccountService;
import com.metrodata.clientapp.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@RestController
@RequestMapping("/api/person")
@AllArgsConstructor
public class RestProfileController {
   private PersonService personService;
   private AccountService accountService;

   @PostMapping
    public PersonResponse create(@RequestBody PersonRequest personRequest){
        return personService.create(personRequest);
    }
}

//@RestController
//@RequestMapping("/api/profile")
//@AllArgsConstructor
//public class RestProfileController {
//    private PersonService personService;
//    private AccountService accountService;
//
////    @GetMapping
////    public List<ProfileResponse> getAll(){
////        return accountService.get
////    }
//}
