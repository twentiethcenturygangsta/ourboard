package com.twentiethcenturygangsta.ourboard.entity;

import com.twentiethcenturygangsta.ourboard.trace.JamBoardEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JamBoardEntity(group="AUTHENTICATION AND AUTHORIZATION", description = "Admin 계정")
public class AuthenticatedAdminMember {

    @Id
    @GeneratedValue
    @Column(name = "authenticated_admin_member_id")
    private Long id;

    private String memberId;
    private Boolean hasCreateAuthority;
    private Boolean hasReadAuthority;

    @Builder
    public AuthenticatedAdminMember(String memberId) {
        this.memberId = memberId;
        this.hasCreateAuthority = true;
        this.hasReadAuthority = true;
    }
}
