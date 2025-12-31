package com.example.shareview.usecases;

import com.example.shareview.entities.Class;
import com.example.shareview.entities.Feedback;
import com.example.shareview.entities.User;
import com.example.shareview.exceptions.BadRequestException;
import com.example.shareview.gateways.*;
import com.example.shareview.mappers.FeedbackMapper;
import dtos.requests.CreateBadFeedbackNotificationRequest;
import dtos.requests.CreateFeedbackRequest;

public class CreateFeedbackUseCase {

    private final TokenGateway tokenGateway;
    private final UserGateway userGateway;
    private final ClassGateway classGateway;
    private final FeedbackGateway feedbackGateway;
    private final FeedbackNotificationGateway feedbackNotificationGateway;

    public CreateFeedbackUseCase(TokenGateway tokenGateway, UserGateway userGateway, ClassGateway classGateway, FeedbackGateway feedbackGateway, FeedbackNotificationGateway feedbackNotificationGateway) {
        this.tokenGateway = tokenGateway;
        this.userGateway = userGateway;
        this.classGateway = classGateway;
        this.feedbackGateway = feedbackGateway;
        this.feedbackNotificationGateway = feedbackNotificationGateway;
    }

    public Feedback execute(String token, CreateFeedbackRequest createFeedbackRequest) {
        String email = tokenGateway.getEmail(token);
        User user = userGateway.findUserByEmail(email);
        Class clazz = classGateway.findClassById(createFeedbackRequest.classId());

        if (clazz.getStudentsCopy().stream().noneMatch(student -> student.getId().equals(user.getId())))
            throw new BadRequestException("Aluno só podem criar feedbacks para classes das quais fizeram parte.");

        Feedback feedback = new Feedback(null, user, clazz, createFeedbackRequest.rating(), createFeedbackRequest.description());
        feedback = feedbackGateway.createFeedback(FeedbackMapper.toDto(feedback));

        if (feedback.getRating() <= 4) {
            CreateBadFeedbackNotificationRequest createBadFeedbackNotificationRequest =
                    new CreateBadFeedbackNotificationRequest(user.getEmail(), clazz.getCourse().getName(),
                            createFeedbackRequest.rating(), createFeedbackRequest.description());
            String serviceToken = tokenGateway.generateServiceToken();
            feedbackNotificationGateway.sendBadFeedbackNotification(serviceToken, createBadFeedbackNotificationRequest);
        }

        return feedback;
    }
}
