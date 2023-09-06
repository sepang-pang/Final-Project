package com.team6.finalproject.club.report.entity;

import com.team6.finalproject.club.enums.ApprovalStateEnum;
import com.team6.finalproject.common.entity.Timestamped;
import com.team6.finalproject.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "approval_state", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ApprovalStateEnum approvalStateEnum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Column(name = "apply_user_id", nullable = false)
    private User applyUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Column(name = "target_user_id", nullable = false)
    private User targetUser;
}
