package com.metrodata.clientapp.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private long id;

    private String username;
    private String password;

    private PersonResponse personResponse;
}
