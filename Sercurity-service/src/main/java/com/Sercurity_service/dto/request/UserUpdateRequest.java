package com.Sercurity_service.dto.request;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String username;
    String password;
    String email;
    String fullName;
    String phone;
    String address;
}
