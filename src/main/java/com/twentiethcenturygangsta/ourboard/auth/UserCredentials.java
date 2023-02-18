package com.twentiethcenturygangsta.ourboard.auth;

import lombok.Getter;

@Getter
public class UserCredentials {

    private final String userName;
    private final String password;
    private final Role role;

    public UserCredentials(String userName, String password) {
        if (userName == null) {
            throw new IllegalArgumentException("Username cannot be null.");
        }
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        this.userName = userName;
        this.password = password;
        this.role = Role.SUPER_USER;
    }
}
