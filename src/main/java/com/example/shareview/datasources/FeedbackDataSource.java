package com.example.shareview.datasources;

import dtos.FeedbackDto;

public interface FeedbackDataSource {
    FeedbackDto createFeedback(FeedbackDto feedbackDto);
}
