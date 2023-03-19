package com.github.twentiethcenturygangsta.ourboard.services;

import com.github.twentiethcenturygangsta.ourboard.entity.OurBoardMember;
import com.github.twentiethcenturygangsta.ourboard.handler.exception.ExceptionCode;
import com.github.twentiethcenturygangsta.ourboard.handler.exception.OurBoardException;
import com.github.twentiethcenturygangsta.ourboard.repository.OurBoardMemberRepository;
import com.github.twentiethcenturygangsta.ourboard.config.EncryptionConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final OurBoardMemberRepository ourBoardMemberRepository;

    public OurBoardMember login(String loginId, String password) throws Exception {
        String encryptPassword = EncryptionConfig.encrypt(password);
        return ourBoardMemberRepository.findOurBoardMemberByMemberId(loginId)
                .filter(m -> m.getPassword().equals(encryptPassword))
                .orElseThrow(() -> new OurBoardException(ExceptionCode.NOT_FOUND_OUR_BOARD_MEMBER));
    }
}
