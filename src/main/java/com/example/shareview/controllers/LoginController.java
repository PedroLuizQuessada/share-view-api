package com.example.shareview.controllers;

import com.example.shareview.datasources.UserDataSource;
import com.example.shareview.dtos.responses.LoginResponse;
import com.example.shareview.entities.User;
import com.example.shareview.gateways.UserGateway;
import com.example.shareview.mappers.LoginMapper;
import com.example.shareview.usecases.LoginUseCase;

public class LoginController {

    private final UserDataSource userDataSource;

    public LoginController(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    public LoginResponse login(String email) {
        UserGateway userGateway = new UserGateway(this.userDataSource);
        LoginUseCase loginUseCase = new LoginUseCase(userGateway);
        User user = loginUseCase.execute(email);
        return LoginMapper.toResponse(user);
    }
}
