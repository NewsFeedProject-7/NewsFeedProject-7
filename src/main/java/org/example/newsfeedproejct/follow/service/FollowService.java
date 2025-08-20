package org.example.newsfeedproejct.follow.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.follow.dto.FollowResponseDto;
import org.example.newsfeedproejct.follow.entity.Follow;
import org.example.newsfeedproejct.follow.repository.FollowRepository;
import org.example.newsfeedproejct.global.exception.GlobalException;
import org.example.newsfeedproejct.global.exception.errorcode.FollowErrorCode;
import org.example.newsfeedproejct.user.entity.User;
import org.example.newsfeedproejct.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(Long followerId, Long followingId) {
        //자신을 팔로우하는경우 예외처리
        if (followerId.equals(followingId)) {
            throw new GlobalException(FollowErrorCode.CANNOT_FOLLOW_YOURSELF);
        }
        //이미 팔로우중인 경우 예외처리
        if (followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            throw new GlobalException(FollowErrorCode.ALREADY_FOLLOWING);
        }

        Follow follow = new Follow(followerId, followingId);
        //팔로우관계 저장
        followRepository.save(follow);
    }

    @Transactional
    public void unfollow(Long followerId, Long followingId) {
        //팔로우 관계가 없는경우 예외처리
        if (!followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            throw new GlobalException(FollowErrorCode.NOT_FOLLOWING_YET);
        }
        followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
    }

    @Transactional(readOnly = true)
    public List<Long> getFollowingIds(Long loginUserId) {
        // 특정 유저가 팔로우하는 모든 팔로우 관계 엔티티를 조회합니다.
        List<Follow> followingRelations = followRepository.findAllByFollowerId(loginUserId);

        // 조회된 엔티티에서 팔로잉 대상의 ID만 추출하여 리스트로 반환합니다.
        return followingRelations.stream()
                .map(Follow::getFollowingId)
                .collect(Collectors.toList());
    }
}