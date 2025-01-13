package com.Sercurity_service.configuration;

import com.Sercurity_service.dto.request.IntrospectRequest;
import com.Sercurity_service.dto.response.IntrospectResponse;
import com.Sercurity_service.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Component
@Slf4j
public class CustomJwtDecoder implements JwtDecoder {
    private final AuthenticationService authenticationService;
    @Value("${jwt.signerKey}")
    private String signerKey;
    private NimbusJwtDecoder nimbusJwtDecoder = null;

    public CustomJwtDecoder(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Jwt decode(String token) throws JwtException {

        try {
            IntrospectRequest request = new IntrospectRequest(token);
            IntrospectResponse response = authenticationService.introspect(request);
            if (response == null) {
                throw new JwtException("Phản hồi kiểm tra token bị null");
            }
                log.info("gia trị trả về token: " + response.isToken_valid());
            if (!response.isToken_valid()) {
                throw new JwtException("Token không hợp lệ khi kiểm tra");
            }
        }catch ( JOSEException | ParseException e){
            throw new JwtException(e.getMessage());
        }

        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}
