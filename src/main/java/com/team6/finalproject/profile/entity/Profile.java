package com.team6.finalproject.profile.entity;

import com.team6.finalproject.common.entity.Timestamped;
import com.team6.finalproject.profile.profileinterest.entity.ProfileInterest;
import com.team6.finalproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToMany(mappedBy = "profile", orphanRemoval = true)
    private List<ProfileInterest> profileInterests = new ArrayList<>();

    @Builder
    public Profile(String nickname, String introduction, String locate, User user) {
        this.nickname = nickname;
        this.introduction = introduction;
        this.locate = locate;
        this.user = user;
        this.userScore = 0L;
    }

    public void update(String nickname, String introduction, String locate) {
        this.nickname = nickname;
        this.introduction = introduction;
        this.locate = locate;
    }

    public void updateImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
