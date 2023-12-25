package com.metrodata.serverapp.service.implementation;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.metrodata.serverapp.entity.Account;
import com.metrodata.serverapp.exception.CustomException;
import com.metrodata.serverapp.model.AppUserDetail;
import com.metrodata.serverapp.repository.AccountRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AppUserDetailService implements UserDetailsService{

    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsernameOrPerson_Email(username, username)
                .orElseThrow(() -> new CustomException(
                        "Username or Password incorrect",
                        "INVALID_USERNAME_PASSWORD",
                        400
                ));
        return new AppUserDetail(account);
    }
}