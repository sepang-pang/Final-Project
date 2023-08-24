package com.team6.finalproject.profile.entity;

import com.team6.finalproject.common.entity.Timestamped;
import com.team6.finalproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "profile")
public class Profile extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nickname;

    @Column
    private String introduction;

    @Column
    private String profileImage;

    @Column
    private String locate;

    @Column(nullable = false)
    private Long userScore;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Profile(String nickname, String introduction, User user) {
        this.nickname = nickname;
        this.introduction = introduction;
        this.user = user;
        this.userScore = 0L;
    }

    public void update(String nickname, String introduction) {
        this.nickname = nickname;
        this.introduction = introduction;
    }

    public void updateImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
