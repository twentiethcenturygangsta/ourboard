package com.twentiethcenturygangsta.ourboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Slf4j
public class Entity {
    private Class<?> entityClass;
    private Class<?> idType;

    @Builder
    public Entity(Class<?> entityClass, Class<?> idType) {
        this.entityClass = entityClass;
        this.idType = idType;
    }

}
