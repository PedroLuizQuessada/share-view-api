package com.example.shareview.datasources;

import dtos.UserDto;

import java.util.Optional;

public interface UserDataSource {
    Long countByEmail(String email);
    UserDto createUser(UserDto userDto);
    Optional<UserDto> findUserByEmail(String email);
    Optional<UserDto> findUserById(Long id);
    void updateUserEmailById(Long id, String email);
    void updateUserPasswordById(Long id, String password);
    void deleteUser(UserDto userDto);
}
