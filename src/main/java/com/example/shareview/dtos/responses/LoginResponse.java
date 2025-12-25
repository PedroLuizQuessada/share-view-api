package com.example.shareview.dtos.responses;

import com.example.shareview.enums.UserType;

public record LoginResponse(String login, String password, UserType userType) {
}
