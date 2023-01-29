package com.twentiethcenturygangsta.jamboard.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

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
