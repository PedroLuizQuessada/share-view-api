package com.example.shareview.datasources;

import dtos.ClassDto;
import dtos.CourseDto;
import dtos.UserDto;

import java.util.List;
import java.util.Optional;

public interface ClassDataSource {
    ClassDto createClass(ClassDto classDto);
    Optional<ClassDto> findClassById(Long id);
    void addTeacher(ClassDto classDto, UserDto userDto);
    void addStudent(ClassDto classDto, UserDto userDto);
    void removeTeacher(ClassDto classDto, UserDto userDto);
    void removeStudent(ClassDto classDto, UserDto userDto);
    List<ClassDto> findClassesFromStudent(UserDto userDto);
    List<ClassDto> findClassesFromTeacher(UserDto userDto);
    Long countClassesFromCourse(CourseDto courseDto);
    void deleteClass(ClassDto classDto);
}
