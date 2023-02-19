package com.twentiethcenturygangsta.ourboard.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class OurBoardExceptionControllerAdvice {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Error> UserExceptionHandle(UserException e) {
        String timestamp = String.valueOf(LocalDateTime.now());
        Error errorResult = Error.builder()
                .timestamp(timestamp)
                .code(e.getExceptionCode().getCode())
                .field(e.getExceptionCode().getField())
                .message(e.getExceptionCode().getMessage())
                .build();
        return new ResponseEntity<>(errorResult, e.getExceptionCode().getStatusCode());
    }
}
