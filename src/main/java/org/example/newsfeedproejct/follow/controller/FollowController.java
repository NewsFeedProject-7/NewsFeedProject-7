package org.example.newsfeedproejct.follow.controller;

import lombok.RequiredArgsConstructor;
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

    @PostMapping("/users/{userId}/following")
    @ResponseStatus(HttpStatus.CREATED)
    public void followUser(@PathVariable Long userId, @SessionAttribute(Const.LOGIN_USER) Long loginUserId) {
        followService.follow(loginUserId, userId);
    }

    @DeleteMapping("/users/{userId}/following")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unfollowUser(@PathVariable Long userId, @SessionAttribute(Const.LOGIN_USER) Long loginUserId) {
        followService.unfollow(loginUserId, userId);
    }

    //팔로잉 목록 조회 API
    @GetMapping("/users/{userId}/following")
    @ResponseStatus(HttpStatus.OK)
    public List<FollowResponseDto> getFollowingList(@PathVariable Long userId) {
        //userId가 팔로우하는 목록 가져옴
        List<Long> followingIds = followService.getFollowingIds(userId);
        //가져온 ID목록 사용해서 User정보조회 및 DTO변환
        List<User> followingUsers = userRepository.findAllById(followingIds);
        return followingUsers.stream()
                .map(FollowResponseDto::from)
                .collect(Collectors.toList());
    }


}
