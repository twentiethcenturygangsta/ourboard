package com.github.twentiethcenturygangsta.ourboard.auth;

import com.github.twentiethcenturygangsta.ourboard.config.EncryptionConfig;
import lombok.Getter;


/**
 * credentials to receive OurBoardMember account information to log in to the dashboard
 *
 * @author oereo, junhyeok
 * @version 1.0.0
 */
@Getter
public class UserCredentials {

    private final String memberId;
    private final String password;
    private final Role role;

    /**
     * Constructs a new UserDatabaseCredentials object, with the specified user database
     * endpoint, id and password.
     *
     * @param encryptKey
     *            The password encryption key
     * @param memberId
     *            The user login id.
     * @param password
     *            The user login password.
     *
     * @exception IllegalArgumentException when there is no value among encryptKey, memberId, password
     */
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
