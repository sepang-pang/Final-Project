package com.team6.finalproject.user.entity;

import com.team6.finalproject.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "users")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;

    @Column(unique = true)
    private String phone; // 휴대폰 번호

    private boolean isDeleted;
    private boolean isBlock;
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;
    private Long oAuth_id;
    private String birth;

    @OneToOne(fetch = FetchType.LAZY)
    private Profile profile;

    public void saveProfile(Profile profile) {
        this.profile = profile;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
