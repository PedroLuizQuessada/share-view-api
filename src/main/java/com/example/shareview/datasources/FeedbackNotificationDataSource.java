package com.example.shareview.datasources;

import dtos.requests.CreateBadFeedbackNotificationRequest;

public interface FeedbackNotificationDataSource {
    void sendBadFeedbackNotification(String serviceToken, CreateBadFeedbackNotificationRequest createBadFeedbackNotificationRequest);
}
