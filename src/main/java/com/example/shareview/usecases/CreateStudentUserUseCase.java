package com.example.shareview.usecases;

import com.example.shareview.dtos.requests.CreateStudentUserRequest;
import com.example.shareview.entities.User;
import com.example.shareview.enums.UserType;
import com.example.shareview.gateways.UserGateway;
import com.example.shareview.mappers.UserMapper;

public class CreateStudentUserUseCase {
    private final UserGateway userGateway;
    private final CheckUserEmailAvailabilityUseCase checkUserEmailAvailabilityUseCase;

    public CreateStudentUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
        this.checkUserEmailAvailabilityUseCase = new CheckUserEmailAvailabilityUseCase(userGateway);
    }

    public User execute(CreateStudentUserRequest createStudentUserRequest) {
        User user = new User(null, createStudentUserRequest.email(), createStudentUserRequest.password(),
                UserType.STUDENT, true);
        checkUserEmailAvailabilityUseCase.execute(user.getEmail());
        return userGateway.createUser(UserMapper.toDto(user));
    }
}
