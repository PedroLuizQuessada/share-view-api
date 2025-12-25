package com.example.shareview.dtos.responses;

public record FeedbackResponse(Long id, Long studentId, Long classId, Integer rating, String description) {
}
