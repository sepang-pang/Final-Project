package com.team6.finalproject.post.postLike.entity;

import com.team6.finalproject.common.entity.Timestamped;
import com.team6.finalproject.post.entity.Post;
import com.team6.finalproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "like_post",
        uniqueConstraints = @UniqueConstraint(
                name = "DuplicatedLike",
                columnNames = {
                        "user_id", "post_id", "is_deleted"})

)
public class PostLike extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likePostId;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void dislike() {
        this.isDeleted = true;
    }

}
