package com.example.shareview.dtos.responses;

import com.example.shareview.enums.UserType;

public record UserResponse(Long id, String email, UserType userType) {
}
