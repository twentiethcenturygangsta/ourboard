package com.twentiethcenturygangsta.ourboard.site;

import com.twentiethcenturygangsta.ourboard.auth.UserCredentials;
import com.twentiethcenturygangsta.ourboard.database.UserDatabaseCredentials;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;


@Slf4j
public class OurBoardClient {
    private final UserDatabaseCredentials userDatabaseCredentials;
    private final UserCredentials userCredentials;
    private Connection connection;

    @Builder
    public OurBoardClient(UserDatabaseCredentials userDatabaseCredentials, UserCredentials userCredentials, String basePackagePath) throws SQLException {
        this.userDatabaseCredentials = userDatabaseCredentials;
        this.userCredentials = userCredentials;
        connectDB(userDatabaseCredentials);
    }

    public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    public Connection getConnection() {
        return connection;
    }

//    public Set<Class<?>> getTables() {
//        return tables;
//    }

    public void connectDB(UserDatabaseCredentials userDatabaseCredentials) {
        try {
            this.connection = DriverManager.getConnection(
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
        log.info("connection = OK");
    }
}
