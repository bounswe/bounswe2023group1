package com.groupa1.resq.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class EntityNotAllowedException extends RuntimeException  {
    public EntityNotAllowedException(String message) {
        super(message);
    }
}
