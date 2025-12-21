package com.example.shareview.exceptions;

public class BadArgumentException extends BadRequestException {
    public BadArgumentException(String message) {
        super(message);
    }
}
