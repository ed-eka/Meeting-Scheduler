package com.metrodata.serverapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.metrodata.serverapp.entity.Account;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@AllArgsConstructor
public class AppUserDetail implements UserDetails {

    private Account account;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        account.getRoles()
                    .forEach(roles -> {
                        String role = "ROLE_" + roles.getName().toUpperCase();
                        authorities.add(new SimpleGrantedAuthority(role));
                        roles.getPrivileges().forEach(privileges -> {
                            String privilege = privileges.getName().toUpperCase();
                            authorities.add(new SimpleGrantedAuthority(privilege));
                        });
                    });
        log.info("Authorisation : {}", authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return account.isActive();
    }
    
}