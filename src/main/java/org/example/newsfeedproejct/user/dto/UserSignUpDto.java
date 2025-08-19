package org.example.newsfeedproejct.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.example.newsfeedproejct.user.validation.annotation.ValidEmail;
import org.example.newsfeedproejct.user.validation.annotation.ValidPassword;

public class UserSignUpDto {

    @Getter
    public static class Request {

        @ValidEmail
        private String email;

        @ValidPassword
        private String password;

        private String confirmPassword;

        @NotBlank(message = "닉네임은 필수값입니다.")
        private String nickname;
    }

}
