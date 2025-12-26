package com.example.shareview.usecases;

import com.example.shareview.entities.User;
import com.example.shareview.gateways.TokenGateway;
import com.example.shareview.gateways.UserGateway;
import dtos.requests.UpdateUserEmailRequest;

import java.util.Objects;

public class UpdateUserEmailUseCase {
    private final TokenGateway tokenGateway;
    private final UserGateway userGateway;
    private final CheckUserEmailAvailabilityUseCase checkUserEmailAvailabilityUseCase;

    public UpdateUserEmailUseCase(TokenGateway tokenGateway, UserGateway userGateway) {
        this.tokenGateway = tokenGateway;
        this.userGateway = userGateway;
        this.checkUserEmailAvailabilityUseCase = new CheckUserEmailAvailabilityUseCase(userGateway);
    }

    public void execute(String token, UpdateUserEmailRequest request) {
        String oldEmail = tokenGateway.getEmail(token);

        User user = userGateway.findUserByEmail(oldEmail);
        user.setEmail(request.newEmail());

        if (!Objects.equals(oldEmail, user.getEmail()))
            checkUserEmailAvailabilityUseCase.execute(user.getEmail());

        userGateway.updateUserEmailById(user.getId(), user.getEmail());
    }
}
