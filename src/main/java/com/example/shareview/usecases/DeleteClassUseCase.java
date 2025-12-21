package com.example.shareview.usecases;

import com.example.shareview.entities.Class;
import com.example.shareview.gateways.ClassGateway;
import com.example.shareview.mappers.ClassMapper;

public class DeleteClassUseCase {

    private final ClassGateway classGateway;

    public DeleteClassUseCase(ClassGateway classGateway) {
        this.classGateway = classGateway;
    }

    public void execute(Long classId) {
        Class clazz = classGateway.findClassById(classId);
        classGateway.deleteClass(ClassMapper.toDto(clazz));
    }
}
