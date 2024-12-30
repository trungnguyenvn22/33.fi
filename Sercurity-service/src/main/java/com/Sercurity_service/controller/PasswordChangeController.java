package com.Sercurity_service.controller;

import com.Sercurity_service.dto.request.ApiResponse;
import com.Sercurity_service.dto.request.ChangePasswordRequest;
import com.Sercurity_service.dto.response.OtpResponse;
import com.Sercurity_service.exception.AppException;
import com.Sercurity_service.exception.ErrorCode;
import com.Sercurity_service.repository.OtpRepository;
import com.Sercurity_service.repository.UserRepository;
import com.Sercurity_service.service.OtpService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordChangeController {
    @Autowired
    OtpService otpService;

    @PostMapping("/otp/generate/{email}")

    public ApiResponse<OtpResponse> generateOtp(@PathVariable String email){
        ApiResponse<OtpResponse> response = new ApiResponse<>();
        response.setResult(otpService.generateOtp(email));
        return response;
    }


}
