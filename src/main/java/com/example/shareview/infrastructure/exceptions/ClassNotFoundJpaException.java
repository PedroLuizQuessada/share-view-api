package com.example.shareview.infrastructure.exceptions;

public class ClassNotFoundJpaException extends RuntimeException {
    public ClassNotFoundJpaException(Long id) {
      super(String.format("Classe %d não encontrada", id));
    }
}
