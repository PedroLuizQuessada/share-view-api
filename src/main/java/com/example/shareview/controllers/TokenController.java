package com.example.shareview.controllers;

import com.example.shareview.datasources.TokenDataSource;
import com.example.shareview.gateways.TokenGateway;
import com.example.shareview.usecases.GenerateTokenUseCase;

public class TokenController {

    private final TokenDataSource tokenDataSource;

    public TokenController(TokenDataSource tokenDataSource) {
        this.tokenDataSource = tokenDataSource;
    }

    public String generateToken(String userType, String email) {
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        GenerateTokenUseCase useCase = new GenerateTokenUseCase(tokenGateway);
        return useCase.execute(userType, email);
    }
}
