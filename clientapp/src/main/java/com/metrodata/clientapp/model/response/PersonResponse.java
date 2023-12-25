package com.metrodata.clientapp.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponse {
    private long id;
    private String name;
    private String email;
    private String address;
    private String phoneNUmber;

    private AccountResponse account;
}
