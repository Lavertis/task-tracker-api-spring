package com.lavertis.tasktrackerapi.exceptions;

public class BadRequestException extends ApiRequestException {
    public BadRequestException(String message) {
        super(message);
    }
}
