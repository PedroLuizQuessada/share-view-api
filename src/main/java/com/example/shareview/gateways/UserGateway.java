package com.example.shareview.gateways;

import com.example.shareview.datasources.UserDataSource;
import com.example.shareview.entities.User;
import com.example.shareview.exceptions.UserNotFoundException;
import com.example.shareview.mappers.UserMapper;
import dtos.UserDto;

import java.util.Optional;

public class UserGateway {

    private final UserDataSource userDataSource;

    public UserGateway(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    public Long countByEmail(String email) {
        return userDataSource.countByEmail(email);
    }

    public User createUser(UserDto createUserDto) {
        UserDto userDto = userDataSource.createUser(createUserDto);
        return UserMapper.toEntity(userDto, false);
    }

    public User findUserByEmail(String email) {
        Optional<UserDto> userDtoOptional = userDataSource.findUserByEmail(email);

        if (userDtoOptional.isEmpty())
            throw new UserNotFoundException(email);

        return UserMapper.toEntity(userDtoOptional.get(), false);
    }

    public User findUserById(Long id) {
        Optional<UserDto> userDtoOptional = userDataSource.findUserById(id);

        if (userDtoOptional.isEmpty())
            throw new UserNotFoundException(id);

        return UserMapper.toEntity(userDtoOptional.get(), false);
    }

    public void updateUserEmailById(Long id, String email) {
        userDataSource.updateUserEmailById(id, email);
    }

    public void updateUserPasswordById(Long id, String password) {
        userDataSource.updateUserPasswordById(id, password);
    }

    public void deleteUser(UserDto userDto) {
        userDataSource.deleteUser(userDto);
    }
}
