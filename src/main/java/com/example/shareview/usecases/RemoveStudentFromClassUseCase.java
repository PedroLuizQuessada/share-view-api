package com.example.shareview.usecases;

import com.example.shareview.entities.Class;
import com.example.shareview.entities.User;
import com.example.shareview.gateways.ClassGateway;
import com.example.shareview.gateways.UserGateway;
import com.example.shareview.mappers.ClassMapper;
import com.example.shareview.mappers.UserMapper;

public class RemoveStudentFromClassUseCase {
    private final ClassGateway classGateway;
    private final UserGateway userGateway;

    public RemoveStudentFromClassUseCase(ClassGateway classGateway, UserGateway userGateway) {
        this.classGateway = classGateway;
        this.userGateway = userGateway;
    }

    public void execute(Long classId, Long studentId) {
        Class clazz = classGateway.findClassById(classId);
        User user = userGateway.findUserById(studentId);
        clazz.removeStudent(user);
        classGateway.removeStudent(ClassMapper.toDto(clazz), UserMapper.toDto(user));
    }
}
