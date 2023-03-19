package com.github.twentiethcenturygangsta.ourboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Getter
@Slf4j
public class DatabaseSchema {
    private String name;
    private String schema;

    @Builder
    public DatabaseSchema (String name, String schema) {
        this.name = name;
        this.schema = schema;
    }
}
