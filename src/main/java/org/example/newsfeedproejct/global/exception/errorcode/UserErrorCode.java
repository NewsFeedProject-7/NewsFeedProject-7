package org.example.newsfeedproejct.global.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    // 4xx: 클라이언트 오류
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER-001", "삭제되었거나 존재하지 않는 유저입니다."),
    CONFIRM_PASSWORD_NOT_MATCHED(HttpStatus.BAD_REQUEST, "USER-002", "비밀번호가 서로 일치하지 않습니다."),
    CURRENT_PASSWORD_NOT_MATCHED(HttpStatus.UNAUTHORIZED, "USER-003", "본인확인용 비밀번호가 일치하지 않습니다"),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "USER-003", "이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "USER-004", "이미 존재하는 닉네임입니다."),
    INVALID_EMAIL(HttpStatus.UNAUTHORIZED, "USER-005", "이메일이 올바르지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "USER-006", "비밀번호가 올바르지 않습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "USER-007", "해당 작업을 수행할 권한이 없습니다."),
    SAME_AS_OLD_PASSWORD(HttpStatus.BAD_REQUEST, "USER-008", "현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다."),
    INVALID_PASSWORD_INPUT(HttpStatus.BAD_REQUEST, "USER-009", "비밀번호와 비밀번호 확인을 모두 입력해주세요.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
