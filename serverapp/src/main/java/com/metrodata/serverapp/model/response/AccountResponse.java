package com.metrodata.serverapp.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private long id;
    private String username;
    private String password;
    private boolean isActive;
}