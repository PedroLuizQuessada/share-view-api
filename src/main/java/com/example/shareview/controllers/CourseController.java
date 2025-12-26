package com.example.shareview.controllers;

import com.example.shareview.datasources.ClassDataSource;
import com.example.shareview.datasources.CourseDataSource;
import com.example.shareview.entities.Course;
import com.example.shareview.gateways.ClassGateway;
import com.example.shareview.gateways.CourseGateway;
import com.example.shareview.mappers.CourseMapper;
import com.example.shareview.usecases.*;
import dtos.requests.CreateCourseRequest;
import dtos.requests.UpdateCourseRequest;
import dtos.responses.CourseResponse;

public class CourseController {

    private final CourseDataSource courseDataSource;
    private final ClassDataSource classDataSource;

    public CourseController(CourseDataSource courseDataSource, ClassDataSource classDataSource) {
        this.courseDataSource = courseDataSource;
        this.classDataSource = classDataSource;
    }

    public CourseResponse createCourse(CreateCourseRequest createCourseRequest) {
        CourseGateway courseGateway = new CourseGateway(courseDataSource);
        CreateCourseUseCase useCase = new CreateCourseUseCase(courseGateway);
        Course course = useCase.execute(createCourseRequest);
        return CourseMapper.toResponse(course);
    }

    public void deleteCourse(Long courseId) {
        ClassGateway classGateway = new ClassGateway(classDataSource);
        CourseGateway courseGateway = new CourseGateway(courseDataSource);
        DeleteCourseUseCase useCase = new DeleteCourseUseCase(classGateway, courseGateway);
        useCase.execute(courseId);
    }

    public void updateCourse(Long id,  UpdateCourseRequest updateCourseRequest) {
        CourseGateway courseGateway = new CourseGateway(courseDataSource);
        UpdateCourseUseCase useCase = new UpdateCourseUseCase(courseGateway);
        useCase.execute(id, updateCourseRequest);
    }
}
