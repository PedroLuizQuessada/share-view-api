package com.example.shareview.mappers;

import com.example.shareview.entities.User;
import dtos.responses.LoginResponse;

public class LoginMapper {

    private LoginMapper() {}

    public static LoginResponse toResponse(User user) {
        return new LoginResponse(user.getEmail(), user.getPassword(), user.getUserType());
    }
}
