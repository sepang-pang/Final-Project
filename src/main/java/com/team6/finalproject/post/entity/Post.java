package com.team6.finalproject.post.entity;

import com.team6.finalproject.common.entity.Timestamped;
import com.team6.finalproject.post.dto.PostRequestDto;
import com.team6.finalproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "posts")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private int view;

    @Column
    @Enumerated(value = EnumType.STRING)
    private PostTypeEnum postType;

    // soft-delete 상태값
    @Column(nullable = false)
    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Post(PostRequestDto postRequestDto, User user) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.postType = postRequestDto.getPostType();
        this.user = user;
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.postType = postRequestDto.getPostType();
    }

    public void deletePost() {
        this.title = getTitle();
        this.content = getContent();
        this.postType = getPostType();
        this.isDeleted = true;

    }
}
