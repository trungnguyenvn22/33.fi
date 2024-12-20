package com.Sercurity_service.service;

import com.Sercurity_service.dto.request.AuthenticationRequest;
import com.Sercurity_service.dto.request.IntrospectRequest;
import com.Sercurity_service.dto.response.AuthenticationResponse;
import com.Sercurity_service.dto.response.IntrospectResponse;
import com.Sercurity_service.entity.Users;
import com.Sercurity_service.enums.Role;
import com.Sercurity_service.exception.AppException;
import com.Sercurity_service.exception.ErrorCode;
import com.Sercurity_service.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Signature;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    @Autowired
    UserRepository userRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        AuthenticationResponse response = new AuthenticationResponse();
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTS));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(user);
        response.setToken(token);
        response.setAuthenticated(authenticated);
        return response;

    };

    // Tạo token
    private String generateToken(Users users){
        //tạo header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        // (data trong body gọi là claim)
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(users.getUsername())
                .issuer("oxChun21.fi")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(29, ChronoUnit.DAYS).toEpochMilli()
                ))
                .claim("scope",buildScope(users))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        // jws cần header và payload
        JWSObject jwsObject = new JWSObject(header, payload);

        //  kí token
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Can't not create token");
            throw new RuntimeException(e);
        }

    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {

        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);
        Date exp = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        IntrospectResponse response = new IntrospectResponse();
        response.setToken_valid(verified && exp.after(new Date()));
        return response;
    };

    private String buildScope(Users users){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!users.getRoles().isEmpty()){
            for (String role : users.getRoles()){
                stringJoiner.add(role);
            }
        }
        return stringJoiner.toString();
    }
}
