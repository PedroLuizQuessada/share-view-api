package com.example.shareview.usecases;

import com.example.shareview.entities.Class;
import com.example.shareview.entities.User;
import com.example.shareview.gateways.ClassGateway;
import com.example.shareview.gateways.UserGateway;
import com.example.shareview.mappers.ClassMapper;
import com.example.shareview.mappers.UserMapper;

public class RemoveTeacherFromClassUseCase {
    private final ClassGateway classGateway;
    private final UserGateway userGateway;

    public RemoveTeacherFromClassUseCase(ClassGateway classGateway, UserGateway userGateway) {
        this.classGateway = classGateway;
        this.userGateway = userGateway;
    }

    public void execute(Long classId, Long teacherId) {
        Class clazz = classGateway.findClassById(classId);
        User user = userGateway.findUserById(teacherId);
        clazz.removeTeacher(user);
        classGateway.removeTeacher(ClassMapper.toDto(clazz), UserMapper.toDto(user));
    }
}
