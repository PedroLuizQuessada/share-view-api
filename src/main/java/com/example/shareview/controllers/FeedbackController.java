package com.example.shareview.controllers;

import com.example.shareview.datasources.ClassDataSource;
import com.example.shareview.datasources.FeedbackDataSource;
import com.example.shareview.datasources.TokenDataSource;
import com.example.shareview.datasources.UserDataSource;
import com.example.shareview.entities.Feedback;
import com.example.shareview.gateways.ClassGateway;
import com.example.shareview.gateways.FeedbackGateway;
import com.example.shareview.gateways.TokenGateway;
import com.example.shareview.gateways.UserGateway;
import com.example.shareview.mappers.FeedbackMapper;
import com.example.shareview.usecases.CreateFeedbackUseCase;
import dtos.requests.CreateFeedbackRequest;
import dtos.responses.FeedbackResponse;

public class FeedbackController {

    private final TokenDataSource tokenDataSource;
    private final UserDataSource userDataSource;
    private final ClassDataSource classDataSource;
    private final FeedbackDataSource feedbackDataSource;

    public FeedbackController(TokenDataSource tokenDataSource, UserDataSource userDataSource, ClassDataSource classDataSource, FeedbackDataSource feedbackDataSource) {
        this.tokenDataSource = tokenDataSource;
        this.userDataSource = userDataSource;
        this.classDataSource = classDataSource;
        this.feedbackDataSource = feedbackDataSource;
    }

    public FeedbackResponse createFeedback(String token, CreateFeedbackRequest request) {
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        UserGateway userGateway = new UserGateway(userDataSource);
        ClassGateway classGateway = new ClassGateway(classDataSource);
        FeedbackGateway feedbackGateway = new FeedbackGateway(feedbackDataSource);
        CreateFeedbackUseCase useCase = new CreateFeedbackUseCase(tokenGateway, userGateway, classGateway, feedbackGateway);
        Feedback feedback = useCase.execute(token, request);
        return FeedbackMapper.toResponse(feedback);
    }
}
