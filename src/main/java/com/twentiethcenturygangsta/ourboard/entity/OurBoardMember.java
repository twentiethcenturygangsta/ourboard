package com.twentiethcenturygangsta.ourboard.entity;

import com.twentiethcenturygangsta.ourboard.annoatation.OurBoardColumn;
import com.twentiethcenturygangsta.ourboard.annoatation.OurBoardEntity;
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
@OurBoardEntity(group="AUTHENTICATION AND AUTHORIZATION", description = "Admin 계정")
public class OurBoardMember {

    @Id
    @GeneratedValue
    @Column(name = "our_board_member_id")
    @OurBoardColumn(fieldName = "OUR_BOARD_MEMBER_ID", description = "AUTO_INCREMENT")
    private Long id;

    @OurBoardColumn(fieldName = "MEMBER_ID", description = "LOGIN ID")
    private String memberId;
    private String password;
    @OurBoardColumn(fieldName = "HAS_CREATE_AUTHORITY", description = "CREATE PERMISSION")
    private Boolean hasCreateAuthority;
    @OurBoardColumn(fieldName = "HAS_READ_AUTHORITY", description = "READ PERMISSION")
    private Boolean hasReadAuthority;
    @OurBoardColumn(fieldName = "HAS_UPDATE_AUTHORITY", description = "UPDATE PERMISSION")
    private Boolean hasUpdateAuthority;
    @OurBoardColumn(fieldName = "HAS_DELETE_AUTHORITY", description = "DELETE PERMISSION")
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
