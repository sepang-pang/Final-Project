package com.team6.finalproject.post.entity;

import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.common.entity.Timestamped;
import com.team6.finalproject.post.dto.PostRequestDto;
import com.team6.finalproject.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "posts")
@Entity
@Getter
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

    // soft-delete 상태값
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;



    public Post(PostRequestDto postRequestDto, User user, Club club, String media) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.postType = postRequestDto.getPostType();
        this.user = user;
        this.club = club;
        this.media = media;
    }


    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.postType = postRequestDto.getPostType();
    }

    public void deletePost() {
        this.isDeleted = true;
    }

    public void updateMedia(String media) {
        this.media = media;

    }
}
