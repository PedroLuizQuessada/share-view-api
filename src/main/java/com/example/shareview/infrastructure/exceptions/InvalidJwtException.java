package com.example.shareview.infrastructure.exceptions;

public class InvalidJwtException extends RuntimeException {
  public InvalidJwtException() {
    super("Token de acesso inválido");
  }
}
