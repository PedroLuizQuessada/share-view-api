package com.example.shareview.entities;

import com.example.shareview.exceptions.BadArgumentException;
import enums.UserType;
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
        if (Objects.isNull(teachers))
            return new ArrayList<>();

        return teachers.stream()
                .map(User::new)
                .collect(java.util.stream.Collectors.toList());
    }

    public void addTeacher(User addTeacher) {
        validateTeacher(addTeacher);
        if (Objects.isNull(this.teachers))
            this.teachers = new ArrayList<>();

        if (teachers.stream().anyMatch(teacher -> teacher.getId().equals(addTeacher.getId())))
            throw new BadArgumentException("Professor já consta na classe.");

        this.teachers.add(addTeacher);
    }

    public void removeTeacher(User removeTeacher) {
        validateTeacher(removeTeacher);
        if (Objects.isNull(this.teachers))
            this.teachers = new ArrayList<>();

        boolean removed = this.teachers.removeIf(teacher -> teacher.getId().equals(removeTeacher.getId()));
        if (!removed)
            throw new BadArgumentException("Professor não consta na classe.");
    }

    public List<User> getStudentsCopy() {
        if (Objects.isNull(students))
            return new ArrayList<>();

        return students.stream()
                .map(User::new)
                .collect(java.util.stream.Collectors.toList());
    }

    public void addStudent(User addStudent) {
        validateStudent(addStudent);
        if (Objects.isNull(this.students))
            this.students = new ArrayList<>();

        if (students.stream().anyMatch(student -> student.getId().equals(addStudent.getId())))
            throw new BadArgumentException("Aluno já consta na classe.");

        this.students.add(addStudent);
    }

    public void removeStudent(User removeStudent) {
        validateStudent(removeStudent);
        if (Objects.isNull(this.students))
            this.students = new ArrayList<>();

        boolean removed = this.students.removeIf(student -> student.getId().equals(removeStudent.getId()));
        if (!removed)
            throw new BadArgumentException("Aluno não consta na classe.");
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
        if (!teacher.getUserType().equals(UserType.TEACHER))
            throw new BadArgumentException(String.format("Professor %s inválido.", teacher.getEmail()));
    }

    private void validateStudents(List<User> students) {
        if (!Objects.isNull(students)) {
            students.forEach(this::validateStudent);
        }
    }

    private void validateStudent(User student) {
        if (!student.getUserType().equals(UserType.STUDENT))
            throw new BadArgumentException(String.format("Aluno %s inválido.", student.getEmail()));
    }

    private void validateStartDate(Date startDate) {
        if (Objects.isNull(startDate))
            throw new BadArgumentException("A turma deve possuir uma data de início.");
    }
}
