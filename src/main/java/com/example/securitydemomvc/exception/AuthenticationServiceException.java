package com.example.securitydemomvc.exception;

public class AuthenticationServiceException extends RuntimeException{
    public AuthenticationServiceException(String message) {
        super(message);
    }
}
