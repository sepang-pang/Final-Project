package com.team6.finalproject.profile.profileinterest.entity;

import com.team6.finalproject.club.interest.entity.InterestMinor;
import com.team6.finalproject.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "profile_interest", uniqueConstraints = @UniqueConstraint(columnNames = {"interest_minor_id", "profile_id"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_minor_id", nullable = false)
    private InterestMinor interestMinor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Builder
    public ProfileInterest(InterestMinor interestMinor, Profile profile) {
        this.interestMinor = interestMinor;
        this.profile = profile;
    }
}
