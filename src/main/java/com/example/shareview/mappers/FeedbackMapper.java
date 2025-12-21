package com.example.shareview.mappers;

import com.example.shareview.dtos.FeedbackDto;
import com.example.shareview.dtos.responses.FeedbackResponse;
import com.example.shareview.entities.Feedback;

import java.util.Objects;

public class FeedbackMapper {

    private FeedbackMapper() {}

    public static FeedbackDto toDto(Feedback feedback) {
        return new FeedbackDto(
                Objects.isNull(feedback.getStudent()) ? null : UserMapper.toDto(feedback.getStudent()),
                Objects.isNull(feedback.getClazz()) ? null : ClassMapper.toDto(feedback.getClazz()),
                feedback.getRating(), feedback.getDescription());
    }

    public static Feedback toEntity(FeedbackDto feedbackDto) {
        return new Feedback(
                Objects.isNull(feedbackDto.student()) ? null : UserMapper.toEntity(feedbackDto.student(), false),
                Objects.isNull(feedbackDto.clazz()) ? null : ClassMapper.toEntity(feedbackDto.clazz()),
                feedbackDto.rating(), feedbackDto.description());
    }

    public static FeedbackResponse toResponse(Feedback feedback) {
        return new FeedbackResponse(feedback.getStudent().getId(), feedback.getClazz().getId(), feedback.getRating(), feedback.getDescription());
    }
}
