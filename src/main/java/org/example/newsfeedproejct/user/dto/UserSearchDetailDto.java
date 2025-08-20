package org.example.newsfeedproejct.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.user.entity.User; // User 엔티티 import
import org.example.newsfeedproejct.user.validation.annotation.ValidEmail;
import org.example.newsfeedproejct.user.validation.annotation.ValidNickname;

public class UserSearchDetailDto {

    @Getter
    @RequiredArgsConstructor
    public static class Request {
        @NotBlank(message = "아이디값은 필수값입니다.")
        private final Long id;
    }

    @Getter
    public static class Response {
        @ValidEmail
        private final String email;

        @ValidNickname
        private final String nickname;

        public Response(User user) {
            this.email = user.getEmail();
            this.nickname = user.getNickname();
        }
    }
}