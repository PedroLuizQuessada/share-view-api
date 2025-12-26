package com.example.shareview.infrastructure.persistence.jpa.mappers;

import com.example.shareview.dtos.FeedbackDto;
import com.example.shareview.infrastructure.persistence.jpa.models.FeedbackJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Profile("jpa")
public class FeedbackJpaDtoMapper {

    @Autowired
    private ClassJpaDtoMapper classJpaDtoMapper;

    @Autowired
    private UserJpaDtoMapper userJpaDtoMapper;

    public FeedbackJpa toFeedbackJpa(FeedbackDto feedbackDto) {
        return new FeedbackJpa(feedbackDto.id(),
                Objects.isNull(feedbackDto.student()) ? null : userJpaDtoMapper.toUserJpa(feedbackDto.student()),
                Objects.isNull(feedbackDto.clazz()) ? null : classJpaDtoMapper.toClassJpa(feedbackDto.clazz()),
                feedbackDto.rating(), feedbackDto.description(), new java.sql.Date(feedbackDto.evaluationDate().getTime()));
    }

    public FeedbackDto toFeedbackDto(FeedbackJpa feedbackJpa) {
        return new FeedbackDto(feedbackJpa.getId(),
                Objects.isNull(feedbackJpa.getStudentJpa()) ? null : userJpaDtoMapper.toUserDto(feedbackJpa.getStudentJpa()),
                Objects.isNull(feedbackJpa.getClassJpa()) ? null : classJpaDtoMapper.toClassDto(feedbackJpa.getClassJpa()),
                feedbackJpa.getRating(), feedbackJpa.getDescription(), feedbackJpa.getEvaluationDate());
    }

}
