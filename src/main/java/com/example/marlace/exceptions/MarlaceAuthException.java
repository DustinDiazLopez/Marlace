package com.example.marlace.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class MarlaceAuthException extends RuntimeException {
    public MarlaceAuthException(String message) {
        super(message);
    }
}
