package com.twentiethcenturygangsta.ourboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Slf4j
public class Entity {
    private Class<?> entityClass;
    private String idType;

    @Builder
    public Entity(Class<?> entityClass, String idType) {
        this.entityClass = entityClass;
        this.idType = idType;
    }

}
