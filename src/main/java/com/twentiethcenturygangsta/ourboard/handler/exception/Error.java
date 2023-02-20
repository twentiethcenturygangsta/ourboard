package com.twentiethcenturygangsta.ourboard.handler.exception;

import lombok.Builder;
import lombok.Data;


@Data
public class Error {
    private String timestamp;
    private String code;
    private String field;
    private String message;

    @Builder
    public Error(String timestamp, String code, String field, String message) {
        this.timestamp = timestamp;
        this.code = code;
        this.field = field;
        this.message = message;
    }
}
