package com.example.shareview.infrastructure.persistence.jpa.repos;

import com.example.shareview.datasources.FeedbackDataSource;
import dtos.FeedbackDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import mappers.FeedbackJpaDtoMapper;
import models.FeedbackJpa;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Profile("jpa")
public class FeedbackRepositoryJpaImpl implements FeedbackDataSource {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public FeedbackDto createFeedback(FeedbackDto feedbackDto) {
        FeedbackJpa feedbackJpa = FeedbackJpaDtoMapper.toFeedbackJpa(feedbackDto);
        feedbackJpa = entityManager.merge(feedbackJpa);
        return FeedbackJpaDtoMapper.toFeedbackDto(feedbackJpa);
    }
}
