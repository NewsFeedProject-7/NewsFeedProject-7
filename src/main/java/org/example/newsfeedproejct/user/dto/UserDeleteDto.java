package org.example.newsfeedproejct.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class UserDeleteDto {

    @Getter
    public static class Request {

        @NotBlank(message = "본인확인용 비밀번호는 필수값입니다.")
        private String currentPassword;
    }

}
