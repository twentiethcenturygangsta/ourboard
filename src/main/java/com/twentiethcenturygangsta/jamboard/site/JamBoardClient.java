package com.twentiethcenturygangsta.jamboard.site;

import com.twentiethcenturygangsta.jamboard.auth.UserCredentials;
import com.twentiethcenturygangsta.jamboard.database.UserDatabaseCredentials;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;


import java.sql.*;
import java.util.ArrayList;


@Slf4j
public class JamBoardClient {
    private final UserDatabaseCredentials userDatabaseCredentials;
    private final UserCredentials userCredentials;
    private Connection connection;
    private ArrayList<Class> tables;

    @Builder
    public JamBoardClient(UserDatabaseCredentials userDatabaseCredentials, UserCredentials userCredentials) {
        this.userDatabaseCredentials = userDatabaseCredentials;
        this.userCredentials = userCredentials;
    }

    public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    public Connection getConnection() {
        return connection;
    }

    public ArrayList<Class> getTables() {
        return tables;
    }

    public void connectDB(UserDatabaseCredentials userDatabaseCredentials) {
        try {
            Connection connection = DriverManager.getConnection(
                    userDatabaseCredentials.getUserDatabaseEndpoint(),
                    userDatabaseCredentials.getUserDatabaseId(),
                    userDatabaseCredentials.getUserDatabasePassword()
            );
            this.connection = connection;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void register(ArrayList<Class> tables) throws SQLException {
        connectDB(userDatabaseCredentials);
        log.info("connection = OK");
        this.tables = tables;
    }
}
