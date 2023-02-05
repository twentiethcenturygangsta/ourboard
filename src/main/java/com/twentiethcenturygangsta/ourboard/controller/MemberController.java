package com.twentiethcenturygangsta.ourboard.controller;

import com.twentiethcenturygangsta.ourboard.entity.OurBoardMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("ourboard/api/v1/member")
public class MemberController {

    @PostMapping("/login")
    public String getMemberLogin(@RequestBody OurBoardMember member) {

        return member.getMemberId() + " / " + member.getPassword();
    }
}
