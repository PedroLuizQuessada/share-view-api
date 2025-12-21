package com.example.shareview.usecases;

import com.example.shareview.entities.Class;
import com.example.shareview.entities.User;
import com.example.shareview.gateways.ClassGateway;
import com.example.shareview.gateways.TokenGateway;
import com.example.shareview.gateways.UserGateway;
import com.example.shareview.mappers.UserMapper;

import java.util.List;

public class DeleteUserUseCase {

    private final TokenGateway tokenGateway;
    private final UserGateway userGateway;
    private final ClassGateway classGateway;
    private final RemoveStudentFromClassUseCase removeStudentFromClassUseCase;
    private final RemoveTeacherFromClassUseCase removeTeacherFromClassUseCase;

    public DeleteUserUseCase(TokenGateway tokenGateway, UserGateway userGateway, ClassGateway classGateway) {
        this.tokenGateway = tokenGateway;
        this.userGateway = userGateway;
        this.classGateway = classGateway;
        this.removeStudentFromClassUseCase = new RemoveStudentFromClassUseCase(classGateway, userGateway);
        this.removeTeacherFromClassUseCase = new RemoveTeacherFromClassUseCase(classGateway, userGateway);
    }

    public void execute(String token) {
        String email = tokenGateway.getEmail(token);

        List<Class> classes;
        User user = userGateway.findUserByEmail(email);
        switch (user.getUserType()) {
            case STUDENT:
                classes = classGateway.findClassesFromStudent(UserMapper.toDto(user));
                classes.forEach((clazz) -> removeStudentFromClassUseCase.execute(clazz.getId(), user.getId()));
                break;

            case TEACHER:
                classes = classGateway.findClassesFromTeacher(UserMapper.toDto(user));
                classes.forEach((clazz) -> removeTeacherFromClassUseCase.execute(clazz.getId(), user.getId()));
                break;
        }
        userGateway.deleteUser(UserMapper.toDto(user));
    }
}
