package org.example.newsfeedproejct.user.dto;

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
        private String password;

        private String confirmPassword;

        @ValidNickname
        private String nickname;
    }

}
