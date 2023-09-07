package com.team6.finalproject.club.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClubRequestDto {
    private String name; // 동호회 이름
    private Long minorId; // 동호회 소분류
    private String description; // 동호회 설명
    private boolean trialAvailable; // 모임 시범운영 여부
    private boolean isOnline; // 활동 방식 (온라인/오프라인)
    private boolean openJoinType; // 가입방식 (자유/승인)
    private int maxMember; // 동호회 최대 인원 수
    private int minAge; // 모집 연령대의 최소 값
    private int maxAge; // 모집 연령대의 최대 값
    private Double latitude; // 동호회 위도
    private Double longitude; // 동호회 경도
    private String locate; // 동호회 위치
}
