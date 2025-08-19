package org.example.newsfeedproejct.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class UserSignUpDto {

    @Getter
    public static class Request {

        @NotBlank(message = "이메일은 필수값입니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수값입니다.")
        @Size(min = 4, message = "비밀번호는 최소 4자 이상이어야 합니다.")
        private String password;

        private String confirmPassword;

        @NotBlank(message = "닉네임은 필수값입니다.")
        private String nickname;
    }

}
