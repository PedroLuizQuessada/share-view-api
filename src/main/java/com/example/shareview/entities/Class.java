package com.example.shareview.entities;

import com.example.shareview.enums.UserType;
import com.example.shareview.exceptions.BadArgumentException;
import com.example.shareview.exceptions.UserNotFoundException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Class {
    @Getter
    private final Long id;
    @Getter
    private final Course course;
    private List<User> teachers;
    private List<User> students;
    @Getter
    private final Date startDate;

    public Class(Long id, Course course, List<User> teachers, List<User> students, Date startDate) {
        String message = "";

        try {
            validateCourse(course);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateTeachers(teachers);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateStudents(students);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateStartDate(startDate);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadArgumentException(message);

        this.id = id;
        this.course = course;
        this.teachers = teachers;
        this.students = students;
        this.startDate = startDate;
    }

    public List<User> getTeachersCopy() {
        return teachers.stream()
                .map(User::new)
                .collect(java.util.stream.Collectors.toList());
    }

    public void addTeacher(User teacher) {
        validateTeacher(teacher);
        if (Objects.isNull(this.teachers))
            this.teachers = new ArrayList<>();
        this.teachers.add(teacher);
    }

    public void removeTeacher(User teacher) {
        validateTeacher(teacher);
        if (!this.teachers.contains(teacher)) {
            throw new UserNotFoundException(teacher.getEmail());
        }
        this.teachers.remove(teacher);
    }

    public List<User> getStudentsCopy() {
        return students.stream()
                .map(User::new)
                .collect(java.util.stream.Collectors.toList());
    }

    public void addStudent(User student) {
        validateStudent(student);
        if (Objects.isNull(this.students))
            this.students = new ArrayList<>();
        this.students.add(student);
    }

    public void removeStudent(User student) {
        validateStudent(student);
        if (!this.students.contains(student)) {
            throw new UserNotFoundException(student.getEmail());
        }
        this.students.remove(student);
    }

    private void validateCourse(Course course) {
        if (Objects.isNull(course))
            throw new BadArgumentException("A turma deve possuir um curso.");
    }

    private void validateTeachers(List<User> teachers) {
        if (!Objects.isNull(teachers)) {
            teachers.forEach(this::validateTeacher);
        }
    }

    private void validateTeacher(User teacher) {
        if (teacher.getUserType().equals(UserType.TEACHER))
            throw new BadArgumentException(String.format("Professor %s inválido.", teacher.getEmail()));
    }

    private void validateStudents(List<User> students) {
        if (!Objects.isNull(students)) {
            students.forEach(this::validateStudent);
        }
    }

    private void validateStudent(User student) {
        if (student.getUserType().equals(UserType.STUDENT))
            throw new BadArgumentException(String.format("Aluno %s inválido.", student.getEmail()));
    }

    private void validateStartDate(Date startDate) {
        if (Objects.isNull(startDate))
            throw new BadArgumentException("A turma deve possuir uma data de início.");
    }
}
