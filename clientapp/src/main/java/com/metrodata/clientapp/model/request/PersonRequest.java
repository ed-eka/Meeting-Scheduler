package com.metrodata.clientapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonRequest {
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
}
