package com.twentiethcenturygangsta.ourboard.services;

import com.twentiethcenturygangsta.ourboard.entity.OurBoardMember;
import com.twentiethcenturygangsta.ourboard.repository.OurBoardMemberRepository;
import com.twentiethcenturygangsta.ourboard.site.OurBoardClient;
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
        OurBoardMember ourBoardMember = OurBoardMember.builder()
                .memberId(ourBoardClient.getUserCredentials().getUserName())
                .password(ourBoardClient.getUserCredentials().getPassword())
                .build();
        ourBoardMemberRepository.save(ourBoardMember);
    }
}
