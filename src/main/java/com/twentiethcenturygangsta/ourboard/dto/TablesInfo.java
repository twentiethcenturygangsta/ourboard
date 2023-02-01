package com.twentiethcenturygangsta.ourboard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TablesInfo {
    private String tableName;
    private String description;

    @Builder
    public TablesInfo(String tableName, String description) {
        this.tableName = tableName;
        this.description = description;
    }
}
