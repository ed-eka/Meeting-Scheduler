package com.metrodata.serverapp.service;

import com.metrodata.serverapp.model.request.RegistrationRequest;
import com.metrodata.serverapp.model.response.PersonResponse;

public interface RegistrationService {
    PersonResponse registration(RegistrationRequest registrationRequest);
    String confirmToken(String token);
}