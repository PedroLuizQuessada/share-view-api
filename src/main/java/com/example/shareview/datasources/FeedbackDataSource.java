package com.example.shareview.datasources;

import com.example.shareview.dtos.FeedbackDto;

public interface FeedbackDataSource {
    FeedbackDto createFeedback(FeedbackDto feedbackDto);
}
