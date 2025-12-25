package com.example.shareview.dtos;

public record FeedbackDto(Long id, UserDto student, ClassDto clazz, Integer rating, String description) {
}
