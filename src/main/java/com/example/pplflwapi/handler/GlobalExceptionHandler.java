package com.example.pplflwapi.handler;

import com.example.pplflwapi.controller.model.ErrorResponse;
import com.example.pplflwapi.exception.EventProcessingException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        return createErrorResponse(e);
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse handleException(ObjectNotFoundException e) {
        return createErrorResponse(e);
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(EventProcessingException e) {
        return createErrorResponse(e);
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(MethodArgumentNotValidException e) {
        return createErrorResponse(e);
    }

    private ErrorResponse createErrorResponse(Exception e) {
        log.error(e.getMessage(), e);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        return errorResponse;
    }
}
