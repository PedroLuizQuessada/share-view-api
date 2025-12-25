package com.example.shareview.exceptions;

public class ClassNotFoundException extends NotFoundException {
    public ClassNotFoundException(Long id) {
        super(String.format("Classe %d não encontrada", id));
    }
}
