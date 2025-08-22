package org.example.newsfeedproejct.follow.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.newsfeedproejct.user.entity.User;


public class FollowResponseDto {
    @Getter
    @Builder
    public static class Response {
        private final long userId;
        private final String nickname;

        public static Response from(User user) {
            return Response.builder()
                    .userId(user.getId())
                    .nickname(user.getNickname())
                    .build();
        }
    }
}