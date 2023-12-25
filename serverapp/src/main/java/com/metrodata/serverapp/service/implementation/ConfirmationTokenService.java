package com.metrodata.serverapp.service.implementation;

import com.metrodata.serverapp.entity.ConfirmationToken;
import com.metrodata.serverapp.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token){
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedDate(String token){
        return confirmationTokenRepository.updateConfirmedDate(
                LocalDateTime.now(), token
        );
    }
}