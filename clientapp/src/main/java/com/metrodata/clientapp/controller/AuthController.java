package com.metrodata.clientapp.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.metrodata.clientapp.model.request.ForgotPassword;
import com.metrodata.clientapp.model.request.LoginRequest;
import com.metrodata.clientapp.service.AuthService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @GetMapping("/login")
    public String loginView(LoginRequest loginRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "auth/login";
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/login")
    public String login(LoginRequest loginRequest) {
        if (!authService.login(loginRequest)) {
            return "redirect:/login?error=true";
        } 
        return "redirect:/dashboard";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordView(ForgotPassword forgotPassword) {
        return "auth/forgot-password";
    }

    // @PostMapping("/forgot-password")
    // public String forgotPassword(ForgotPassword forgotPassword) {

    // }
    
}
