package com.metrodata.serverapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metrodata.serverapp.model.request.PersonRequest;
import com.metrodata.serverapp.model.response.PersonResponse;
import com.metrodata.serverapp.service.PersonService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
//@PreAuthorize("permitAll()")
//@PreAuthorize("hasRole('USER')")
public class PersonController {

   private PersonService personService;

    @GetMapping
    // @PreAuthorize("hasAuthority('READ_ADMIN')")
    public ResponseEntity<List<PersonResponse>> getAll(){
        return new ResponseEntity<>(personService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/visible")
    public ResponseEntity<List<PersonResponse>> getByVisibility(){
        return new ResponseEntity<>(personService.getByVisibility(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<PersonResponse> getById(@PathVariable long id){
        return new ResponseEntity<>(personService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_USER')")
    public ResponseEntity<PersonResponse> create(@RequestBody PersonRequest personRequest){
        return new ResponseEntity<>(personService.create(personRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public ResponseEntity<PersonResponse> update(@PathVariable long id, @RequestBody PersonRequest personRequest){
        return new ResponseEntity<>(personService.update(id, personRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    public ResponseEntity<PersonResponse> delete(@PathVariable long id){
        return new ResponseEntity<>(personService.delete(id), HttpStatus.OK);
    }
}