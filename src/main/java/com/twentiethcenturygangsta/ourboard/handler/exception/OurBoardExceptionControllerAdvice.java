package com.twentiethcenturygangsta.ourboard.handler.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OurBoardExceptionControllerAdvice {

    @ExceptionHandler(OurBoardException.class)
    public ResponseEntity<Error> UserExceptionHandle(OurBoardException e) {
        String timestamp = String.valueOf(LocalDateTime.now());
        Error errorResult = Error.builder()
                .timestamp(timestamp)
                .code(e.getExceptionCode().getCode())
                .message(e.getExceptionCode().getMessage())
                .build();
        log.info("OurBoardException = {}", e.getExceptionCode().getStatusCode());
        return new ResponseEntity<>(errorResult, e.getExceptionCode().getStatusCode());

    }
}
