package org.lavertis.tasktrackerapi.exceptions;

public abstract class ApiRequestException extends Exception {
    public ApiRequestException(String message) {
        super(message);
    }
}
