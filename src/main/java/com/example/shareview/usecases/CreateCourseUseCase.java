package com.example.shareview.usecases;

import com.example.shareview.dtos.requests.CreateCourseRequest;
import com.example.shareview.entities.Course;
import com.example.shareview.gateways.CourseGateway;
import com.example.shareview.mappers.CourseMapper;

public class CreateCourseUseCase {

    private final CourseGateway courseGateway;
    private final CheckCourseNameAvailability checkCourseNameAvailability;

    public CreateCourseUseCase(CourseGateway courseGateway) {
        this.courseGateway = courseGateway;
        this.checkCourseNameAvailability = new CheckCourseNameAvailability(courseGateway);
    }

    public Course execute(CreateCourseRequest createCourseRequest) {
        Course course = new Course(null, createCourseRequest.name(), createCourseRequest.description());
        checkCourseNameAvailability.execute(course.getName());
        return courseGateway.createCourse(CourseMapper.toDto(course));
    }
}
