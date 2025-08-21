package org.example.newsfeedproejct.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;


public class BoardSearchDto {

    @Getter
    public static class Response {
        private final Long id;
        private final String subject;
        private final String content;
        private final Long userId;
        private final String nickname;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;

        @QueryProjection
        public Response(Long id, String subject, String content,
                        Long userId, String nickname,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.id = id;
            this.subject = subject;
            this.content = content;
            this.userId = userId;
            this.nickname = nickname;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }
}
