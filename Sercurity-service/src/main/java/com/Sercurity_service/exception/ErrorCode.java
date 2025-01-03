package com.Sercurity_service.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorCode {
    USER_NOT_FOUND(404, "User Not Found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(409, "User Already Exists", HttpStatus.BAD_REQUEST),
    UNCAUGHT_EXCEPTION(9999, "Uncaught Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_INVALID(1003, "Username must be at least 6 characters",HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters",HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTS(1005, "User Not Exists",HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission",HttpStatus.FORBIDDEN),
    PERMISSION_ALREADY_EXISTS(410, "Permission already exists", HttpStatus.BAD_REQUEST),
    ROLE_ALREADY_EXISTS(411, "Role already exists", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(412, "Role not found", HttpStatus.NOT_FOUND),
    OTP_HAS_EXPIRED(413, "Otp has expired", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_FOUND(414, "Email not found",HttpStatus.NOT_FOUND),
    OTP_NOT_FOUND(414, "OTP not found",HttpStatus.NOT_FOUND)

    ;

    int code;
    String message;
    HttpStatusCode statusCode;
}
