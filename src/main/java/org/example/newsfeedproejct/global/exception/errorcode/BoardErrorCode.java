package org.example.newsfeedproejct.global.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardErrorCode implements ErrorCode {
    // 4xx: 클라이언트 오류
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "BOARD-001", "삭제되었거나 존재하지 않는 게시글입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
