package org.example.newsfeedproejct.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.example.newsfeedproejct.user.validation.annotation.ValidEmail;
import org.example.newsfeedproejct.user.validation.annotation.ValidNickname;
import org.example.newsfeedproejct.user.validation.annotation.ValidPassword;

public class UserSignUpDto {

    @Getter
    public static class Request {

        @ValidEmail
        private String email;

        @ValidPassword
        @NotBlank(message = "비밀번호는 필수값입니다.")
        private String password;

        @NotBlank(message = "비밀번호 확인은 필수값입니다.")
        private String confirmPassword;

        @ValidNickname
        @NotBlank(message = "닉네임은 필수값입니다.")
        private String nickname;
    }

}
