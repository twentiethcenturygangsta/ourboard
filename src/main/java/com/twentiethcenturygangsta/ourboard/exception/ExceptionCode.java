package com.twentiethcenturygangsta.ourboard.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ExceptionCode {

    INVALID_INPUT_OUR_BOARD_MEMBER_NAME(HttpStatus.BAD_REQUEST, "INVALID_INPUT_OUR_BOARD_MEMBER_NAME", "memberId", "입력한 아이디가 올바르지 않습니다.");

    private final HttpStatus statusCode;
    private final String code;
    private final String field;
    private final String message;

    ExceptionCode(HttpStatus httpStatus, String code, String field, String message) {
        this.statusCode = httpStatus;
        this.code = code;
        this.field = field;
        this.message = message;
    }
}
