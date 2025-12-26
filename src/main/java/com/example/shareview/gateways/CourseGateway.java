package com.example.shareview.gateways;

import com.example.shareview.datasources.CourseDataSource;
import com.example.shareview.entities.Course;
import com.example.shareview.exceptions.CourseNotFoundException;
import com.example.shareview.mappers.CourseMapper;
import dtos.CourseDto;

import java.util.Optional;

public class CourseGateway {

    private final CourseDataSource courseDataSource;

    public CourseGateway(CourseDataSource courseDataSource) {
        this.courseDataSource = courseDataSource;
    }

    public Long countByName(String name) {
        return courseDataSource.countByName(name);
    }

    public Course createCourse(CourseDto createCourseDto) {
        CourseDto courseDto = courseDataSource.createCourse(createCourseDto);
        return CourseMapper.toEntity(courseDto);
    }

    public Course findCourseById(Long id) {
        Optional<CourseDto> courseDtoOptional = courseDataSource.findCourseById(id);

        if (courseDtoOptional.isEmpty())
            throw new CourseNotFoundException(id);

        return CourseMapper.toEntity(courseDtoOptional.get());
    }

    public void updateCourse(CourseDto courseDto) {
        courseDataSource.updateCourse(courseDto);
    }

    public void deleteCourse(CourseDto courseDto) {
        courseDataSource.deleteCourse(courseDto);
    }
}
