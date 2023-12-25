package com.metrodata.clientapp.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {
    private long id;

    private String username;
    private String password;

    private String name;
    private String email;
    private String address;
    private String phoneNumber;
}
