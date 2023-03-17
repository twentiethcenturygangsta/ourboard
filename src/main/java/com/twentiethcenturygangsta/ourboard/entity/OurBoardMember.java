package com.twentiethcenturygangsta.ourboard.entity;

import com.twentiethcenturygangsta.ourboard.annoatation.OurBoardColumn;
import com.twentiethcenturygangsta.ourboard.annoatation.OurBoardEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@OurBoardEntity(group = GroupType.Constants.AUTHENTICATION_AND_AUTHORIZATION, description = "Admin 계정")
public class OurBoardMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "our_board_member_id")
    @OurBoardColumn(description = "AUTO_INCREMENT (PRIMARY KEY)")
    private Long id;

    @OurBoardColumn(description = "LOGIN ID")
    private String memberId;
    @OurBoardColumn(description = "LOGIN PASSWORD")
    private String password;
    @OurBoardColumn(description = "CREATE PERMISSION")
    private Boolean hasCreateAuthority;
    @OurBoardColumn(description = "READ PERMISSION")
    private Boolean hasReadAuthority;
    @OurBoardColumn(description = "UPDATE PERMISSION")
    private Boolean hasUpdateAuthority;
    @OurBoardColumn(description = "DELETE PERMISSION")
    private Boolean hasDeleteAuthority;

    @Builder
    public OurBoardMember(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
        this.hasCreateAuthority = true;
        this.hasReadAuthority = true;
        this.hasUpdateAuthority = true;
        this.hasDeleteAuthority = true;
    }
}
