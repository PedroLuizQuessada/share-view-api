package com.example.shareview.mappers;

import com.example.shareview.dtos.ClassDto;
import com.example.shareview.dtos.responses.ClassResponse;
import com.example.shareview.entities.Class;
import com.example.shareview.entities.User;

import java.util.Objects;

public class ClassMapper {

    private ClassMapper() {}

    public static ClassDto toDto(Class clazz) {
        return new ClassDto(clazz.getId(),
                Objects.isNull(clazz.getCourse()) ? null : CourseMapper.toDto(clazz.getCourse()),
                Objects.isNull(clazz.getTeachersCopy()) ? null : clazz.getTeachersCopy().stream().map(UserMapper::toDto).toList(),
                Objects.isNull(clazz.getStudentsCopy()) ? null : clazz.getStudentsCopy().stream().map(UserMapper::toDto).toList(),
                clazz.getStartDate());
    }

    public static Class toEntity(ClassDto classDto) {
        return new Class(classDto.id(),
                Objects.isNull(classDto.course()) ? null : CourseMapper.toEntity(classDto.course()),
                Objects.isNull(classDto.teachers()) ? null : classDto.teachers().stream().map((teacher) -> UserMapper.toEntity(teacher, false)).toList(),
                Objects.isNull(classDto.students()) ? null : classDto.students().stream().map((student) -> UserMapper.toEntity(student, false)).toList(),
                classDto.startDate());
    }

    public static ClassResponse toResponse(Class clazz) {
        return new ClassResponse(clazz.getId(), clazz.getCourse().getId(),
                clazz.getTeachersCopy().stream().map(User::getId).toList(),
                clazz.getStudentsCopy().stream().map(User::getId).toList(),
                clazz.getStartDate());
    }
}
