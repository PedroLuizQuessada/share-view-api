package com.example.shareview.enums;

public enum UserType {
    STUDENT,
    TEACHER,
    ADMINISTRATOR;

    public static UserType fromString(String value) {
        for (UserType userType : UserType.values()) {
            if (userType.toString().equalsIgnoreCase(value)) {
                return userType;
            }
        }
        return null;
    }
}
