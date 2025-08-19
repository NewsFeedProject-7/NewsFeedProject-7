package org.example.newsfeedproejct.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UserLoginDto {

    @Getter
    public static class Request {

        @NotBlank(message = "이메일은 필수값입니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수값입니다.")
        private String password;
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class Response {

        private final Long id;
    }

}
