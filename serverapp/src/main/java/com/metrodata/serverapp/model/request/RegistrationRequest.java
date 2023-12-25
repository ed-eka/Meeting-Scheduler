package com.metrodata.serverapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private String username;
    private String password;
    private long roleId;
}