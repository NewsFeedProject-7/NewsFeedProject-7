package org.example.newsfeedproejct.global.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FollowErrorCode implements ErrorCode {

    // 4xx: 클라이언트 오류
    ALREADY_FOLLOWING(HttpStatus.CONFLICT, "FOLLOW-001", "이미 팔로우 중인 유저입니다."),
    NOT_FOLLOWING_YET(HttpStatus.CONFLICT, "FOLLOW-002", "아직 팔로우하지 않은 유저입니다."),
    CANNOT_FOLLOW_YOURSELF(HttpStatus.BAD_REQUEST, "FOLLOW-003", "자기 자신을 팔로우할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}