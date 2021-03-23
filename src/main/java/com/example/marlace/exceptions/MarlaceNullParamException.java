package com.example.marlace.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MarlaceNullParamException extends RuntimeException {
    public MarlaceNullParamException(String message) {
        super(message);
    }
}
