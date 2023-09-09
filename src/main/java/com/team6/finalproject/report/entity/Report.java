package com.team6.finalproject.report.entity;

import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.enums.ApprovalStateEnum;
import com.team6.finalproject.report.enums.ReportTypeEnum;
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

    @Column(name = "report_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ReportTypeEnum reportTypeEnum;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "report_user_id")
    private User reportUser;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "target_user_id")
    private User targetUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club targetClub;

    public void updateApprovalState(ApprovalStateEnum approvalState) {
        this.approvalStateEnum = approvalState;
    }
}
