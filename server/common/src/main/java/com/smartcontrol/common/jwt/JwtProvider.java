package com.smartcontrol.common.jwt;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.smartcontrol.common.constant.Role;
import com.smartcontrol.common.dto.Token;

/**
 * JWTProvider class.
 * 
 * @author MinhTuMTN
 */
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final int DEFAULT_TOKEN_DURATION = 60 * 60; // Default token duration in seconds (1 hour)

    private static final int DEFAULT_REFRESH_TOKEN_DURATION = 60 * 60 * 24 * 30; // Default refresh token duration in seconds (30 days)

    /**
     * Verifies the given JWT token.
     *
     * @return SignedJWT (if valid)
     * @throws JwtException if token is invalid
     */
    public SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(secretKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();
        if (expirationDate.before(new Date())) {
           throw new JwtException("Token has expired");
        }

        if (!signedJWT.verify(verifier)) {
            throw new JwtException("Invalid token signature");
        }

        return signedJWT;
    }
    
    /**
     * Generates a JWT token for the given user ID and role.
     *
     * @param userId The ID of the user.
     * @param role The role of the user.
     * @param durationInSeconds The duration for which the token is valid, in seconds.
     * @return The generated JWT token as a String.
     */    
    private Token generateToken(String userId, String role, int durationInSeconds) {
        // JWT Header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        Instant expirationTime = Instant.now().plusSeconds(durationInSeconds);
        Date expirationDate = Date.from(expirationTime);

        // Payload
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
            .subject(userId)
            .issueTime(new Date())
            .expirationTime(expirationDate)
            .jwtID(UUID.randomUUID().toString())
            .claim("scope", role) // Add role as a claim
            .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return Token.builder()
                .token(jwsObject.serialize())
                .expiration(claimsSet.getExpirationTime())
                .build();
        } catch (JOSEException e) {
            throw new RuntimeException("Error generating token", e);
        }
    }

    /**
     * Generates an access token for the given user ID and role.
     *
     * @param userId The ID of the user.
     * @param role The role of the user.
     * @return The generated access token as a String.
     */
    public Token generateAccessToken(String userId, Role role) {
        return generateToken(userId, role.getValue(), DEFAULT_TOKEN_DURATION);
    }

    /**
     * Generates a refresh token for the given user ID and role.
     *
     * @param userId The ID of the user.
     * @param role The role of the user.
     * @return The generated refresh token as a String.
     */
    public Token generateRefreshToken(String userId, Role role) {
        return generateToken(userId, role.getValue(), DEFAULT_REFRESH_TOKEN_DURATION);
    }

    /**
     * Checks if the given JWT token is valid.
     * @param token The JWT token to validate.
     * @return true if the token is valid, false otherwise.
     */
    public boolean isValidToken(String token) {
        try {
            verifyToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
