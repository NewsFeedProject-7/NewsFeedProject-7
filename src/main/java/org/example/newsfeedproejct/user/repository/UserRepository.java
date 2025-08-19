package org.example.newsfeedproejct.user.repository;

import org.example.newsfeedproejct.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<User> findByIdAndDeletedAtIsNull(Long id);

    default User findByIdOrElseThrow(Long id) {

        return findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new RuntimeException("존재하지 않거나 회원탈퇴한 유저입니다."));
    }

}
