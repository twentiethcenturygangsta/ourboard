package com.twentiethcenturygangsta.ourboard.controller;

import com.twentiethcenturygangsta.ourboard.entity.OurBoardMember;
import com.twentiethcenturygangsta.ourboard.form.LoginForm;
import com.twentiethcenturygangsta.ourboard.handler.ResponseHandler;
import com.twentiethcenturygangsta.ourboard.manager.session.SessionConst;
import com.twentiethcenturygangsta.ourboard.services.LoginService;
import com.twentiethcenturygangsta.ourboard.services.TableService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;


@Slf4j
@Controller
@RequestMapping("/our-board/api")
@RequiredArgsConstructor
public class AdminAPIController {

    private final LoginService loginService;
    private final TableService tableService;

    @PostMapping("/login")
    public ResponseEntity<Object> loginAPI(@RequestBody LoginForm loginForm, HttpServletRequest request) throws Exception {

        OurBoardMember ourBoardMember = loginService.login(loginForm.getMemberId(), loginForm.getPassword());
        log.info("ourBoardMember = {}", ourBoardMember);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, ourBoardMember);
        return ResponseHandler.generateResponse(HttpStatus.OK, "Successful login");
    }

    @PostMapping("/{tableName}")
    public ResponseEntity<Object> createInstanceAPI(@PathVariable("tableName") String tableName,
                                                    @RequestBody HashMap<String, Object> requestBody) throws Exception {
        log.info("requestBody = {}", requestBody);
        log.info("tableName = {}", tableName);
        tableService.createObject(requestBody, tableName);
        return ResponseHandler.generateResponse(HttpStatus.OK, "Successful create");
    }
}
