package com.empresa.entrevista.exceptions;

public class PasswordPatternException extends RuntimeException {
    public PasswordPatternException(String message) {
        super(message);
    }
}
