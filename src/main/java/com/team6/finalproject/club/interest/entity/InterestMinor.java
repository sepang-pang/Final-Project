package com.team6.finalproject.club.interest.entity;

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
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"minor_name", "interest_major_id"})
)
public class InterestMinor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "minor_name")
    private String minorName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_major_id")
    private InterestMajor interestMajor;
}
