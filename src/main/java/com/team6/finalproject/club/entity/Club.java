package com.team6.finalproject.club.entity;

import com.team6.finalproject.club.dto.ClubRequestDto;
import com.team6.finalproject.club.enums.ActivityTypeEnum;
import com.team6.finalproject.club.enums.JoinTypeEnum;
import com.team6.finalproject.club.interest.entity.InterestMinor;
import com.team6.finalproject.common.entity.Timestamped;
import com.team6.finalproject.meeting.entity.Meeting;
import com.team6.finalproject.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username; // 동호회 개설자 아이디

    @Column(name = "nink_name")
    private String nickName; // 동호회 개설자 본명 및 별명

    @Column(name = "name")
    private String name; // 동호회 이름

    @Column(name = "description")
    private String description; // 동호회 설명

    @Column(name = "max_member")
    private int maxMember; // 동호회 최대 인원 수

    @Column(name = "min_age")
    private int minAge; // 모집 연령대의 최소 값

    @Column(name = "max_age")
    private int maxAge; // 모집 연령대의 최대 값

    @Column(name = "media")
    private String media; // 동호회 사진

    @Column(name = "latitude")
    private Double latitude; // 동호회 위도

    @Column(name = "longitude")
    private Double longitude; // 동호회 경도

    @Column(name = "locate")
    private String locate; // 동호회 위치

    @Column(name = "activity_score")
    private int activityScore; // 동호회 활동 점수

    @Column(name = "is_deleted")
    private boolean isDeleted; // 삭제 상태값

    @Column(name = "is_trial_available")
    private boolean isTrialAvailable; // 소모임 가능 여부

    @Column(name = "activity_type")
    @Enumerated(value = EnumType.STRING)
    private ActivityTypeEnum activityType; // 활동 방식 (온라인, 오프라인)

    @Column(name = "join_type")
    @Enumerated(value = EnumType.STRING)
    private JoinTypeEnum joinType; // 가입 방식 (즉시 가입, 가입 승인)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_minor_id")
    private InterestMinor minor;

    @OneToMany(mappedBy ="club", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy ="club", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Meeting> meetings;

    public void deleteClub() {
        this.isDeleted = true;
    }

    public void addMeeting(Meeting meeting) {
        this.meetings.add(meeting);
    }

    public void updateActivityScore(int i) {
        this.activityScore += i;
    }

    public void updateClub(ClubRequestDto clubRequestDto, String media, InterestMinor interestMinor, ActivityTypeEnum activity, JoinTypeEnum join) {
        this.name = clubRequestDto.getName();
        this.description = clubRequestDto.getDescription();
        this.media = media;
        this.maxMember = clubRequestDto.getMaxMember();
        this.minAge = clubRequestDto.getMinAge();
        this.maxAge = clubRequestDto.getMaxAge();
        this.latitude = clubRequestDto.getLatitude();
        this.longitude = clubRequestDto.getLongitude();
        this.locate = clubRequestDto.getLocate();
        this.isTrialAvailable = clubRequestDto.isTrialAvailable();
        this.activityType = activity;
        this.joinType = join;
        this.minor = interestMinor;
    }
}
