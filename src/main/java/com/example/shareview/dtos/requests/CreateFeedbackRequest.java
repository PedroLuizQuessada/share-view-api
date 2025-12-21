package com.example.shareview.dtos.requests;

public record CreateFeedbackRequest(Long classId, Integer rating, String description) {
}
