package com.example.shareview.usecases;

import com.example.shareview.enums.UserType;
import com.example.shareview.exceptions.BadArgumentException;
import com.example.shareview.gateways.TokenGateway;

import java.util.Objects;

public class GenerateTokenUseCase {
    private final TokenGateway tokenGateway;

    public GenerateTokenUseCase(TokenGateway tokenGateway) {
        this.tokenGateway = tokenGateway;
    }

    public String execute(String userTypeText, String email) {
        UserType userType = UserType.fromString(userTypeText);
        if (Objects.isNull(userType)) {
            throw new BadArgumentException(String.format("Tipo de usuário %s inválido",  userTypeText));
        }
        return tokenGateway.generateToken(userType, email);
    }
}
