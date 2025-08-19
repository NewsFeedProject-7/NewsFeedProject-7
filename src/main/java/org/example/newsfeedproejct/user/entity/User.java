package org.example.newsfeedproejct.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeedproejct.global.config.PasswordEncoder;
import org.example.newsfeedproejct.global.entity.SoftDeletableEntity;
import org.example.newsfeedproejct.global.exception.GlobalException;
import org.example.newsfeedproejct.global.exception.errorcode.UserErrorCode;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String email;

    private String password;

    public User(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public void verifyPasswordOrThrow(PasswordEncoder encoder, String rawPassword) {

        if (false == encoder.matches(rawPassword, this.password)) {
            throw new GlobalException(UserErrorCode.INVALID_PASSWORD);
        }
    }
}
