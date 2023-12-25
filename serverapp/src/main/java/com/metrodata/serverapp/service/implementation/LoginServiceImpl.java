package com.metrodata.serverapp.service.implementation;

import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.metrodata.serverapp.entity.Account;
import com.metrodata.serverapp.model.request.LoginRequest;
import com.metrodata.serverapp.model.response.LoginResponse;
import com.metrodata.serverapp.repository.AccountRepository;
import com.metrodata.serverapp.service.LoginService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private AuthenticationManager authenticationManager;
    private AppUserDetailService appAccountDetailService;
    private AccountRepository accountRepository;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = appAccountDetailService.loadUserByUsername(loginRequest.getUsername());
        Account account = accountRepository.findByUsernameOrPerson_Email(loginRequest.getUsername(), loginRequest.getUsername()).get();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(account.getId());
        loginResponse.setUsername(userDetails.getUsername());
        loginResponse.setEmail(account.getPerson().getEmail());
        loginResponse.setAuthorities(
                userDetails.getAuthorities()
                        .stream()
                        .map(authority -> authority.getAuthority())
                        .collect(Collectors.toList())
        );
        return loginResponse;
    }
}