package com.twentiethcenturygangsta.ourboard.controller;

import com.twentiethcenturygangsta.ourboard.dto.Table;
import com.twentiethcenturygangsta.ourboard.dto.TablesInfo;
import com.twentiethcenturygangsta.ourboard.entity.OurBoardMember;
import com.twentiethcenturygangsta.ourboard.form.LoginForm;
import com.twentiethcenturygangsta.ourboard.manager.session.SessionConst;
import com.twentiethcenturygangsta.ourboard.services.LoginService;
import com.twentiethcenturygangsta.ourboard.services.TableService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Configuration(proxyBeanMethods = false)
@Controller
@RequestMapping("/our-board")
@RequiredArgsConstructor
public class AdminController {
    private final TableService tableService;
    private final LoginService loginService;

    /**
     * TODO
     * Need to get userName from userCredentials
     *
     */
    @GetMapping("/admin")
    public String responseView(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) OurBoardMember loginMember,
            Model model) {
        if (!loginService.isOurBoardMember(loginMember)) {
            return "login";
        }
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
    public String responseLoginView(@ModelAttribute LoginForm loginForm) {
        return "login";
    }

    @PostMapping("/api/login")
    public String loginAPI(@ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult, HttpServletRequest request) {
        log.info("bindingResult = {}", bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }
        OurBoardMember ourBoardMember = loginService.login(form.getMemberId(), form.getPassword());
        log.info("ourBoardMember = {}", ourBoardMember);
        if (ourBoardMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, ourBoardMember);
        return "redirect:/our-board/admin";
    }

    @GetMapping("/admin/{groupName}/{tableName}/add")
    public String responseInstanceCreateView(@PathVariable("groupName") String groupName,
                                             @PathVariable("tableName") String tableName,
                                             Model model) throws SQLException {
        Table table = tableService.getTableData(tableName);
        model.addAttribute("groupName", groupName);
        model.addAttribute("tableName", tableName);
        model.addAttribute("data", table);
        return "createView";
    }
}
