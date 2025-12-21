package com.example.shareview.usecases;

import com.example.shareview.dtos.requests.CreateClassRequest;
import com.example.shareview.entities.Class;
import com.example.shareview.entities.Course;
import com.example.shareview.gateways.ClassGateway;
import com.example.shareview.gateways.CourseGateway;
import com.example.shareview.mappers.ClassMapper;

public class CreateClassUseCase {
    private final CourseGateway courseGateway;
    private final ClassGateway classGateway;
    private final CheckCourseNameAvailability checkCourseNameAvailability;

    public CreateClassUseCase(CourseGateway courseGateway, ClassGateway classGateway) {
        this.courseGateway = courseGateway;
        this.classGateway = classGateway;
        this.checkCourseNameAvailability = new CheckCourseNameAvailability(courseGateway);
    }

    public Class execute(CreateClassRequest createClassRequest) {
        Course course = courseGateway.findCourseById(createClassRequest.courseId());
        Class clazz = new Class(null, course, null, null, createClassRequest.startDate());
        checkCourseNameAvailability.execute(clazz.getCourse().getName());
        return classGateway.createClass(ClassMapper.toDto(clazz));
    }
}
