package com.Sercurity_service.controller;

import com.Sercurity_service.dto.request.ApiResponse;
import com.Sercurity_service.dto.request.AuthenticationRequest;
import com.Sercurity_service.dto.request.IntrospectRequest;
import com.Sercurity_service.dto.response.AuthenticationResponse;
import com.Sercurity_service.dto.response.IntrospectResponse;
import com.Sercurity_service.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticatie(@RequestBody AuthenticationRequest request) {
       AuthenticationResponse result = authenticationService.authenticate(request);
       ApiResponse<AuthenticationResponse> apiAuth = new ApiResponse<>();
       apiAuth.setResult(result);
       return apiAuth;
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        IntrospectResponse result = authenticationService.introspect(request);
        ApiResponse<IntrospectResponse> apiIntrospect = new ApiResponse<>();
        apiIntrospect.setResult(result);
        return apiIntrospect;
    }

}
