package com.twentiethcenturygangsta.ourboard.services;

import com.twentiethcenturygangsta.ourboard.config.EncryptionConfig;
import com.twentiethcenturygangsta.ourboard.entity.OurBoardMember;
import com.twentiethcenturygangsta.ourboard.handler.exception.ExceptionCode;
import com.twentiethcenturygangsta.ourboard.handler.exception.OurBoardException;
import com.twentiethcenturygangsta.ourboard.repository.OurBoardMemberRepository;
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
