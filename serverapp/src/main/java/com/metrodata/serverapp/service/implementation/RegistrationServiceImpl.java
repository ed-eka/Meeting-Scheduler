package com.metrodata.serverapp.service.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metrodata.serverapp.entity.Account;
import com.metrodata.serverapp.entity.Person;
import com.metrodata.serverapp.entity.Role;
import com.metrodata.serverapp.entity.VerificationToken;
import com.metrodata.serverapp.exception.CustomException;
import com.metrodata.serverapp.model.request.RegistrationRequest;
import com.metrodata.serverapp.model.response.AccountResponse;
import com.metrodata.serverapp.model.response.PersonResponse;
import com.metrodata.serverapp.repository.AccountRepository;
import com.metrodata.serverapp.repository.PersonRepository;
import com.metrodata.serverapp.repository.RoleRepository;
import com.metrodata.serverapp.service.RegistrationService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    private PersonRepository personRepository;
    private RoleRepository roleRepository;
    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenService verificationTokenService;
    
    @Override
    public PersonResponse registration(RegistrationRequest registrationRequest) {
        Optional<Person> findByEmailOrName = personRepository.findByEmailOrName(registrationRequest.getEmail(), registrationRequest.getName());

        if (findByEmailOrName.isPresent()) {
            Account getAccount = accountRepository.findById(findByEmailOrName.get().getId()).get();
            if (getAccount.isActive() == false) {
                verificationTokenService.setVerificationTokenAndSendToEmail(getAccount, registrationRequest);
                throw new CustomException(
                    "Person with email or name : " + registrationRequest + " is already exists, but not active",
                    "SEND_EMAIL_VERIFICATION_AGAIN",
                    400
                );
            } else {
                log.error("Email or Name is already exists : {}", registrationRequest);
                throw new CustomException(
                    "Person with email or name : " + registrationRequest + " is already exists",
                    "EMAIL_OR_NAME_ALREADY_EXISTS",
                    400
                );
            }
            
        }

        log.info("Registration request : {}" + registrationRequest);

        Person person = new Person();
        BeanUtils.copyProperties(registrationRequest, person);

        Account account = new Account();
        BeanUtils.copyProperties(registrationRequest, account);
        account.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        account.setPerson(person);

        if (registrationRequest.getRoleId() == 2) {
            List<Role> roles = new ArrayList<>();
            Role roleUser = roleRepository.findById(1L).get();
            Role roleAdmin = roleRepository.findById(2L).get();
            roles.add(roleUser);
            roles.add(roleAdmin);
            account.setRoles(roles);
        } else {
            List<Role> roles = new ArrayList<>();
            Role role = roleRepository.findById(registrationRequest.getRoleId()).get();
            roles.add(role);
            account.setRoles(roles);
        }

        person.setAccount(account);
        personRepository.save(person);

        verificationTokenService.setVerificationTokenAndSendToEmail(account, registrationRequest);

        return mappingPersonToPersonResponse(person);
    }

    
    @Override
    @Transactional
    public String confirmToken(String token) {
        VerificationToken verificationToken = verificationTokenService
                                    .getToken(token)
                                    .orElseThrow(() ->
                                        new CustomException(
                                            "Token not found!!!",
                                            "TOKEN_NOT_FOUND",
                                            404));
        
        if (verificationToken.getConfirmedDate() != null) {
            throw new CustomException(
                "Account is already confirmed",
                "ACCOUNT_ALREADY_CONFIRMED",
                400
            );
        }

        LocalDateTime expiredDate = verificationToken.getExpiredDate();

        if (expiredDate.isBefore(LocalDateTime.now())) {
            throw new CustomException(
                "Token expired",
                "TOKEN_EXPIRED",
                400
            );
        }

        verificationTokenService.setConfirmedDate(token);
        accountRepository.activatedAccount(verificationToken.getAccount().getUsername());

        return "confirmed";
    }


    public PersonResponse mappingPersonToPersonResponse(Person person) {
        PersonResponse personResponse = new PersonResponse();
        BeanUtils.copyProperties(person, personResponse);

        AccountResponse accountResponse = new AccountResponse();
        BeanUtils.copyProperties(person.getAccount(), accountResponse);
        personResponse.setAccount(accountResponse);

        return personResponse;
    }
    
}