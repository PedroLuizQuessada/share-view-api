package com.example.shareview.infrastructure.token.jwt;

import com.example.shareview.datasources.TokenDataSource;
import com.example.shareview.infrastructure.exceptions.InvalidJwtException;
import enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Profile("jwt")
public class TokenServiceJwtImpl implements TokenDataSource {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${jwt.token.expiration-time}")
    private Long expirationTime;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    public String generateToken(UserType userType, String email) {
        Instant now = Instant.now();

        var claims = JwtClaimsSet.builder()
                .issuer(applicationName)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirationTime))
                .claim("authorities", userType)
                .subject(email)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public String getEmail(String token) {
        Jwt jwt = decodeToken(token);
        return  jwt.getSubject();
    }

    private Jwt decodeToken(String token) {
        try {
            return jwtDecoder.decode(token.replace("Bearer ", ""));
        }
        catch (Exception e) {
            throw new InvalidJwtException();
        }
    }
}
