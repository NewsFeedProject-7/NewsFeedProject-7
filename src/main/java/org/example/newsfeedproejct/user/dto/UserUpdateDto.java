package org.example.newsfeedproejct.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.user.entity.User;
import org.example.newsfeedproejct.user.validation.annotation.ValidNickname;
import org.example.newsfeedproejct.user.validation.annotation.ValidPassword;

import java.time.LocalDateTime;

public class UserUpdateDto {

    @Getter
    public static class Request {

        @ValidNickname
        private String nickname;

        @NotBlank(message = "본인확인용 비밀번호는 필수값입니다.")
        private String currentPassword;

        @ValidPassword
        private String changePassword;

        private String confirmPassword;
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class Response {

        private final Long id;
        private final String nickname;
        private final LocalDateTime updatedAt;

        public static Response from(User user) {
            return Response.builder()
                    .id(user.getId())
                    .nickname(user.getNickname())
                    .updatedAt(user.getUpdatedAt())
                    .build();
        }
    }

}
