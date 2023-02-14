package com.twentiethcenturygangsta.ourboard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TablesInfo {
    private String tableName;
    private String entityClassName;
    private String description;

    @Builder
    public TablesInfo(String tableName, String entityClassName, String description) {
        this.tableName = tableName;
        this.entityClassName = entityClassName;
        this.description = description;
    }
}
