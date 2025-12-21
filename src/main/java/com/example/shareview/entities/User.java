package com.example.shareview.entities;

import com.example.shareview.enums.UserType;
import com.example.shareview.exceptions.BadArgumentException;
import lombok.Getter;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

@Getter
public class User {
    private final Long id;
    private String email;
    private String password;
    private final UserType userType;

    public User(Long id, String email, String password, UserType userType, boolean encodePassword) {
        String message = "";

        try {
            validateEmail(email);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validatePassword(password, encodePassword);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateUserType(userType);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadArgumentException(message);

        this.id = id;
        this.email = email;
        if (encodePassword) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            this.password = encoder.encode(password);
        }
        else {
            this.password = password;
        }
        this.userType = userType;
    }

    public User(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.userType = user.getUserType();
    }

    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    public void setPassword(String password) {
        validatePassword(password, true);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    private void validateEmail(String email) {
        if (Objects.isNull(email) || email.isEmpty())
            throw new BadArgumentException("O usuário deve possuir um e-mail.");

        if (email.length() > 45)
            throw new BadArgumentException("O e-mail do usuário deve possuir até 45 caracteres.");

        if (!EmailValidator.getInstance().isValid(email))
            throw new BadArgumentException("E-mail inválido.");
    }

    private void validatePassword(String password, boolean decodedPassword) {
        if (Objects.isNull(password) || password.isEmpty())
            throw new BadArgumentException("O usuário deve possuir uma senha.");

        if (decodedPassword && password.length() < 6)
            throw new BadArgumentException("A senha do usuário deve possuir ao menos 6 caracteres.");
    }

    private void validateUserType(UserType userType) {
        if (Objects.isNull(userType)) {
            throw new BadArgumentException("O usuário deve possuir um tipo de usuário.");
        }
    }
}
