package com.example.shareview.entities;

import com.example.shareview.exceptions.BadArgumentException;
import lombok.Getter;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Getter
public class Feedback {
    private final Long id;
    private final User student;
    private final Class clazz;
    private final Integer rating;
    private final String description;
    private final Date evaluationDate;

    public Feedback(Long id, User student, Class clazz, Integer rating, String description) {
        String message = "";

        try {
            validateStudent(student);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateClass(clazz);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateRating(rating);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadArgumentException(message);

        this.id = id;
        this.student = student;
        this.clazz = clazz;
        this.rating = rating;
        this.description = description;
        this.evaluationDate = Date.from(Instant.now());
    }

    private void validateStudent(User student) {
        if (Objects.isNull(student))
            throw new BadArgumentException("O feedback deve possuir um aluno.");
    }

    private void validateClass(Class clazz) {
        if (Objects.isNull(clazz))
            throw new BadArgumentException("O feedback deve possuir uma classe.");
    }

    private void validateRating(Integer rating) {
        if (Objects.isNull(rating))
            throw new BadArgumentException("O feedback deve possuir uma nota.");

        if (rating < 0 || rating > 10)
            throw new BadArgumentException("A nota do feedback deve ser um valor entre 0 e 10.");
    }
}
