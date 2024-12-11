package com.Sercurity_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreationRequest {

    String username;
    String password;
    String email;
    String fullName;
    String phone;
    String address;
}
