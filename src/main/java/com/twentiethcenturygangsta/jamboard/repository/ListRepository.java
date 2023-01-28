package com.twentiethcenturygangsta.jamboard.repository;

import com.twentiethcenturygangsta.jamboard.dto.Table;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class ListRepository {
    private final String query = "SELECT * FROM ";

    public Table findAll(String tableName, Connection connection) throws SQLException {
        List<String> columns = new ArrayList<>();
        List<List<String>> dataset = new ArrayList<>();

        String sql = query + tableName + ";";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        if (resultSet.next()) {
            ArrayList<String> row = new ArrayList<>();
            for(int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                columns.add(resultSetMetaData.getColumnName(i));
                row.add(resultSet.getString(resultSetMetaData.getColumnName(i)));
            }
            dataset.add(row);
        }
        while(resultSet.next()) {
            ArrayList<String> row = new ArrayList<>();
            for(int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                row.add(resultSet.getString(resultSetMetaData.getColumnName(i)));
            }
            dataset.add(row);
        }
        return Table.builder()
                .fields(columns)
                .dataset(dataset)
                .build();
    }
}
