package com.example.shareview.usecases;

import com.example.shareview.entities.User;
import com.example.shareview.gateways.UserGateway;

public class LoginUseCase {

    private final UserGateway userGateway;

    public LoginUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(String email) {
        return userGateway.findUserByEmail(email);
    }
}
