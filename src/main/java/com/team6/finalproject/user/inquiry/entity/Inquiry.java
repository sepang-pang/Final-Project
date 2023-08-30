package com.team6.finalproject.user.inquiry.entity;

import com.team6.finalproject.common.entity.Timestamped;
import com.team6.finalproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "inquiry")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @Column
    @Enumerated(value = EnumType.STRING)
    private InquiryTypeEnum inquiryType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Inquiry(InquiryTypeEnum inquiryType, String description, User user) {
        this.inquiryType = inquiryType;
        this.description = description;
        this.user = user;
    }

    public void update(InquiryTypeEnum inquiryType, String description) {
        this.inquiryType = inquiryType;
        this.description = description;
    }
}
