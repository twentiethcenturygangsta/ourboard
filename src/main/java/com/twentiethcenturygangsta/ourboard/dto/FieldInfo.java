package com.twentiethcenturygangsta.ourboard.dto;

import com.twentiethcenturygangsta.ourboard.entity.DatabaseRelationType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FieldInfo {

    private final String databaseFieldName;
    private final String description;
    private final DatabaseRelationType databaseRelationType;

    @Builder
    public FieldInfo (String databaseFieldName, String description, DatabaseRelationType databaseRelationType) {
        this.databaseFieldName = databaseFieldName;
        this.description = description;
        this.databaseRelationType = databaseRelationType;
    }


}
