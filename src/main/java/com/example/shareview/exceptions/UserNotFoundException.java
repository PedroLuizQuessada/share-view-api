package com.example.shareview.exceptions;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String email) {
        super(String.format("Usuário %s não encontrado", email));
    }
    public UserNotFoundException(Long id) {
        super(String.format("Usuário %d não encontrado", id));
    }
}
