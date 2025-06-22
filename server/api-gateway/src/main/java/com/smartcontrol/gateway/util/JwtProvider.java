package com.smartcontrol.gateway.util;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
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

/**
 * JWTProvider class.
 * 
 * @author MinhTuMTN
 */
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

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
           throw new RuntimeException("Token has expired");
        }

        if (!signedJWT.verify(verifier)) {
            throw new RuntimeException("Invalid token signature");
        }

        return signedJWT;
    }
    
    /**
     * Generates a JWT token for the given user ID and role.
     *
     * @param userId The ID of the user.
     * @param role The role of the user.
     * @return The generated JWT token as a String.
     */    
    public String generateToken(String userId, String role) {
        // JWT Header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Payload
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
            .subject(userId)
            .issueTime(new Date())
            .expirationTime(new Date(System.currentTimeMillis() + 3600000))
            .jwtID(UUID.randomUUID().toString())
            .claim("scope", "ROLE_USER")
            .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Error generating token", e);
        }
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
