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
    private String title;

    @Column
    private String description;

    @Column
    private String answer;

    @Column
    private String media;

    @Column(nullable = false)
    private boolean isDeleted;

    @Column
    @Enumerated(value = EnumType.STRING)
    private InquiryTypeEnum inquiryType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Inquiry(String title, String description, String media, InquiryTypeEnum inquiryType, User user) {
        this.title = title;
        this.description = description;
        this.media = media;
        this.inquiryType = inquiryType;
        this.user = user;
    }

    public void update(String title, String description, String media, InquiryTypeEnum inquiryType) {
        this.title = title;
        this.description = description;
        this.media = media;
        this.inquiryType = inquiryType;
    }

    public void saveAnswer(String answer) {
        this.answer = answer;
    }

    public void deleteInquiry() {
        this.isDeleted = true;
    }
}
