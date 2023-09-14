package com.team6.finalproject.comment.entity;

import com.team6.finalproject.comment.dto.CommentRequestDto;
import com.team6.finalproject.comment.like.entity.CommentLike;
import com.team6.finalproject.common.entity.Timestamped;
import com.team6.finalproject.meeting.entity.Meeting;
import com.team6.finalproject.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(name = "content")
    private String content;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "nickname")
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @OneToMany(mappedBy = "comment")
    private List<CommentLike> commentLikes = new ArrayList<>();

    public Comment(CommentRequestDto commentRequestDto, User user, Meeting meeting) {
        this.content = commentRequestDto.getContent();
        this.user = user;
        this.meeting = meeting;
        this.nickname = user.getProfile().getNickname();
        meeting.getMeetingComments().add(this);
    }


    public void deleteComment() {
        this.isDeleted = true;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }

}
