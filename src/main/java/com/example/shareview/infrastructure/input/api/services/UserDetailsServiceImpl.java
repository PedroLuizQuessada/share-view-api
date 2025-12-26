package com.example.shareview.infrastructure.input.api.services;

import com.example.shareview.controllers.LoginController;
import com.example.shareview.datasources.UserDataSource;
import dtos.responses.LoginResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("api")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final LoginController loginController;

    public UserDetailsServiceImpl(UserDataSource userDataSource) {
        this.loginController = new LoginController(userDataSource);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        LoginResponse userResponse = loginController.login(username);
        return new org.springframework.security.core.userdetails.User(userResponse.login(), userResponse.password(),
                List.of(new SimpleGrantedAuthority(String.valueOf(userResponse.userType()))));
    }
}
