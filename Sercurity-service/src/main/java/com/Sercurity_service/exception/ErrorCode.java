package com.Sercurity_service.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorCode {
    USER_NOT_FOUND(404, "User Not Found"),
    USER_ALREADY_EXISTS(409, "User Already Exists"),
    UNCAUGHT_EXCEPTION(9999, "Uncaught Exception"),
    USERNAME_INVALID(1003, "Username must be at least 6 characters"),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters"),
    ;

    int code;
    String message;
}
