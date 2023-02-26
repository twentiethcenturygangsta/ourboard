package com.twentiethcenturygangsta.ourboard.dto;

import com.twentiethcenturygangsta.ourboard.entity.DatabaseRelationType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FieldInfo {

    private final String databaseFieldName;
    private final String description;
    private final Class<?> type;
    private final DatabaseRelationType databaseRelationType;
    private final DatabaseColumn databaseColumn;

    @Builder
    public FieldInfo (String databaseFieldName, String description, Class<?> type, DatabaseRelationType databaseRelationType, DatabaseColumn databaseColumn) {
        this.databaseFieldName = databaseFieldName;
        this.description = description;
        this.type = type;
        this.databaseRelationType = databaseRelationType;
        this.databaseColumn = databaseColumn;
    }


}
