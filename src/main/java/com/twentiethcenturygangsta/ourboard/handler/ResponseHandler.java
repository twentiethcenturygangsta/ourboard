package com.twentiethcenturygangsta.ourboard.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(HttpStatus status, Object object) {
        String timestamp = String.valueOf(LocalDateTime.now());
        Map<String, Object> responseData = new HashMap<>();

        responseData.put("message", status.value());
        responseData.put("result", object);
        responseData.put("timestamp", timestamp);

        return new ResponseEntity<>(responseData, status);
    }
}
