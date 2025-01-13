package com.Sercurity_service.dto.request;

import com.Sercurity_service.validate.PasswordConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    @NotNull
    String username;
    @PasswordConstraint(
       minLength = 8,
       requireLowercase = true,
       requireUppercase = true,
       requireNumber = true,
       requireSpecialChar = true,
       message = "Password out scope exception"

    )
    String password;


}
