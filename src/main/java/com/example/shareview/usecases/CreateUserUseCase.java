package com.example.shareview.usecases;

import com.example.shareview.dtos.requests.CreateUserRequest;
import com.example.shareview.entities.User;
import com.example.shareview.gateways.UserGateway;
import com.example.shareview.mappers.UserMapper;

public class CreateUserUseCase {
    private final UserGateway userGateway;
    private final CheckUserEmailAvailabilityUseCase checkUserEmailAvailabilityUseCase;

    public CreateUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
        this.checkUserEmailAvailabilityUseCase = new CheckUserEmailAvailabilityUseCase(userGateway);
    }

    public User execute(CreateUserRequest createUserRequest) {
        User user = new User(null, createUserRequest.email(), createUserRequest.password(),
                createUserRequest.userType(), true);
        checkUserEmailAvailabilityUseCase.execute(user.getEmail());
        return userGateway.createUser(UserMapper.toDto(user));
    }
}
