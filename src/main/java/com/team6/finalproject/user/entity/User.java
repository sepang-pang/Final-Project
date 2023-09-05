package com.team6.finalproject.user.entity;

import com.team6.finalproject.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean isDeleted;
    private boolean isBlock;
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;
    private Long oAuth_id;
    private String birth;

    @OneToOne
    private Profile profile;

    public User(String userName, String password, String email, String birth, UserRoleEnum role) {
        this.username = userName;
        this.password = password;
        this.email = email;
        this.role = role;
        this.birth = birth;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
