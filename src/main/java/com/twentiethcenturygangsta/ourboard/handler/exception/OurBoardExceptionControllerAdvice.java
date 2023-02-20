package com.twentiethcenturygangsta.ourboard.handler.exception;


import org.springframework.http.ResponseEntity;
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
                .message(e.getExceptionCode().getMessage())
                .build();
        return new ResponseEntity<>(errorResult, e.getExceptionCode().getStatusCode());
    }
}
