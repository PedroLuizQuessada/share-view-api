package com.example.shareview.datasources;

import com.example.shareview.enums.UserType;

public interface TokenDataSource {
    String generateToken(UserType userType, String email);
    String getEmail(String token);
}
