package org.lavertis.tasktrackerapi.exceptions;

public class NotFoundException extends ApiRequestException {
    public NotFoundException(String message) {
        super(message);
    }
}
