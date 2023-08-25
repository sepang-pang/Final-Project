package com.team6.finalproject.club.interest.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
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
