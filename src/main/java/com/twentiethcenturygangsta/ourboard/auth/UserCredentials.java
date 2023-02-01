package com.twentiethcenturygangsta.ourboard.auth;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
public class UserCredentials {

    private final String memberId;
    private final String password;

    public UserCredentials(String memberId, String password) {
        if (memberId == null) {
            throw new IllegalArgumentException("MemberId cannot be null.");
        }
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        this.memberId = memberId;
        this.password = password;
    }
}
