package com.github.twentiethcenturygangsta.ourboard.services;

import com.github.twentiethcenturygangsta.ourboard.entity.OurBoardMember;
import com.github.twentiethcenturygangsta.ourboard.repository.OurBoardMemberRepository;
import com.github.twentiethcenturygangsta.ourboard.site.OurBoardClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Configuration
public class OurBoardAuthService {
    private final OurBoardMemberRepository ourBoardMemberRepository;
    private final OurBoardClient ourBoardClient;

    @Bean
    public void createOurBoardAdminMember() {
        if (!ourBoardMemberRepository.existsOurBoardMemberByMemberIdAndPassword(
                ourBoardClient.getUserCredentials().getMemberId(),
                ourBoardClient.getUserCredentials().getPassword()
        )) {
            OurBoardMember ourBoardMember = OurBoardMember.builder()
                    .memberId(ourBoardClient.getUserCredentials().getMemberId())
                    .password(ourBoardClient.getUserCredentials().getPassword())
                    .build();
            ourBoardMemberRepository.save(ourBoardMember);
        }

    }
}
