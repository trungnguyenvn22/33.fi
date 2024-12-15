package com.Sercurity_service.service;

import com.Sercurity_service.dto.request.AuthenticationRequest;
import com.Sercurity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthenticationService {
    UserRepository userRepository;

    boolean authenticate(AuthenticationRequest request){



        return false;
    };
}
