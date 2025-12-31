package com.example.shareview.gateways;

import com.example.shareview.datasources.FeedbackNotificationDataSource;
import dtos.requests.CreateBadFeedbackNotificationRequest;

public class FeedbackNotificationGateway {

    private final FeedbackNotificationDataSource feedbackNotificationDataSource;

    public FeedbackNotificationGateway(FeedbackNotificationDataSource feedbackNotificationDataSource) {
        this.feedbackNotificationDataSource = feedbackNotificationDataSource;
    }

    public void sendBadFeedbackNotification(String serviceToken, CreateBadFeedbackNotificationRequest createBadFeedbackNotificationRequest) {
        feedbackNotificationDataSource.sendBadFeedbackNotification(serviceToken, createBadFeedbackNotificationRequest);
    }
}
