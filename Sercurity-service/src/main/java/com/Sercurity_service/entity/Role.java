package com.Sercurity_service.entity;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    int id;
    String role_name;
    Users user;
}
