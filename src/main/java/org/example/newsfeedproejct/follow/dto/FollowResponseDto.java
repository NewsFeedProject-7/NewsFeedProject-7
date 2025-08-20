package org.example.newsfeedproejct.follow.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.newsfeedproejct.user.entity.User;

@Getter
@Builder
public class FollowResponseDto {

    private final long userId;
    private final String nickname;

    public static FollowResponseDto from(User user) {
        return FollowResponseDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .build();
    }

}
