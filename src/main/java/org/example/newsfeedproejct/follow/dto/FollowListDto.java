package org.example.newsfeedproejct.follow.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FollowListDto {
    private final List<FollowResponseDto.Response> followingId;
    private final List<FollowResponseDto.Response> followerId;
}
