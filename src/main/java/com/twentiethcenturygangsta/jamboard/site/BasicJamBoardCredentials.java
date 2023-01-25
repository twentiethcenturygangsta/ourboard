package com.twentiethcenturygangsta.jamboard.site;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor
public class BasicJamBoardCredentials {

    public Connection register(String url, String userName, String password) {
        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            log.info("get connection = {}, class ={}", connection, connection.getClass());

            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
