package com.example.shareview.controllers;

import com.example.shareview.datasources.ClassDataSource;
import com.example.shareview.datasources.TokenDataSource;
import com.example.shareview.datasources.UserDataSource;
import com.example.shareview.dtos.requests.*;
import com.example.shareview.dtos.responses.UserResponse;
import com.example.shareview.entities.User;
import com.example.shareview.gateways.ClassGateway;
import com.example.shareview.gateways.TokenGateway;
import com.example.shareview.gateways.UserGateway;
import com.example.shareview.mappers.UserMapper;
import com.example.shareview.usecases.*;

public class UserController {

    private final UserDataSource userDataSource;
    private final TokenDataSource tokenDataSource;
    private final ClassDataSource classDataSource;

    public UserController(UserDataSource userDataSource, TokenDataSource tokenDataSource, ClassDataSource classDataSource) {
        this.userDataSource = userDataSource;
        this.tokenDataSource = tokenDataSource;
        this.classDataSource = classDataSource;
    }

    public UserResponse createStudentUser(CreateStudentUserRequest createStudentUserRequest) {
        UserGateway userGateway = new UserGateway(userDataSource);
        CreateStudentUserUseCase useCase = new CreateStudentUserUseCase(userGateway);
        User user = useCase.execute(createStudentUserRequest);
        return UserMapper.toResponse(user);
    }

    public UserResponse createUser(CreateUserRequest createUserRequest) {
        UserGateway userGateway = new UserGateway(userDataSource);
        CreateUserUseCase useCase = new CreateUserUseCase(userGateway);
        User user = useCase.execute(createUserRequest);
        return UserMapper.toResponse(user);
    }

    public void deleteUser(String token) {
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        UserGateway userGateway = new UserGateway(userDataSource);
        ClassGateway classGateway = new ClassGateway(classDataSource);
        DeleteUserUseCase useCase = new DeleteUserUseCase(tokenGateway, userGateway, classGateway);
        useCase.execute(token);
    }

    public void updateUserEmail(String token, UpdateUserEmailRequest updateUserEmailRequest) {
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        UserGateway userGateway = new UserGateway(userDataSource);
        UpdateUserEmailUseCase useCase = new UpdateUserEmailUseCase(tokenGateway, userGateway);
        useCase.execute(token, updateUserEmailRequest);
    }

    public void updateUserPassword(String token, UpdateUserPasswordRequest updateUserPasswordRequest) {
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        UserGateway userGateway = new UserGateway(userDataSource);
        UpdateUserPasswordUseCase useCase = new UpdateUserPasswordUseCase(tokenGateway, userGateway);
        useCase.execute(token, updateUserPasswordRequest);
    }
}
