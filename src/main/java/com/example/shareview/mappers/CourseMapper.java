package com.example.shareview.mappers;

import com.example.shareview.entities.Course;
import dtos.CourseDto;
import dtos.responses.CourseResponse;

public class CourseMapper {

    private CourseMapper() {}

    public static CourseDto toDto(Course course) {
        return new CourseDto(course.getId(), course.getName(), course.getDescription());
    }

    public static Course toEntity(CourseDto courseDto) {
        return new Course(courseDto.id(), courseDto.name(), courseDto.description());
    }

    public static CourseResponse toResponse(Course course) {
        return new CourseResponse(course.getId(), course.getName(), course.getDescription());
    }
}
