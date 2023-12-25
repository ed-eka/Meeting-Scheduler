package com.metrodata.clientapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/statistic")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class StatisticAdminController {
    @GetMapping
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    public String index(Model model){
        model.addAttribute("title", "Statistic");
        return "statistic/statistic-admin";
    }
}
