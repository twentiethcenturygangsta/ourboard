package com.twentiethcenturygangsta.jamboard.site;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthMember {
    private final JamBoardClient jamBoardClient;
//
//    @Bean
//    public void createAuthenticatedAdminMember() {
//        AuthenticatedAdminMember authenticatedAdminMember = AuthenticatedAdminMember.builder()
//                .memberId(jamBoardClient.getUserCredentials().getUserName())
//                .build();
//        log.info("authenticatedMember = {}", authenticatedAdminMember);
//        authenticatedAdminMemberRepository.save(authenticatedAdminMember);
//    }
}
