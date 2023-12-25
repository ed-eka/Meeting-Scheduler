package com.metrodata.serverapp.service;

import com.metrodata.serverapp.model.request.LoginRequest;
import com.metrodata.serverapp.model.response.LoginResponse;

public interface LoginService {
    LoginResponse login(LoginRequest loginRequest);
}