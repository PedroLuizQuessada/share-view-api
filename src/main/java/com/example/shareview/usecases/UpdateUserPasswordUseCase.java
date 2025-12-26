package com.example.shareview.usecases;

import com.example.shareview.entities.User;
import com.example.shareview.gateways.TokenGateway;
import com.example.shareview.gateways.UserGateway;
import dtos.requests.UpdateUserPasswordRequest;

public class UpdateUserPasswordUseCase {

    private final TokenGateway tokenGateway;
    private final UserGateway userGateway;

    public UpdateUserPasswordUseCase(TokenGateway tokenGateway, UserGateway userGateway) {
        this.tokenGateway = tokenGateway;
        this.userGateway = userGateway;
    }

    public void execute(String token, UpdateUserPasswordRequest request) {
        String email = tokenGateway.getEmail(token);

        User user = userGateway.findUserByEmail(email);
        user.setPassword(request.newPassword());

        userGateway.updateUserPasswordById(user.getId(), user.getPassword());
    }
}
