package com.metrodata.serverapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequest {
   private String name;
   private String email;
   private String address;
   private String phoneNumber;
}