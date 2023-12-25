package com.metrodata.clientapp.controller.rest;

import com.metrodata.clientapp.model.request.PersonRequest;
import com.metrodata.clientapp.model.request.RegistrationRequest;
import com.metrodata.clientapp.model.response.PersonResponse;
import com.metrodata.clientapp.service.AccountService;
import com.metrodata.clientapp.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@AllArgsConstructor
public class RestPersonController {
    private PersonService personService;
    private AccountService accountService;

    @GetMapping
    public List<PersonResponse> getAll(){
        return personService.getAll();
    }

    @GetMapping("/{id}")
    public PersonResponse getById(@PathVariable long id){
        return personService.getById(id);
    }

    @GetMapping("/{username}")
    public PersonResponse getByUsername(@PathVariable String username){
        long id = accountService.getByUsername(username).getId();
        return personService.getById(id);
    }

    @PutMapping("/{id}")
    public PersonResponse update(@PathVariable long id, @RequestBody PersonRequest personRequest){
        return personService.update(id, personRequest);
    }

    @DeleteMapping("/{id}")
    public PersonResponse delete(@PathVariable long id){
        return personService.delete(id);
    }

    @PostMapping("/registration")
    public String registration(@RequestBody RegistrationRequest registrationRequest){
        if (!personService.registration(registrationRequest)) {
            return "redirect:/registration?error=true";
        }
        return "/profile";
    }


}
