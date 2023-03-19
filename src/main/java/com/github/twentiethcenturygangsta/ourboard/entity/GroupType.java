package com.github.twentiethcenturygangsta.ourboard.entity;


import lombok.Getter;

@Getter
public enum GroupType {
    AUTHENTICATION_AND_AUTHORIZATION(Constants.AUTHENTICATION_AND_AUTHORIZATION),
    DEFAULT(Constants.DEFAULT);

    GroupType(String groupType) {}

    public static class Constants {
        public static final String AUTHENTICATION_AND_AUTHORIZATION = "AUTHENTICATION AND AUTHORIZATION";
        public static final String DEFAULT = "YOUR TABLES";
    }
}
