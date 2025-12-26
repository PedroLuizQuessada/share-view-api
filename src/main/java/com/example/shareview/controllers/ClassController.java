package com.example.shareview.controllers;

import com.example.shareview.datasources.ClassDataSource;
import com.example.shareview.datasources.CourseDataSource;
import com.example.shareview.datasources.UserDataSource;
import com.example.shareview.entities.Class;
import com.example.shareview.gateways.ClassGateway;
import com.example.shareview.gateways.CourseGateway;
import com.example.shareview.gateways.UserGateway;
import com.example.shareview.mappers.ClassMapper;
import com.example.shareview.usecases.*;
import dtos.requests.CreateClassRequest;
import dtos.responses.ClassResponse;

public class ClassController {

    private final ClassDataSource classDataSource;
    private final UserDataSource userDataSource;
    private final CourseDataSource courseDataSource;

    public ClassController(ClassDataSource classDataSource, UserDataSource userDataSource, CourseDataSource courseDataSource) {
        this.classDataSource = classDataSource;
        this.userDataSource = userDataSource;
        this.courseDataSource = courseDataSource;
    }

    public void addStudentToClass(Long classId, Long studentId) {
        ClassGateway classGateway = new ClassGateway(classDataSource);
        UserGateway userGateway = new UserGateway(userDataSource);
        AddStudentToClassUseCase useCase = new AddStudentToClassUseCase(classGateway, userGateway);
        useCase.execute(classId, studentId);
    }

    public void addTeacherToClass(Long classId, Long teacherId) {
        ClassGateway classGateway = new ClassGateway(classDataSource);
        UserGateway userGateway = new UserGateway(userDataSource);
        AddTeacherToClassUseCase useCase = new AddTeacherToClassUseCase(classGateway, userGateway);
        useCase.execute(classId, teacherId);
    }

    public ClassResponse createClass(CreateClassRequest createClassRequest) {
        CourseGateway courseGateway = new CourseGateway(courseDataSource);
        ClassGateway classGateway = new ClassGateway(classDataSource);
        CreateClassUseCase useCase = new CreateClassUseCase(courseGateway, classGateway);
        Class clazz = useCase.execute(createClassRequest);
        return ClassMapper.toResponse(clazz);
    }

    public void deleteClass(Long classId) {
        ClassGateway classGateway = new ClassGateway(classDataSource);
        DeleteClassUseCase useCase = new DeleteClassUseCase(classGateway);
        useCase.execute(classId);
    }

    public void removeStudentFromClass(Long classId, Long studentId) {
        ClassGateway classGateway = new ClassGateway(classDataSource);
        UserGateway userGateway = new UserGateway(userDataSource);
        RemoveStudentFromClassUseCase useCase = new RemoveStudentFromClassUseCase(classGateway, userGateway);
        useCase.execute(classId, studentId);
    }

    public void removeTeacherFromClass(Long classId, Long teacherId) {
        ClassGateway classGateway = new ClassGateway(classDataSource);
        UserGateway userGateway = new UserGateway(userDataSource);
        RemoveTeacherFromClassUseCase useCase = new RemoveTeacherFromClassUseCase(classGateway, userGateway);
        useCase.execute(classId, teacherId);
    }
}
