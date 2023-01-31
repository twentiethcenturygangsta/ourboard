package com.twentiethcenturygangsta.jamboard.site;

import com.twentiethcenturygangsta.jamboard.entity.AuthenticatedAdminMember;
import com.twentiethcenturygangsta.jamboard.repository.TestImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthMember {
    private final JamBoardClient jamBoardClient;
    private final TestImpl authenticatedAdminMemberRepository;

    @Bean
    public void createAuthenticatedAdminMember() {
        AuthenticatedAdminMember authenticatedAdminMember = AuthenticatedAdminMember.builder()
                .memberId(jamBoardClient.getUserCredentials().getUserName())
                .build();
        log.info("authenticatedMember = {}", authenticatedAdminMember);
        authenticatedAdminMemberRepository.save(authenticatedAdminMember);
    }
}
