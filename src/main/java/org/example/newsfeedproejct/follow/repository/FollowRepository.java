package org.example.newsfeedproejct.follow.repository;

import org.example.newsfeedproejct.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryCustom {
    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    List<Follow> findAllByFollowerId(Long userId);

    void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);

    List<Follow> findAllByFollowingId(Long loginUserId);
}
