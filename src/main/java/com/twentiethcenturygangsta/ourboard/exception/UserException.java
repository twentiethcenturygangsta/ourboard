package com.twentiethcenturygangsta.ourboard.exception;


import lombok.Getter;

@Getter
public class UserException extends RuntimeException{
    private ExceptionCode exceptionCode;

    public UserException() {
        super();
    }

    public UserException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
