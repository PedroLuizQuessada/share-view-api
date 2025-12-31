package com.example.shareview.gateways;

import com.example.shareview.datasources.TokenDataSource;
import enums.UserType;

public class TokenGateway {

    private final TokenDataSource tokenDataSource;

    public TokenGateway(TokenDataSource tokenDataSource) {
        this.tokenDataSource = tokenDataSource;
    }

    public String generateToken(UserType userType, String email) {
        return tokenDataSource.generateToken(userType, email);
    }

    public String generateServiceToken() {
        return tokenDataSource.generateServiceToken();
    }

    public String getEmail(String token) {
        return tokenDataSource.getEmail(token);
    }
}
