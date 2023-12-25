package com.metrodata.clientapp.controller;

import com.metrodata.clientapp.model.request.AccountRequest;
import com.metrodata.clientapp.model.request.PersonRequest;
import com.metrodata.clientapp.service.AccountService;
import com.metrodata.clientapp.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {
    private AccountService accountService;
    private PersonService personService;

    @GetMapping
    @PostAuthorize("hasAuthority('READ_ADMIN')")
    public String index(Model model){
        model.addAttribute("account", accountService.getAll());
        model.addAttribute("title", "person");
        return "account/index";
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasAuthority('READ_USER')")
    public String indexId(@PathVariable long id, Model model){
        model.addAttribute("person", accountService.getById(id));
        model.addAttribute("title", "account");
        return "account/{id}";
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable long id, Model model, AccountRequest accountRequest){
        model.addAttribute("account", accountService.getById(id));
        model.addAttribute("person", personService.getAll());
        return "account/update-form";
    }

    @PutMapping("/{id}")
    @PostAuthorize("hasAuthority('UPDATE_USER')")
    public String update(@PathVariable long id, AccountRequest accountRequest){
        accountService.update(id, accountRequest);
        return "redirect:/account/{id}";
    }

    @DeleteMapping("/{id}")
    @PostAuthorize("hasAuthority('DELETE_ADMIN')")
    public String delete(@PathVariable long id){
        accountService.delete(id);
        return "redirect:/account";
    }
}
