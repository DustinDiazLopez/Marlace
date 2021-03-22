package com.example.marlace.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MarlaceResourceNotFoundException extends RuntimeException {
    public MarlaceResourceNotFoundException(String message) {
        super(message);
    }
}
