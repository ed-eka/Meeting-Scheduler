package com.metrodata.serverapp.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private long id;
    private String username;
    private String email;
    private List<String> authorities;
}