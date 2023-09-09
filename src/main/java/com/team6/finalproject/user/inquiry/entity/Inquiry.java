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
    private String answer;

    @Column
    private String media;

    @Column
    private boolean isDeleted;

    @Column
    @Enumerated(value = EnumType.STRING)
    private InquiryTypeEnum inquiryType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Inquiry(InquiryTypeEnum inquiryType, String description, String media, User user) {
        this.inquiryType = inquiryType;
        this.description = description;
        this.media = media;
        this.user = user;
    }

    public void update(String media, InquiryTypeEnum inquiryType, String description) {
        this.media = media;
        this.inquiryType = inquiryType;
        this.description = description;
    }

    public void saveAnswer(String answer) {
        this.answer = answer;
    }

    public void deleteInquiry() {
        this.isDeleted = true;
    }
}
