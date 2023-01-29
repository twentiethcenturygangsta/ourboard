package com.twentiethcenturygangsta.jamboard.site;

import com.twentiethcenturygangsta.jamboard.auth.UserCredentials;
import com.twentiethcenturygangsta.jamboard.database.UserDatabaseCredentials;
import com.twentiethcenturygangsta.jamboard.trace.JamBoardEntity;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;


import java.sql.*;
import java.util.ArrayList;
import java.util.Set;


@Slf4j
public class JamBoardClient {
    private final UserDatabaseCredentials userDatabaseCredentials;
    private final UserCredentials userCredentials;
    private final String basePackagePath;
    private Connection connection;
    private final Set<Class<?>> tables;

    @Builder
    public JamBoardClient(UserDatabaseCredentials userDatabaseCredentials, UserCredentials userCredentials, String basePackagePath) {
        this.userDatabaseCredentials = userDatabaseCredentials;
        this.userCredentials = userCredentials;
        this.basePackagePath = basePackagePath;
        this.tables = new Reflections(basePackagePath).getTypesAnnotatedWith(JamBoardEntity.class);

        connectDB(userDatabaseCredentials);
    }

    public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    public Connection getConnection() {
        return connection;
    }

    public Set<Class<?>> getTables() {
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

    @Deprecated
    public void register(ArrayList<Class> tables) throws SQLException {
        connectDB(userDatabaseCredentials);
        log.info("connection = OK");
    }
}
