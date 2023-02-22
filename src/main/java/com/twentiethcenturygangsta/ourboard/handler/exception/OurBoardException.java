package com.twentiethcenturygangsta.ourboard.handler.exception;


import lombok.Getter;

@Getter
public class OurBoardException extends RuntimeException{
    private ExceptionCode exceptionCode;

    public OurBoardException() {
        super();
    }

    public OurBoardException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public OurBoardException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public OurBoardException(String message, Throwable cause) {
        super(message,cause);
    }

    public OurBoardException(Throwable cause) {
        super(cause);
    }

    protected OurBoardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
