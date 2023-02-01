package com.twentiethcenturygangsta.ourboard.controller;

import com.twentiethcenturygangsta.ourboard.dto.Table;
import com.twentiethcenturygangsta.ourboard.dto.TablesInfo;
import com.twentiethcenturygangsta.ourboard.services.TableService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Configuration(proxyBeanMethods = false)
@Controller
@RequiredArgsConstructor
public class AdminController {
    private final TableService tableService;

    /**
     * TODO
     * Need to get userName from userCredentials
     *
     */
    @GetMapping("/admin")
    public String responseView(Model model) {
        HashMap<String, ArrayList<TablesInfo>> table = tableService.getTableSimpleNames();
        model.addAttribute("userName", "JUNHYEOK");
        model.addAttribute("data", table);
        return "main";
    }

    @GetMapping("/admin/{groupName}")
    public String responseGroupView(@PathVariable("groupName") String groupName,
                               Model model) {
        HashMap<String, ArrayList<TablesInfo>> table = tableService.getTableSimpleNames();
        model.addAttribute("userName", "JUNHYEOK");
        model.addAttribute("groupName", groupName);
        model.addAttribute("data", table);
        return "main";
    }

    @GetMapping("/admin/{groupName}/{tableName}")
    public String responseTableListView(@PathVariable("groupName") String groupName,
                                        @PathVariable("tableName") String tableName,
                                        Model model) throws SQLException {
        Table table = tableService.getTableData(tableName);
        model.addAttribute("groupName", groupName);
        model.addAttribute("tableName", tableName);
        model.addAttribute("data", table);
        return "table";
    }

    @GetMapping("/admin/login")
    public String responseLoginView() {
        return "login";
    }
}
