package com.example.shareview.datasources;

import com.example.shareview.dtos.CourseDto;

import java.util.Optional;

public interface CourseDataSource {
    Long countByName(String name);
    CourseDto createCourse(CourseDto course);
    Optional<CourseDto> findCourseById(Long id);
    void updateCourse(CourseDto courseDto);
    void deleteCourse(CourseDto courseDto);
}
