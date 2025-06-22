package com.smartcontrol.common.jwt;

import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

@Component
@Primary
public class CustomJwtDecoder implements JwtDecoder {

    private final JwtProvider jwtProvider;
    private final NimbusJwtDecoder nimbusJwtDecoder;


    public CustomJwtDecoder(JwtProvider jwtProvider, NimbusJwtDecoder nimbusJwtDecoder) {
        this.jwtProvider = jwtProvider;
        this.nimbusJwtDecoder = nimbusJwtDecoder;
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            jwtProvider.verifyToken(token);
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }

        return nimbusJwtDecoder.decode(token);
    }
    
}
