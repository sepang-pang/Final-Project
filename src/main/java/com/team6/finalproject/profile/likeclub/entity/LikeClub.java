package com.team6.finalproject.profile.likeclub.entity;

import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.common.entity.Timestamped;
import com.team6.finalproject.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeClub extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @Builder
    public LikeClub(Profile profile, Club club) {
        this.profile = profile;
        this.club = club;
    }

    public void deleteLikeClub() {
        this.isDeleted = true;
    }

    public void restoreLikeClub() {
        this.isDeleted = false;
    }
}
