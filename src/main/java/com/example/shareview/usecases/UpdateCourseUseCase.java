package com.example.shareview.usecases;

import com.example.shareview.entities.Course;
import com.example.shareview.gateways.CourseGateway;
import com.example.shareview.mappers.CourseMapper;
import dtos.requests.UpdateCourseRequest;

import java.util.Objects;

public class UpdateCourseUseCase {

    private final CourseGateway courseGateway;
    private final CheckCourseNameAvailability checkCourseNameAvailability;

    public UpdateCourseUseCase(CourseGateway courseGateway) {
        this.courseGateway = courseGateway;
        this.checkCourseNameAvailability = new CheckCourseNameAvailability(courseGateway);
    }

    public void execute(Long id, UpdateCourseRequest updateCourseRequest) {
        Course course = courseGateway.findCourseById(id);
        String oldName = course.getName();
        course.setName(updateCourseRequest.name());
        course.setDescription(updateCourseRequest.description());

        if (!Objects.equals(oldName, course.getName()))
            checkCourseNameAvailability.execute(course.getName());

        courseGateway.updateCourse(CourseMapper.toDto(course));
    }
}
