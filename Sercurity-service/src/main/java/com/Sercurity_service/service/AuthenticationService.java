package com.Sercurity_service.service;

import com.Sercurity_service.dto.request.AuthenticationRequest;
import com.Sercurity_service.dto.request.IntrospectRequest;
import com.Sercurity_service.dto.request.LogoutRequest;
import com.Sercurity_service.dto.response.AuthenticationResponse;
import com.Sercurity_service.dto.response.IntrospectResponse;
import com.Sercurity_service.entity.InvalidatedToken;
import com.Sercurity_service.entity.Role;
import com.Sercurity_service.entity.Users;
import com.Sercurity_service.exception.AppException;
import com.Sercurity_service.exception.ErrorCode;
import com.Sercurity_service.repository.InvalidateTokenRepository;
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
import java.util.UUID;

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

    @Autowired
    InvalidateTokenRepository invalidateTokenRepository;

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
                .jwtID(UUID.randomUUID().toString())
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
    public SignedJWT verifyToken(String token) throws JOSEException, ParseException {

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);
        Date exp = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        if(!(verified && exp.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        IntrospectResponse response = new IntrospectResponse();
        var token = request.getToken();
        log.info("giá trị token: "+ token);

        try {
            SignedJWT jwtToken =verifyToken(token);
            String jwtId = jwtToken.getJWTClaimsSet().getJWTID();

            log.warn("gia tri cua jwtID: "+ jwtId);
            log.info("jwtID đã tồn tại trong bảng: "+ invalidateTokenRepository.existsById(jwtId));

            if(invalidateTokenRepository.existsAllByToken(jwtId)){
                log.warn("da chay vào lỗi");
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
            response.setToken_valid(true);
        }catch (AppException e){
           response.setToken_valid(false);
        }
        return response;
    };



    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        var token = request.getToken();
        String tokenId = verifyToken(token).getJWTClaimsSet().getJWTID();
        Date expTime = verifyToken(token).getJWTClaimsSet().getExpirationTime();
        invalidateTokenRepository.save(new InvalidatedToken(tokenId, expTime));

    }

    private String buildScope(Users users){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!users.getRoles().isEmpty()){
            for (Role role: users.getRoles()){
                stringJoiner.add(role.getRole());
            }
        }
        return stringJoiner.toString();
    }
}
