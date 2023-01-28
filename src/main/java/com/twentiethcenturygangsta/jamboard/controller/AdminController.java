package com.twentiethcenturygangsta.jamboard.controller;

import com.twentiethcenturygangsta.jamboard.dto.Table;
import com.twentiethcenturygangsta.jamboard.services.TableService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.*;
import java.util.List;

@Slf4j
@Configuration(proxyBeanMethods = false)
@Controller
@RequiredArgsConstructor
public class AdminController {
    private final TableService tableService;

    @GetMapping("/admin")
    public String responseView(Model model) {
        List<String> table = tableService.getTableSimpleNames();
        model.addAttribute("data", table);
        return "home";
    }

    @GetMapping("/admin/tables/{tableName}")
    public String responseTableListView(@PathVariable String tableName, Model model) throws SQLException {
        Table table = tableService.getTableData(tableName);
        model.addAttribute("data", table);
        return "table";
    }
}
