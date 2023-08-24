package com.team6.finalproject.club.interest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class InterestMinor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String minorName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_major_id")
    private InterestMajor interestMajor;
}
