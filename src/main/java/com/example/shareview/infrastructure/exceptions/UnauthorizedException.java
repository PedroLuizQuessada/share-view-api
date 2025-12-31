package com.example.shareview.infrastructure.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Autenticação inválida.");
    }
}
