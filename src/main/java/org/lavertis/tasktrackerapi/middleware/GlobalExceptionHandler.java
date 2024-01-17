package org.lavertis.tasktrackerapi.middleware;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.lavertis.tasktrackerapi.exceptions.ForbiddenRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, fieldError -> Optional
                        .ofNullable(fieldError.getDefaultMessage())
                        .orElse("No message"))
                );
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, Map<String, String>> getErrorsMap(Map<String, String> errors) {
        Map<String, Map<String, String>> response = new HashMap<>();
        response.put("errors", errors);
        return response;
    }

    @ExceptionHandler(ForbiddenRequestException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ResponseEntity<Object> handleForbiddenRequestException(ForbiddenRequestException ex) {
        var msg = ex.getLocalizedMessage();
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, msg, msg);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}

@Getter
@AllArgsConstructor
class ApiError {
    private final HttpStatus status;
    private final String message;
    private final List<String> errors;

    public ApiError(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        errors = List.of(error);
    }
}