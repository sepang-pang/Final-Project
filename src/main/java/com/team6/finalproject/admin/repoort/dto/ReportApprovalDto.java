package com.team6.finalproject.admin.repoort.dto;

import com.team6.finalproject.club.enums.ApprovalStateEnum;
import lombok.Getter;

@Getter
public class ReportApprovalDto {
    private Long reportId;
    private ApprovalStateEnum approvalState;
}
