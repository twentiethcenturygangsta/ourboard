package com.twentiethcenturygangsta.ourboard.controller;

import com.twentiethcenturygangsta.ourboard.dto.FieldInfo;
import com.twentiethcenturygangsta.ourboard.dto.TablesInfo;
import com.twentiethcenturygangsta.ourboard.entity.OurBoardMember;
import com.twentiethcenturygangsta.ourboard.form.LoginForm;
import com.twentiethcenturygangsta.ourboard.manager.session.SessionConst;
import com.twentiethcenturygangsta.ourboard.services.TableService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.*;


@Slf4j
@Configuration(proxyBeanMethods = false)
@Controller
@RequestMapping("/our-board/admin")
@RequiredArgsConstructor
public class AdminViewController {
    private final TableService tableService;

    /**
     * TODO
     *
     *
     */
    @GetMapping
    public String responseView(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) OurBoardMember loginMember,
            Model model) throws SQLException {

        HashMap<String, ArrayList<TablesInfo>> table = tableService.getTableSimpleNames();
        model.addAttribute("userName", loginMember.getMemberId());
        model.addAttribute("data", table);
        return "main";
    }

    @GetMapping("/{groupName}")
    public String responseGroupView(@PathVariable("groupName") String groupName, Model model) throws SQLException {
        HashMap<String, ArrayList<TablesInfo>> table = tableService.getTableSimpleNames();
        model.addAttribute("groupName", groupName);
        model.addAttribute("data", table);
        return "main";
    }

    @GetMapping("/{groupName}/{tableName}/search")
    public String responseTableListView(@PathVariable("groupName") String groupName,
                                        @PathVariable("tableName") String tableName,
                                        String keyword,
                                        String type,
                                        @PageableDefault Pageable pageable,
                                        Model model) throws SQLException {
        String result = null;

        if(groupName.equals(tableName)) {
            result = "popUpTable";
            Object data = tableService.getObjects(tableName);
            model.addAttribute("data", data);
        } else {
            result = "table";
            Page<?> data = tableService.searchObjects(keyword, type, tableName, pageable);
            model.addAttribute("data", data);
        }
        LinkedHashMap<String, FieldInfo> fields = tableService.getFields(tableName);
        model.addAttribute("groupName", groupName);
        model.addAttribute("tableName", tableName);
        model.addAttribute("fields", fields);

        if ("ALL".equals(keyword) && "ALL".equals(type)) {
            model.addAttribute("keyword", "ALL");
            model.addAttribute("type", "ALL");
        } else {
            model.addAttribute("keyword", keyword);
            model.addAttribute("type", type);
        }

        return result;
    }

    @GetMapping("/login")
    public String responseLoginView(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login";
    }

    @GetMapping("/{groupName}/{tableName}/{event}")
    public String responseInstanceEventView(@PathVariable("groupName") String groupName,
                                             @PathVariable("tableName") String tableName,
                                             @PathVariable("event") String event,
                                             Model model) throws SQLException {
        LinkedHashMap<String, FieldInfo> fields = tableService.getFields(tableName);

        if(!"add".equals(event)) {
            Long id = Long.valueOf(event);
            Optional<?> data = tableService.getObject(tableName, id);
            model.addAttribute("data", data);
        }

        model.addAttribute("groupName", groupName);
        model.addAttribute("tableName", tableName);
        model.addAttribute("event", event);
        model.addAttribute("fields", fields);

        return "eventView";
    }
}
