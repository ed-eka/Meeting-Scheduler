package com.metrodata.serverapp.service;

import com.metrodata.serverapp.model.request.Email;

public interface EmailService {
    Email sendVerificationEmail(Email email);
    Email invitationMessage(Email email);
}