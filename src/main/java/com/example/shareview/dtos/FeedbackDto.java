package com.example.shareview.dtos;

import java.util.Date;

public record FeedbackDto(Long id, UserDto student, ClassDto clazz, Integer rating, String description, Date evaluationDate) {
}
