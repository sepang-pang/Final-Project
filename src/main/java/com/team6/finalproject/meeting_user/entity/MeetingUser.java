package com.team6.finalproject.meeting_user.entity;

import com.team6.finalproject.meeting.entity.Meeting;
import com.team6.finalproject.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "meeting_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"meeting_id", "user_id"})})
public class MeetingUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Boolean isDeleted;

    public MeetingUser(Meeting meeting, User user) {
        this.meeting = meeting;
        this.user = user;
        this.isDeleted = false;
    }

    public void IsDeleted() {
        this.isDeleted = true;
    }

    public void updateMeeting(Meeting meeting) {
        this.meeting = meeting;
    }
}
