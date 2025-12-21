package com.example.shareview.dtos;

import com.example.shareview.enums.UserType;

public record UserDto(Long id, String email, String password, UserType userType) {
}
