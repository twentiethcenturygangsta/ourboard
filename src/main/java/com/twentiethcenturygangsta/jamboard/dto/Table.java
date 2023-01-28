package com.twentiethcenturygangsta.jamboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Slf4j
public class Table {
    private final List<String> fields;
    private final List<List<String>> datasets;

    @Builder
    public Table (List<String> fields, List<List<String>> dataset) {
        this.fields = fields;
        this.datasets = dataset;
    }

}
