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
@Table(name = "like_club")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeClub extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}
