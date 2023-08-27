package com.team6.finalproject.club.member.entity;

import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.enums.ClubRoleEnum;
import com.team6.finalproject.common.entity.Timestamped;
import com.team6.finalproject.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member",
        uniqueConstraints = @UniqueConstraint(
                name = "DuplicateClubMember",
                columnNames = {
                        "user_id", "club_id"})
)
public class Member extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "club_role")
    @Enumerated(value = EnumType.STRING)
    private ClubRoleEnum clubRoleEnum; // 멤버 권한

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

}
