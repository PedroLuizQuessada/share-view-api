package com.example.shareview.gateways;

import com.example.shareview.datasources.FeedbackDataSource;
import com.example.shareview.dtos.FeedbackDto;
import com.example.shareview.entities.Feedback;
import com.example.shareview.mappers.FeedbackMapper;

public class FeedbackGateway {

    private final FeedbackDataSource feedbackDataSource;

    public FeedbackGateway(FeedbackDataSource feedbackDataSource) {
        this.feedbackDataSource = feedbackDataSource;
    }

    public Feedback createUser(FeedbackDto createFeedbackDto) {
        FeedbackDto feedbackDto = feedbackDataSource.createFeedback(createFeedbackDto);
        return FeedbackMapper.toEntity(feedbackDto);
    }
}
