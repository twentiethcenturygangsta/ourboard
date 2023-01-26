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

    @Builder
    public JamBoardClient(UserDatabaseCredentials userDatabaseCredentials, UserCredentials userCredentials) {
        this.userDatabaseCredentials = userDatabaseCredentials;
        this.userCredentials = userCredentials;
    }

    public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    public Connection register(ArrayList<Class> tables) {
        try {
            Connection connection = DriverManager.getConnection(
                    userDatabaseCredentials.getUserDatabaseEndpoint(),
                    userDatabaseCredentials.getUserDatabaseId(),
                    userDatabaseCredentials.getUserDatabasePassword()
            );
            log.info("tables = {}", tables);
            for (Class entity : tables) {
                String sql = "SELECT * FROM " + entity.getSimpleName() + ";";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();


                log.info("--------------------------------------------------------");
                log.info("query: {}", entity.getSimpleName());
                log.info("--------------------------------------------------------");
                if(resultSet.next()) {
                    for(int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                        log.info(resultSetMetaData.getColumnName(i)+
                                "  " +
                                resultSetMetaData.getColumnType(i) +
                                "  " +
                                resultSet.getString(resultSetMetaData.getColumnName(i)));
                    }
                }
            }
            log.info("tables = {}", tables);
            log.info("get connection = {}, class ={}", connection, connection.getClass());

            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
