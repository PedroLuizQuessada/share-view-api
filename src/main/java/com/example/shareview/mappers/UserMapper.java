package com.example.shareview.mappers;

import com.example.shareview.entities.User;
import dtos.UserDto;
import dtos.responses.UserResponse;

public class UserMapper {

    private UserMapper() {}

    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getPassword(), user.getUserType());
    }

    public static User toEntity(UserDto userDto, boolean encodePassword) {
        return new User(userDto.id(), userDto.email(), userDto.password(), userDto.userType(), encodePassword);
    }

    public static UserResponse toResponse(User user) {
        return new  UserResponse(user.getId(), user.getEmail(), user.getUserType());
    }
}
