package com.example.shareview.dtos;

public record FeedbackDto(UserDto student, ClassDto clazz, Integer rating, String description) {
}
