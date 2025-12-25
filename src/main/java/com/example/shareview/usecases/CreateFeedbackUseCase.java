package com.example.shareview.usecases;

import com.example.shareview.dtos.requests.CreateFeedbackRequest;
import com.example.shareview.entities.Class;
import com.example.shareview.entities.Feedback;
import com.example.shareview.entities.User;
import com.example.shareview.exceptions.BadRequestException;
import com.example.shareview.gateways.ClassGateway;
import com.example.shareview.gateways.FeedbackGateway;
import com.example.shareview.gateways.TokenGateway;
import com.example.shareview.gateways.UserGateway;
import com.example.shareview.mappers.FeedbackMapper;

public class CreateFeedbackUseCase {

    private final TokenGateway tokenGateway;
    private final UserGateway userGateway;
    private final ClassGateway classGateway;
    private final FeedbackGateway feedbackGateway;

    public CreateFeedbackUseCase(TokenGateway tokenGateway, UserGateway userGateway, ClassGateway classGateway, FeedbackGateway feedbackGateway) {
        this.tokenGateway = tokenGateway;
        this.userGateway = userGateway;
        this.classGateway = classGateway;
        this.feedbackGateway = feedbackGateway;
    }

    public Feedback execute(String token, CreateFeedbackRequest createFeedbackRequest) {
        String email = tokenGateway.getEmail(token);
        User user = userGateway.findUserByEmail(email);
        Class clazz = classGateway.findClassById(createFeedbackRequest.classId());

        if (clazz.getStudentsCopy().stream().noneMatch(student -> student.getId().equals(user.getId())))
            throw new BadRequestException("Aluno só podem criar feedbacks para classes das quais fizeram parte.");

        Feedback feedback = new Feedback(null, user, clazz, createFeedbackRequest.rating(), createFeedbackRequest.description());
        return feedbackGateway.createUser(FeedbackMapper.toDto(feedback));
    }
}
