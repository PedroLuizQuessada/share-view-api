package com.example.shareview.usecases;

import com.example.shareview.exceptions.BadArgumentException;
import com.example.shareview.gateways.UserGateway;

class CheckUserEmailAvailabilityUseCase {
    private final UserGateway userGateway;

    CheckUserEmailAvailabilityUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    void execute(String email) {
        if (userGateway.countByEmail(email) > 0) {
            throw new BadArgumentException("E-mail já está sendo utilizado");
        }
    }
}
