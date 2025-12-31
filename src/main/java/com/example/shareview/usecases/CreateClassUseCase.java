package com.example.shareview.usecases;

import com.example.shareview.entities.Class;
import com.example.shareview.entities.Course;
import com.example.shareview.gateways.ClassGateway;
import com.example.shareview.gateways.CourseGateway;
import com.example.shareview.mappers.ClassMapper;
import dtos.requests.CreateClassRequest;

public class CreateClassUseCase {
    private final CourseGateway courseGateway;
    private final ClassGateway classGateway;

    public CreateClassUseCase(CourseGateway courseGateway, ClassGateway classGateway) {
        this.courseGateway = courseGateway;
        this.classGateway = classGateway;
    }

    public Class execute(CreateClassRequest createClassRequest) {
        Course course = courseGateway.findCourseById(createClassRequest.courseId());
        Class clazz = new Class(null, course, null, null, createClassRequest.startDate());
        return classGateway.createClass(ClassMapper.toDto(clazz));
    }
}
