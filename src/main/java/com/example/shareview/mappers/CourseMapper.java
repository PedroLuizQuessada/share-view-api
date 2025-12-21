package com.example.shareview.mappers;

import com.example.shareview.dtos.CourseDto;
import com.example.shareview.dtos.responses.CourseResponse;
import com.example.shareview.entities.Course;

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
