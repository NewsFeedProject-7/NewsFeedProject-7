package org.example.newsfeedproejct.global.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LikeErrorCode implements ErrorCode {

    // 4xx: 클라이언트 오류
    CANNOT_LIKE_OWN_BOARD(HttpStatus.BAD_REQUEST, "LIKE-001", "본인의 게시글에는 좋아요를 할 수 없습니다."),
    CANNOT_LIKE_OWN_COMMENT(HttpStatus.BAD_REQUEST, "LIKE-002", "본인의 댓글에는 좋아요를 할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
