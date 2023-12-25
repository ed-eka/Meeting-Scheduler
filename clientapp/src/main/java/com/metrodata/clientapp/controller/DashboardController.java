package com.metrodata.clientapp.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metrodata.clientapp.service.DashboardService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/dashboard")
@AllArgsConstructor
@PreAuthorize("hasRole('USER')")
public class DashboardController {
    private DashboardService dashboardService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public String index(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMM yyyy");
        model.addAttribute("dateNow", LocalDate.now().format(format));
        model.addAttribute("title", "Dashboard");

        if (authentication.getAuthorities().toString().contains("ROLE_ADMIN")) {
            model.addAttribute("role", "ADMIN");
            return "dashboard/dashboard-admin";
        }
        model.addAttribute("role", "USER");
        return "dashboard/dashboard-user";
    }
}
