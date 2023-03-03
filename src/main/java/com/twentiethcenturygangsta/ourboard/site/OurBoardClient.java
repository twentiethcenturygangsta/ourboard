package com.twentiethcenturygangsta.ourboard.site;

import com.twentiethcenturygangsta.ourboard.auth.UserCredentials;
import com.twentiethcenturygangsta.ourboard.auth.UserDatabaseCredentials;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;


@Slf4j
public class OurBoardClient {
    private final UserDatabaseCredentials userDatabaseCredentials;
    private final UserCredentials userCredentials;
    private final Connection connection;

    @Builder
    public OurBoardClient(UserDatabaseCredentials userDatabaseCredentials, UserCredentials userCredentials) {
        this.userDatabaseCredentials = userDatabaseCredentials;
        this.userCredentials = userCredentials;
        this.connection = connectDB(userDatabaseCredentials);
    }

    public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    public Connection getConnection() {
        return connection;
    }

    public Connection connectDB(UserDatabaseCredentials userDatabaseCredentials) {
        try {
            return DriverManager.getConnection(
                    userDatabaseCredentials.getUserDatabaseEndpoint(),
                    userDatabaseCredentials.getUserDatabaseId(),
                    userDatabaseCredentials.getUserDatabasePassword()
            );
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Deprecated
    public void register(ArrayList<Class> tables) throws SQLException {
        connectDB(userDatabaseCredentials);
    }
}
