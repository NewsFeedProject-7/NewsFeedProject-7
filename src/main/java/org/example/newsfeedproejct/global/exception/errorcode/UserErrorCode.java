package org.example.newsfeedproejct.global.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    // 4xx: 클라이언트 오류
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER-001", "삭제되었거나 존재하지 않는 유저입니다."),
    PASSWORD_NOT_MATCHED(HttpStatus.BAD_REQUEST, "USER-002", "비밀번호가 일치하지 않습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "USER-003", "이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "USER-004", "이미 존재하는 닉네임입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
