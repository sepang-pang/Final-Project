package com.team6.finalproject.meeting.entity;


import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.enums.ActivityTypeEnum;
import com.team6.finalproject.common.entity.Timestamped;
import com.team6.finalproject.meeting.dto.MeetingPlaceRequestDto;
import com.team6.finalproject.meeting.dto.MeetingRequestDto;
import com.team6.finalproject.meeting.dto.MeetingScheduleRequestDto;
import com.team6.finalproject.meeting_user.entity.MeetingUser;
import com.team6.finalproject.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    private String name;
    @Column
    private String description;
    @Column
    private String media;
    @Column
    private int maxMember;
    @Column
    private ActivityTypeEnum ACTIVITY_TYPE;
    @Column
    private LocalDateTime date;
    @Column
    private String place;
    @Column
    private Boolean isCompleted;
    @Column
    private Boolean isDeleted;
    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;
    // 모임 작성자
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    // 모임 참여자
    @OneToMany(mappedBy ="meeting", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<MeetingUser> meetingUsers;

    public void update(MeetingRequestDto meetingRequestDto, String media) {
        this.name = meetingRequestDto.getName();
        this.description = meetingRequestDto.getDescription();
        this.media = media;
        this.maxMember = meetingRequestDto.getMaxMember();
        this.ACTIVITY_TYPE = meetingRequestDto.getACTIVITY_TYPE();
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

    public void addMetingUser(MeetingUser meetingUser) {
        this.meetingUsers.add(meetingUser);
    }

    public void completed() {
        this.isCompleted = true;
    }

    public void updateImage(String media) {
        this.media = media;
    }
}
