package com.example.shareview.entities;

import com.example.shareview.exceptions.BadArgumentException;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
public class Course {
    private final Long id;
    private String name;
    @Setter
    private String description;

    public Course(Long id, String name, String description) {
        String message = "";

        try {
            validateName(name);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadArgumentException(message);

        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isEmpty())
            throw new BadArgumentException("O curso deve possuir um nome.");
    }
}
