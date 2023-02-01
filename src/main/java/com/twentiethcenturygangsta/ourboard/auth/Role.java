package com.twentiethcenturygangsta.ourboard.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    SUPER_USER("ROLE_SUPER_USER"),
    DEFAULT_USER("ROLE_DEFAULT_USER");

    private String value;
}
