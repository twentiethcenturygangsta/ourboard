package com.twentiethcenturygangsta.ourboard.services;

import com.twentiethcenturygangsta.ourboard.entity.OurBoardMember;
import com.twentiethcenturygangsta.ourboard.repository.OurBoardMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final OurBoardMemberRepository ourBoardMemberRepository;

    public boolean isOurBoardMember(OurBoardMember member) {
        log.info("OurBoardMember = {}", member);
        return member != null;
    }

    public OurBoardMember login(String loginId, String password) {
        log.info("Login = {}, {}", loginId, password);
        return ourBoardMemberRepository.findOurBoardMemberByMemberId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
