package com.example.shareview.infrastructure.persistence.jpa.mappers;

import com.example.shareview.infrastructure.persistence.jpa.models.CourseJpa;
import dtos.CourseDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("jpa")
public class CourseJpaDtoMapper {

    public CourseJpa toCourseJpa(CourseDto courseDto) {
        return new CourseJpa(courseDto.id(), courseDto.name(), courseDto.description());
    }

    public CourseDto toCourseDto(CourseJpa courseJpa) {
        return new CourseDto(courseJpa.getId(), courseJpa.getName(), courseJpa.getDescription());
    }

}
