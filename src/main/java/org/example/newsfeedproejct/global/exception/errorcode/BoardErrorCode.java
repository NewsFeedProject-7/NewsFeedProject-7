package org.example.newsfeedproejct.global.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardErrorCode implements ErrorCode {
    // 4xx: 클라이언트 오류
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "BOARD-001", "삭제되었거나 존재하지 않는 게시글입니다."),
    BOARD_VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "BOARD-002", "요청 값이 유효하지 않습니다."),
    BOARD_UNAUTHORIZED(HttpStatus.FORBIDDEN, "BOARD-003", "권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
