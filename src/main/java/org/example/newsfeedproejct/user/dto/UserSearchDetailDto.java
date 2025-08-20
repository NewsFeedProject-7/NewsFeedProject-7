package org.example.newsfeedproejct.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.example.newsfeedproejct.user.entity.User;

public class UserSearchDetailDto {
    @Getter
    @Builder
    public static class Response {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final String email;
        private final String nickname;
        private final Boolean isFollowing;

        public static Response from(User user) {
            return Response.builder()
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .isFollowing(null)
                    .build();

        }
    }
}