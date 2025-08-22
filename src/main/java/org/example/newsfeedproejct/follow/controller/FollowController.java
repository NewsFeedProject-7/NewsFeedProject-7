package org.example.newsfeedproejct.follow.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.follow.dto.FollowFriendAnniversaryDto;
import org.example.newsfeedproejct.follow.dto.FollowListDto;
import org.example.newsfeedproejct.follow.dto.FollowResponseDto;
import org.example.newsfeedproejct.follow.service.FollowService;
import org.example.newsfeedproejct.global.consts.Const;
import org.example.newsfeedproejct.user.entity.User;
import org.example.newsfeedproejct.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final UserRepository userRepository;

    @PostMapping("/following/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void followUser(@PathVariable Long userId, @SessionAttribute(Const.LOGIN_USER) Long loginUserId) {
        followService.follow(loginUserId, userId);
    }

    @DeleteMapping("/following/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unfollowUser(@PathVariable Long userId, @SessionAttribute(Const.LOGIN_USER) Long loginUserId) {
        followService.unfollow(loginUserId, userId);
    }

    //팔로잉, 팔로워 목록 조회 API
    @GetMapping("/following")
    @ResponseStatus(HttpStatus.OK)
    public FollowListDto getFollowingList(@SessionAttribute(Const.LOGIN_USER) Long loginUserId) {
        // 내가 팔로잉하는 목록
        List<Long> followingIds = followService.getFollowingIds(loginUserId);
        List<User> followingUsers = userRepository.findAllById(followingIds);
        List<FollowResponseDto.Response> followees = followingUsers.stream()
                .map(FollowResponseDto.Response::from)
                .collect(Collectors.toList());

        // 나를 팔로우하는 목록
        List<Long> followerIds = followService.getFollowerIds(loginUserId);
        List<User> followerUsers = userRepository.findAllById(followerIds);
        List<FollowResponseDto.Response> followers = followerUsers.stream()
                .map(FollowResponseDto.Response::from)
                .collect(Collectors.toList());

        // 묶어서 반환
        return FollowListDto.builder()
                .followingId(followees)
                .followerId(followers)
                .build();
    }

    /* 친구 기념일 API
     1. 팔로잉과 팔로워가 둘 다 되어있을 때 친구라고 표현한다.
     2. 더 나중에 follow 된 createAt 기준으로 기념일을 계산한다.
     3. 현재 알림 Push 기능을 설정하지 못하기 때문에 클라이언트가 조회하는 API로 대체한다.
     4. 기념일인 날짜일 때만 값을 보여준다. 기념일인 친구가 없을 땐 [] 반환
     5. 기념일은 1년, 2년 ... 매 1년마다 반복된다.
    */
    @GetMapping("/friends/anniversaries")
    @ResponseStatus(HttpStatus.OK)
    public List<FollowFriendAnniversaryDto.Response> getTodayFriendAnniversaries(
            @SessionAttribute(Const.LOGIN_USER) Long loginUserId) {
        return followService.getTodayFriendAnniversaries(loginUserId);
    }
}
