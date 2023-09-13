package com.team6.finalproject.meeting.entity;


import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.enums.ActivityTypeEnum;
import com.team6.finalproject.comment.entity.Comment;
import com.team6.finalproject.common.entity.Timestamped;
import com.team6.finalproject.meeting.dto.MeetingPlaceRequestDto;
import com.team6.finalproject.meeting.dto.MeetingRequestDto;
import com.team6.finalproject.meeting.dto.MeetingScheduleRequestDto;
import com.team6.finalproject.meeting_user.entity.MeetingUser;
import com.team6.finalproject.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String media;

    @Column
    private int maxMember;

    @Column
    private LocalDateTime date;

    @Column
    private String place;

    @Column
    private Boolean isCompleted;

    @Column
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    // 모임 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 모임 참석 유저
    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeetingUser> meetingUsers = new ArrayList<>();

    // 모임 댓글
    @OneToMany(mappedBy = "meeting", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comment> meetingComments = new ArrayList<>();

    public Meeting(MeetingRequestDto meetingRequestDto, Club club, User user) {
        this.title = meetingRequestDto.getTitle();
        this.description = meetingRequestDto.getDescription();
        this.maxMember = meetingRequestDto.getMaxMember();
        this.date = meetingRequestDto.getDate();
        this.place = meetingRequestDto.getPlace();
        this.isCompleted = false;
        this.isDeleted = false;
        this.club = club;
        this.user = user;
    }

    public void update(MeetingRequestDto meetingRequestDto, String media) {
        this.title = meetingRequestDto.getTitle();
        this.description = meetingRequestDto.getDescription();
        this.media = media;
        this.maxMember = meetingRequestDto.getMaxMember();
        this.date = meetingRequestDto.getDate();
        this.place = meetingRequestDto.getPlace();
    }

    public void updateSchedule(MeetingScheduleRequestDto meetingScheduleRequestDto) {
        this.date = meetingScheduleRequestDto.getSchedule();
    }

    public void updatePlace(MeetingPlaceRequestDto meetingplaceRequestDto) {
        this.place = meetingplaceRequestDto.getPlace();
    }

    public void deleted() {
        this.isDeleted = true;
    }

    public void completed() {
        this.isCompleted = true;
    }

    public void updateImage(String media) {
        this.media = media;
    }

    public void addMeetingUser(MeetingUser meetingUser) {
        this.meetingUsers.add(meetingUser);
        meetingUser.updateMeeting(this);  // 양방향 연관 관계 설정
    }

}
