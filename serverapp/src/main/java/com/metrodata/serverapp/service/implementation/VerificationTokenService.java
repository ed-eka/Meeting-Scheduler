package com.metrodata.serverapp.service.implementation;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.metrodata.serverapp.entity.Account;
import com.metrodata.serverapp.entity.VerificationToken;
import com.metrodata.serverapp.model.request.Email;
import com.metrodata.serverapp.model.request.RegistrationRequest;
import com.metrodata.serverapp.repository.VerificationTokenRepository;
import com.metrodata.serverapp.service.EmailService;

@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private EmailService emailService;

    @Value("${app.baseUrl}")
    private String url;

    public void setVerificationTokenAndSendToEmail(Account account, RegistrationRequest registrationRequest) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setCreatedDate(LocalDateTime.now());
        verificationToken.setExpiredDate(LocalDateTime.now().plusMinutes(30));
        verificationToken.setAccount(account);

        saveVerificationToken(verificationToken);

        String linkToken = url + "/person/registration/confirm?token=" + token;

        Email email = new Email();
        email.setName(registrationRequest.getName());
        email.setTo(registrationRequest.getEmail());
        email.setAttachment(linkToken);
        email.setSubject("Please verify your Account!!!");
        emailService.sendVerificationEmail(email);
    }

    public void saveVerificationToken(VerificationToken token) {
        verificationTokenRepository.save(token);
    }

    public Optional<VerificationToken> getToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    public int setConfirmedDate(String token) {
        return verificationTokenRepository.updateConfirmedDate( 
            LocalDateTime.now(),
            token
        );
    }
}