package com.team6.finalproject.post.entity;

import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.common.entity.Timestamped;
import com.team6.finalproject.post.dto.ClubPostRequestDto;
import com.team6.finalproject.post.enums.PostTypeEnum;
import com.team6.finalproject.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "posts")
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "view")
    private int view; //조회수

    @Column(name = "post_type")
    @Enumerated(value = EnumType.STRING)
    private PostTypeEnum postType;

    @Column(name = "media")
    private String media;

    @Column(name = "nickname")
    private String nickname;

    // soft-delete 상태값
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    public void update(ClubPostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }

    public void deletePost() {
        this.isDeleted = true;
    }

    public void updateMedia(String media) {
        this.media = media;

    }
}
