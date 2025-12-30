package com.example.shareview.infrastructure.persistence.jpa.repos;

import com.example.shareview.datasources.CourseDataSource;
import dtos.CourseDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import mappers.CourseJpaDtoMapper;
import models.CourseJpa;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Profile("jpa")
public class CourseRepositoryJpaImpl implements CourseDataSource {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long countByName(String name) {
        Query query = entityManager.createQuery("SELECT count(*) FROM CourseJpa course WHERE course.name = :name");
        query.setParameter("name", name);
        return (Long) query.getSingleResult();
    }

    @Override
    @Transactional
    public CourseDto createCourse(CourseDto course) {
        CourseJpa courseJpa = CourseJpaDtoMapper.toCourseJpa(course);
        courseJpa = entityManager.merge(courseJpa);
        return CourseJpaDtoMapper.toCourseDto(courseJpa);
    }

    @Override
    public Optional<CourseDto> findCourseById(Long id) {
        Optional<CourseJpa> optionalCourseJpa = Optional.ofNullable(entityManager.find(CourseJpa.class, id));
        return optionalCourseJpa.map(CourseJpaDtoMapper::toCourseDto);
    }

    @Override
    @Transactional
    public void updateCourse(CourseDto courseDto) {
        CourseJpa courseJpa = CourseJpaDtoMapper.toCourseJpa(courseDto);
        entityManager.merge(courseJpa);
    }

    @Override
    @Transactional
    public void deleteCourse(CourseDto courseDto) {
        Query query = entityManager.createQuery("DELETE FROM CourseJpa course WHERE course.id = :id");
        query.setParameter("id", courseDto.id());
        query.executeUpdate();
    }
}
