package com.example.shareview.usecases;

import com.example.shareview.exceptions.BadArgumentException;
import com.example.shareview.gateways.CourseGateway;

class CheckCourseNameAvailability {
    private final CourseGateway courseGateway;

    CheckCourseNameAvailability(CourseGateway courseGateway) {
        this.courseGateway = courseGateway;
    }

    void execute(String name) {
        if (courseGateway.countByName(name) > 0) {
            throw new BadArgumentException("Nome já está sendo utilizado");
        }
    }
}
