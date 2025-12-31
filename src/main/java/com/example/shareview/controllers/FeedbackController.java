package com.example.shareview.controllers;

import com.example.shareview.datasources.*;
import com.example.shareview.entities.Feedback;
import com.example.shareview.gateways.*;
import com.example.shareview.mappers.FeedbackMapper;
import com.example.shareview.usecases.CreateFeedbackUseCase;
import dtos.requests.CreateFeedbackRequest;
import dtos.responses.FeedbackResponse;

public class FeedbackController {

    private final TokenDataSource tokenDataSource;
    private final UserDataSource userDataSource;
    private final ClassDataSource classDataSource;
    private final FeedbackDataSource feedbackDataSource;
    private final FeedbackNotificationDataSource feedbackNotificationDataSource;

    public FeedbackController(TokenDataSource tokenDataSource, UserDataSource userDataSource, ClassDataSource classDataSource, FeedbackDataSource feedbackDataSource, FeedbackNotificationDataSource feedbackNotificationDataSource) {
        this.tokenDataSource = tokenDataSource;
        this.userDataSource = userDataSource;
        this.classDataSource = classDataSource;
        this.feedbackDataSource = feedbackDataSource;
        this.feedbackNotificationDataSource = feedbackNotificationDataSource;
    }

    public FeedbackResponse createFeedback(String token, CreateFeedbackRequest request) {
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        UserGateway userGateway = new UserGateway(userDataSource);
        ClassGateway classGateway = new ClassGateway(classDataSource);
        FeedbackGateway feedbackGateway = new FeedbackGateway(feedbackDataSource);
        FeedbackNotificationGateway feedbackNotificationGateway = new FeedbackNotificationGateway(feedbackNotificationDataSource);
        CreateFeedbackUseCase useCase = new CreateFeedbackUseCase(tokenGateway, userGateway, classGateway, feedbackGateway, feedbackNotificationGateway);
        Feedback feedback = useCase.execute(token, request);
        return FeedbackMapper.toResponse(feedback);
    }
}
