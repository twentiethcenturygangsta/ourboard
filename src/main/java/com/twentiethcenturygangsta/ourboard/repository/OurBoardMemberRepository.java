package com.twentiethcenturygangsta.ourboard.repository;

import com.twentiethcenturygangsta.ourboard.entity.OurBoardMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OurBoardMemberRepository extends JpaRepository<OurBoardMember, Long> {
}
