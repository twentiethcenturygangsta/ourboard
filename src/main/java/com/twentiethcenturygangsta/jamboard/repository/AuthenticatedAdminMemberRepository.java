package com.twentiethcenturygangsta.jamboard.repository;

import com.twentiethcenturygangsta.jamboard.entity.AuthenticatedAdminMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticatedAdminMemberRepository extends JpaRepository<AuthenticatedAdminMember, Long> {
}
