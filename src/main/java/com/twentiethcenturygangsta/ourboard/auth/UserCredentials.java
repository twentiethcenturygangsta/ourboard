package com.twentiethcenturygangsta.ourboard.auth;

import com.twentiethcenturygangsta.ourboard.config.EncryptionConfig;
import lombok.Getter;

@Getter
public class UserCredentials {

    private final String memberId;
    private final String password;
    private final Role role;

    public UserCredentials(String encryptKey, String memberId, String password) throws Exception {
        if (encryptKey == null) {
            throw new IllegalArgumentException("EncryptKey cannot be null");
        }
        if (memberId == null) {
            throw new IllegalArgumentException("MemberId cannot be null.");
        }
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        setKey(encryptKey);
        this.memberId = memberId;
        this.password = EncryptionConfig.encrypt(password);
        this.role = Role.SUPER_USER;
    }

    private void setKey(String key) {
        EncryptionConfig.key = key;
    }
}
