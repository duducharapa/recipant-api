package com.charapadev.recipant.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@RestControllerAdvice
@AllArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(
        HttpRequestMethodNotSupportedException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ) {
        ErrorDetail error = new ErrorDetail(
            new Date().getTime(),
            status.value(),
            "Message not readable",
            ex.getMessage(),
            "",
            new HashMap<>()
        );

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorDetail error = new ErrorDetail(
            new Date().getTime(),
            HttpStatus.NOT_FOUND.value(),
            "Resource not found",
            ex.getMessage(),
            "",
            new HashMap<>()
        );

        return new ResponseEntity<>(error, null, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Map<String, List<ValidationError>> errors = new HashMap<>();

        for (FieldError fe: fieldErrors) {
            List<ValidationError> validationErrorList = errors.computeIfAbsent(fe.getField(), k -> new ArrayList<>());

            ValidationError validationError = new ValidationError(
                fe.getCode(),
                messageSource.getMessage(fe, Locale.getDefault())
            );

            validationErrorList.add(validationError);
        }

        ErrorDetail error = new ErrorDetail(
            new Date().getTime(),
            HttpStatus.BAD_REQUEST.value(),
            "Validation failed",
            "Input validation failed",
            "",
            errors
        );

        return handleExceptionInternal(ex, error, headers, status, request);
    }

}
