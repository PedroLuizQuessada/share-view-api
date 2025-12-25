package com.example.shareview.infrastructure.persistence.jpa.mappers;

import com.example.shareview.dtos.ClassDto;
import com.example.shareview.infrastructure.persistence.jpa.models.ClassJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Profile("jpa")
public class ClassJpaDtoMapper {

    @Autowired
    private CourseJpaDtoMapper courseJpaDtoMapper;

    @Autowired
    private UserJpaDtoMapper userJpaDtoMapper;

    public ClassJpa toClassJpa(ClassDto classDto) {
        return new ClassJpa(classDto.id(),
                Objects.isNull(classDto.course()) ? null : courseJpaDtoMapper.toCourseJpa(classDto.course()),
                Objects.isNull(classDto.students()) ? null : classDto.students().stream().map((user) -> userJpaDtoMapper.toUserJpa(user)).toList(),
                Objects.isNull(classDto.teachers()) ? null : classDto.teachers().stream().map((user) -> userJpaDtoMapper.toUserJpa(user)).toList(),
                new java.sql.Date(classDto.startDate().getTime()));
    }

    public ClassDto toClassDto(ClassJpa classJpa) {
        return new ClassDto(classJpa.getId(),
                Objects.isNull(classJpa.getCourseJpa()) ? null : courseJpaDtoMapper.toCourseDto(classJpa.getCourseJpa()),
                Objects.isNull(classJpa.getStudentsJpa()) ? null : classJpa.getStudentsJpa().stream().map((user) -> userJpaDtoMapper.toUserDto(user)).toList(),
                Objects.isNull(classJpa.getTeachersJpa()) ? null : classJpa.getTeachersJpa().stream().map((user) -> userJpaDtoMapper.toUserDto(user)).toList(),
                new java.util.Date(classJpa.getStartDate().getTime()));
    }

}
