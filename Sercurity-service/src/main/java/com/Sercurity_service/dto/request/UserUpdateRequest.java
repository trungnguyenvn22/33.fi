package com.Sercurity_service.dto.request;


import com.Sercurity_service.entity.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String id;
    String username;
    String email;
    String fullName;
    String phone;
    String address;
    List<String> roles;
}
