package com.example.shareview.infrastructure.persistence.jpa.repos;

import com.example.shareview.datasources.FeedbackDataSource;
import com.example.shareview.infrastructure.persistence.jpa.mappers.FeedbackJpaDtoMapper;
import com.example.shareview.infrastructure.persistence.jpa.models.FeedbackJpa;
import dtos.FeedbackDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public class FeedbackRepositoryJpaImpl implements FeedbackDataSource {

    @Autowired
    private FeedbackJpaDtoMapper feedbackJpaDtoMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public FeedbackDto createFeedback(FeedbackDto feedbackDto) {
        FeedbackJpa feedbackJpa = feedbackJpaDtoMapper.toFeedbackJpa(feedbackDto);
        feedbackJpa = entityManager.merge(feedbackJpa);
        return feedbackJpaDtoMapper.toFeedbackDto(feedbackJpa);
    }
}
