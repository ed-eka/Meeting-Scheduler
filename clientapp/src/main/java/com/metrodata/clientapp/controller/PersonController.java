package com.metrodata.clientapp.controller;

import com.metrodata.clientapp.model.request.PersonRequest;
import com.metrodata.clientapp.model.request.ProfileRequest;
import com.metrodata.clientapp.model.request.RegistrationRequest;
import com.metrodata.clientapp.model.response.PersonResponse;
import com.metrodata.clientapp.service.AccountService;
import com.metrodata.clientapp.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
public class PersonController {
    private PersonService personService;
    private AccountService accountService;

//    @GetMapping("/registration")
//    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
//    public String registrationView(RegistrationRequest registrationRequest, Model model){
//        model.addAttribute("registration", personService.registration())
//        return "redirect:/registration";
//    }

    @PostMapping("/registration")
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    public String registration(RegistrationRequest registrationRequest){
        if (!personService.registration(registrationRequest)){
            return "redirect:/registration?error=true";
        }
        return "redirect:/profile";
    }

    @GetMapping
    @PostAuthorize("hasAuthority('READ_ADMIN')")
    public String index(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().toString().contains("ROLE_ADMIN")){
            String username = auth.getPrincipal().toString();
            return "profile/" + username;
        }

        model.addAttribute("profile", personService.getAll());
        model.addAttribute("title", "Profile");

        return "profile/index";
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasAuthority('READ_USER')")
    public String indexId(@PathVariable long id, Model model){
        model.addAttribute("profile", personService.getById(id));
        model.addAttribute("title", "Profile");
        return "profile/{id}";
    }



    @GetMapping("/{username}")
    public String indexname(@PathVariable String username, Model model){
        model.addAttribute("profile", personService.getById(accountService.getByUsername(username).getId()));
        model.addAttribute("title", "Profile");
        return "profile/{username}";
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable long id, Model model, PersonRequest personRequest){
        model.addAttribute("profile", personService.getById(id));
        model.addAttribute("accounts", accountService.getAll());
        return "profile/update-form";
    }

    @PutMapping("/{id}")
    @PostAuthorize("hasAuthority('UPDATE_USER')")
    public String update(@PathVariable long id, PersonRequest personRequest){
        personService.update(id, personRequest);
        return "redirect:/profile/{id}";
    }

//    @GetMapping("/update/{id}")
//    public String updateView2(@PathVariable long id, Model model, ProfileRequest profileRequest){
//        model.addAttribute("profile", personService.getById(id));
//        model.addAttribute("accounts", accountService.getById(id));
//        return "profile/update-form";
//    }
//
//    @PutMapping("/{id}")
//    @PostAuthorize("hasAuthority('UPDATE_USER')")
//    public String update2(@PathVariable long id, ProfileRequest profileRequest){
//        personService.update(id, profileRequest);
//        return "redirect:/profile/{id}";
//    }

    @DeleteMapping("/{id}")
    @PostAuthorize("hasAuthority('DELETE_ADMIN')")
    public String delete(@PathVariable long id){
        personService.delete(id);
        return "redirect:/profile";
    }


}
