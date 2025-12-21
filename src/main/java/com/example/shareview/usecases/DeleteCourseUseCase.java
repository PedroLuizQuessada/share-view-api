package com.example.shareview.usecases;

import com.example.shareview.entities.Course;
import com.example.shareview.exceptions.BadArgumentException;
import com.example.shareview.gateways.ClassGateway;
import com.example.shareview.gateways.CourseGateway;
import com.example.shareview.mappers.CourseMapper;

public class DeleteCourseUseCase {
    private final ClassGateway classGateway;
    private final CourseGateway courseGateway;

    public DeleteCourseUseCase(ClassGateway classGateway, CourseGateway courseGateway) {
        this.classGateway = classGateway;
        this.courseGateway = courseGateway;
    }

    public void execute(Long courseId) {
        Course course = courseGateway.findCourseById(courseId);
        if (classGateway.countClassesFromCourse(CourseMapper.toDto(course)) > 0)
            throw new BadArgumentException(String.format("Curso %d possui turmas", courseId));
        courseGateway.deleteCourse(CourseMapper.toDto(course));
    }
}
