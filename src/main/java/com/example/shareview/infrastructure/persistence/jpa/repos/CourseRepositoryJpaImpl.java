package com.example.shareview.infrastructure.persistence.jpa.repos;

import com.example.shareview.datasources.CourseDataSource;
import com.example.shareview.dtos.CourseDto;
import com.example.shareview.infrastructure.persistence.jpa.mappers.CourseJpaDtoMapper;
import com.example.shareview.infrastructure.persistence.jpa.models.CourseJpa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Profile("jpa")
public class CourseRepositoryJpaImpl implements CourseDataSource {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CourseJpaDtoMapper courseJpaDtoMapper;

    @Override
    public Long countByName(String name) {
        Query query = entityManager.createQuery("SELECT count(*) FROM CourseJpa course WHERE course.name = :name");
        query.setParameter("name", name);
        return (Long) query.getSingleResult();
    }

    @Override
    @Transactional
    public CourseDto createCourse(CourseDto course) {
        CourseJpa courseJpa = courseJpaDtoMapper.toCourseJpa(course);
        courseJpa = entityManager.merge(courseJpa);
        return courseJpaDtoMapper.toCourseDto(courseJpa);
    }

    @Override
    public Optional<CourseDto> findCourseById(Long id) {
        Optional<CourseJpa> optionalCourseJpa = Optional.ofNullable(entityManager.find(CourseJpa.class, id));
        return optionalCourseJpa.map(courseJpaDtoMapper::toCourseDto);
    }

    @Override
    @Transactional
    public void updateCourse(CourseDto courseDto) {
        CourseJpa courseJpa = courseJpaDtoMapper.toCourseJpa(courseDto);
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
