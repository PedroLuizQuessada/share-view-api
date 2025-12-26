package com.example.shareview.gateways;

import com.example.shareview.datasources.ClassDataSource;
import com.example.shareview.entities.Class;
import com.example.shareview.exceptions.ClassNotFoundException;
import com.example.shareview.mappers.ClassMapper;
import dtos.ClassDto;
import dtos.CourseDto;
import dtos.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClassGateway {
    private final ClassDataSource classDataSource;

    public ClassGateway(ClassDataSource classDataSource) {
        this.classDataSource = classDataSource;
    }

    public Class createClass(ClassDto createClassDto) {
        ClassDto classDto = classDataSource.createClass(createClassDto);
        return ClassMapper.toEntity(classDto);
    }

    public Class findClassById(Long id) {
        Optional<ClassDto> classDtoOptional = classDataSource.findClassById(id);

        if (classDtoOptional.isEmpty())
            throw new ClassNotFoundException(id);

        return ClassMapper.toEntity(classDtoOptional.get());
    }

    public void addTeacher(ClassDto classDto, UserDto teacher) {
        classDataSource.addTeacher(classDto, teacher);
    }

    public void addStudent(ClassDto classDto, UserDto student) {
        classDataSource.addStudent(classDto, student);
    }

    public void removeTeacher(ClassDto classDto, UserDto teacher) {
        classDataSource.removeTeacher(classDto, teacher);
    }

    public void removeStudent(ClassDto classDto, UserDto teacher) {
        classDataSource.removeStudent(classDto, teacher);
    }

    public List<Class> findClassesFromStudent(UserDto student) {
        return classDataSource.findClassesFromStudent(student).stream().map(ClassMapper::toEntity).collect(Collectors.toList());
    }

    public List<Class> findClassesFromTeacher(UserDto teacher) {
        return classDataSource.findClassesFromTeacher(teacher).stream().map(ClassMapper::toEntity).collect(Collectors.toList());
    }

    public Long countClassesFromCourse(CourseDto courseDto) {
        return classDataSource.countClassesFromCourse(courseDto);
    }

    public void deleteClass(ClassDto classDto) {
        classDataSource.deleteClass(classDto);
    }
}
