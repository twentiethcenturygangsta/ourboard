package com.twentiethcenturygangsta.ourboard.site;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthMember {
    private final OurBoardClient ourBoardClient;
//
//    @Bean
//    public void createAuthenticatedAdminMember() {
//        AuthenticatedAdminMember authenticatedAdminMember = AuthenticatedAdminMember.builder()
//                .memberId(ourBoardClient.getUserCredentials().getUserName())
//                .build();
//        log.info("authenticatedMember = {}", authenticatedAdminMember);
//        authenticatedAdminMemberRepository.save(authenticatedAdminMember);
//    }
}
