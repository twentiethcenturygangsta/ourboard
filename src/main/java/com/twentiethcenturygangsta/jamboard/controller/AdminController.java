package com.twentiethcenturygangsta.jamboard.controller;

import com.twentiethcenturygangsta.jamboard.services.TableService;
import com.twentiethcenturygangsta.jamboard.site.JamBoardClient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.sql.*;
import java.util.ArrayList;

@Slf4j
@Configuration(proxyBeanMethods = false)
@Controller
@RequiredArgsConstructor
public class AdminController {
    private final JamBoardClient jamBoardClient;
    private final TableService tableService;

    @GetMapping("/admin")
    public String responseView(Model model) throws SQLException {
        ArrayList<String> tableSet = new ArrayList<>();
        ArrayList<ArrayList<String>> dataList = new ArrayList<>();
        Connection connection = jamBoardClient.getConnection();
        log.info("module connection = {}", connection);
        for (Class table : jamBoardClient.getTables()) {
            tableSet.add(table.getSimpleName());
            String sql = "SELECT * FROM " + table.getSimpleName() + ";";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            if(resultSet.next()) {
                ArrayList<String> temp = new ArrayList<>();
                for(int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    temp.add(resultSetMetaData.getColumnName(i)+
                            "  " +
                            resultSetMetaData.getColumnType(i) +
                            "  " +
                            resultSet.getString(resultSetMetaData.getColumnName(i)));

                    log.info(resultSetMetaData.getColumnName(i)+
                            "  " +
                            resultSetMetaData.getColumnType(i) +
                            "  " +
                            resultSet.getString(resultSetMetaData.getColumnName(i)));
                }
                dataList.add(temp);
            }

        }
        log.info("tables in controller = {}", tableSet);
        model.addAttribute("data", tableSet);
        model.addAttribute("data2", dataList);
        return "hello";
    }
}
