package com.Sercurity_service.service;

import com.Sercurity_service.repository.OtpRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class OtpService {
    @Autowired
    OtpRepository otpRepository;
}
