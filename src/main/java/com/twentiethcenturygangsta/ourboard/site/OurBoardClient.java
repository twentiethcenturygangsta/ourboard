package com.twentiethcenturygangsta.ourboard.site;

import com.twentiethcenturygangsta.ourboard.auth.UserCredentials;
import com.twentiethcenturygangsta.ourboard.database.UserDatabaseCredentials;
import com.twentiethcenturygangsta.ourboard.annoatation.OurBoardEntity;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.sql.*;
import java.util.ArrayList;
import java.util.Set;


@Slf4j
public class OurBoardClient {
    private final String ourBoardBasePackage = "com.twentiethcenturygangsta.ourboard";
    private final UserDatabaseCredentials userDatabaseCredentials;
    private final UserCredentials userCredentials;
    private final String basePackagePath;
    private Connection connection;
    private final Set<Class<?>> tables;

    @Builder
    public OurBoardClient(UserDatabaseCredentials userDatabaseCredentials, UserCredentials userCredentials, String basePackagePath) throws SQLException {
        this.userDatabaseCredentials = userDatabaseCredentials;
        this.userCredentials = userCredentials;
        this.basePackagePath = basePackagePath;
        this.tables = registerTables(basePackagePath);

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

    public Set<Class<?>> registerTables(String basePackagePath) {
        Set<Class<?>> baseClasses = new Reflections(ourBoardBasePackage).getTypesAnnotatedWith(OurBoardEntity.class);
        baseClasses.addAll(new Reflections(basePackagePath).getTypesAnnotatedWith(OurBoardEntity.class));
        return baseClasses;
    }

    @Deprecated
    public void register(ArrayList<Class> tables) throws SQLException {
        connectDB(userDatabaseCredentials);
        log.info("connection = OK");
    }
}
