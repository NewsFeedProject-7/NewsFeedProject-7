package org.example.newsfeedproejct.board.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.example.newsfeedproejct.board.entity.Board;

import java.time.LocalDateTime;

public class BoardUpdateDto {
    @Getter
    public static class Request {
        @Size(max = 50, message = "제목은 50자 이내여야 합니다.")
        private final String subject;
        private final String content;

        public Request(String subject, String content) {
            this.subject = subject;
            this.content = content;
        }
    }

    @Getter
    @Builder
    public static class Response {
        private final Long id;
        private final String subject;
        private final String content;
        private final Long userId;
        private final String nickname;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;
        private final LocalDateTime deletedAt;

        public static Response from(Board board) {
            return Response.builder()
                    .id(board.getId())
                    .subject(board.getSubject())
                    .content(board.getContent())
                    .userId(board.getUser().getId())
                    .nickname(board.getUser().getNickname())
                    .createdAt(board.getCreatedAt())
                    .updatedAt(board.getUpdatedAt())
                    .deletedAt(board.getDeletedAt())
                    .build();
        }
    }
}
