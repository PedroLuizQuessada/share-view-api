package com.example.shareview.exceptions;

public class CourseNotFoundException extends NotFoundException {
    public CourseNotFoundException(Long id) {
        super(String.format("Curso %d não encontrado", id));
    }
}
