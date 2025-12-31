package com.example.shareview.datasources;

import enums.UserType;

public interface TokenDataSource {
    String generateToken(UserType userType, String email);
    String generateServiceToken();
    String getEmail(String token);
}
