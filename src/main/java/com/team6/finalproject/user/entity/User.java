package com.team6.finalproject.user.entity;

import com.team6.finalproject.profile.entity.Profile;
import com.team6.finalproject.user.dto.SignupRequestDto;
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

    @Column(nullable = false, unique = true)
    private String phone; // 휴대폰 번호

    private boolean isDeleted;
    private boolean isBlock;
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;
    private Long oAuth_id;
    private String birth;

    @OneToOne
    private Profile profile;

    public User(String userName, String password, String phoneNumber, String email, String birth, UserRoleEnum role) {
        this.username = userName;
        this.password = password;
        this.phone = phoneNumber;
        this.email = email;
        this.role = role;
        this.birth = birth;
    }
}
