package com.example.shareview.mappers;

import com.example.shareview.dtos.responses.LoginResponse;
import com.example.shareview.entities.User;

public class LoginMapper {

    private LoginMapper() {}

    public static LoginResponse toResponse(User user) {
        return new LoginResponse(user.getEmail(), user.getPassword(), user.getUserType());
    }
}
