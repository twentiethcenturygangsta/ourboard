package com.twentiethcenturygangsta.ourboard.entity;

import com.twentiethcenturygangsta.ourboard.auth.Role;
import com.twentiethcenturygangsta.ourboard.trace.OurBoardEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@OurBoardEntity(group="AUTHENTICATION AND AUTHORIZATION", description = "OUR BOARD MEMBER ACCOUNT")
public class OurBoardMember {

    @Id
    @GeneratedValue
    @Column(name = "our_board_member_id")
    private Long id;

    private String memberId;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean hasCreateAuthority;
    private Boolean hasReadAuthority;
    private Boolean hasUpdateAuthority;
    private Boolean hasDeleteAuthority;

    @Builder
    public OurBoardMember(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
        this.role = Role.SUPER_USER;
        this.hasCreateAuthority = true;
        this.hasReadAuthority = true;
        this.hasUpdateAuthority = true;
        this.hasDeleteAuthority = true;
    }
}
