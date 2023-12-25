package com.metrodata.serverapp.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse {
    private long id;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private boolean isEmployee;
    private boolean isVisible;
    private AccountResponse account;
}