package com.metrodata.serverapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.metrodata.serverapp.model.response.ErrorMessage;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomException.class)
    ResponseEntity<ErrorMessage> customExceptionHandler(CustomException customException) {
        return new ResponseEntity<>(
            ErrorMessage.builder()
                .message(customException.getMessage())
                .code(customException.getCode())
                .build(), 
            HttpStatus.valueOf(customException.getStatus())
        );
    }
}