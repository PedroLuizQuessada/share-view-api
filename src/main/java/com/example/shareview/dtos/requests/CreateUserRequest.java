package com.example.shareview.dtos.requests;

import com.example.shareview.enums.UserType;

public record CreateUserRequest(String email, String password, UserType userType) {
}
