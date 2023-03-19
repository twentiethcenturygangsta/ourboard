package com.github.twentiethcenturygangsta.ourboard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DatabaseColumn {
    private final String name;
    private final String type;
    private final String label;
    private final String displaySize;
    private final String typeName;

    @Builder
    public DatabaseColumn(String name, String type, String label, String displaySize, String typeName) {
        this.name = name;
        this.type = type;
        this.label = label;
        this.displaySize = displaySize;
        this.typeName = typeName;
    }
}
