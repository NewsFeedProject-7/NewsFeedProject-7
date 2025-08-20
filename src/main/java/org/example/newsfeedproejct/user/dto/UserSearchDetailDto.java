package org.example.newsfeedproejct.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.newsfeedproejct.user.entity.User;

public class UserSearchDetailDto {
    @Getter
    @Builder
    public static class Response {
        private final String email;
        private final String nickname;

        public static Response from(User user) {
            return Response.builder()
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .build();

        }
    }
}