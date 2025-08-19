package org.example.newsfeedproejct.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.example.newsfeedproejct.user.validation.annotation.ValidPassword;

public class UserSignUpDto {

    @Getter
    public static class Request {

        @NotBlank(message = "이메일은 필수값입니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        private String email;

        @ValidPassword
        private String password;

        private String confirmPassword;

        @NotBlank(message = "닉네임은 필수값입니다.")
        private String nickname;
    }

}
