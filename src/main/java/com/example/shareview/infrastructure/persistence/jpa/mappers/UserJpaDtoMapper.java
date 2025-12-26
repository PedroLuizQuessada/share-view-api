package com.example.shareview.infrastructure.persistence.jpa.mappers;

import com.example.shareview.infrastructure.persistence.jpa.models.UserJpa;
import dtos.UserDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("jpa")
public class UserJpaDtoMapper {

    public UserJpa toUserJpa(UserDto userDto) {
        return new UserJpa(userDto.id(), userDto.email(), userDto.password(), userDto.userType());
    }

    public UserDto toUserDto(UserJpa userJpa) {
        return new UserDto(userJpa.getId(), userJpa.getEmail(), userJpa.getPassword(), userJpa.getUserType());
    }

}
