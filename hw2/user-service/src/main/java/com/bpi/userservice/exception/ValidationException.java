package com.bpi.userservice.exception;

import com.bpi.userservice.entity.User;

public class ValidationException extends RuntimeException {
    public ValidationException (String message) {
        super(message);
    }

    }
