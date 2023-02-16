package com.twentiethcenturygangsta.ourboard.repository;

import com.twentiethcenturygangsta.ourboard.entity.OurBoardMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OurBoardMemberRepository extends JpaRepository<OurBoardMember, Long> {

    Optional<OurBoardMember> findOurBoardMemberByMemberId(String memberId);
    boolean existsOurBoardMemberByMemberIdAndPassword(String memberId, String password);
}
