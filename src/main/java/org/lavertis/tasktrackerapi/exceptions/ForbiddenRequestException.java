package org.lavertis.tasktrackerapi.exceptions;

public class ForbiddenRequestException extends ApiRequestException {
    public ForbiddenRequestException(String message) {
        super(message);
    }
}
