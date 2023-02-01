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
public class OurBoardClient {
    private final String ourBoardBasePackage = "com.twentiethcenturygangsta.jamboard";
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
        createAuthenticatedMember();
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
        Set<Class<?>> baseClasses = new Reflections(ourBoardBasePackage).getTypesAnnotatedWith(JamBoardEntity.class);
        baseClasses.addAll(new Reflections(basePackagePath).getTypesAnnotatedWith(JamBoardEntity.class));
        return baseClasses;
    }

    public void createAuthenticatedMember() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS OurBoardMember (" +
                "id BIGINT NOT NULL AUTO_INCREMENT," +
                "username VARCHAR(100) NOT NULL," +
                "hasCreateAuthority boolean," +
                "hasReadAuthority boolean, PRIMARY KEY (id)" +
                ");";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute();

        if (!isExistAuthenticatedMember()) {
            createAuthenticatedSuperMember();
        }
    }

    @Deprecated
    public void register(ArrayList<Class> tables) throws SQLException {
        connectDB(userDatabaseCredentials);
        log.info("connection = OK");
    }

    public boolean isExistAuthenticatedMember() throws SQLException {
        String sql = "SELECT * FROM OurBoardMember WHERE username = " + String.format("'%s'", userCredentials.getUserName());
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public void createAuthenticatedSuperMember() throws SQLException {
        String sql = String.format("INSERT INTO OurBoardMember (username, hasCreateAuthority, hasReadAuthority) " +
                "VALUES ('%s', true, true);", userCredentials.getUserName());
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute();
    }
}
