package org.example.newsfeedproejct.board.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.newsfeedproejct.board.entity.Board;
import org.example.newsfeedproejct.comment.dto.CommentSearchDetailDto;

import java.time.LocalDateTime;
import java.util.List;


public class BoardSearchDetailDto {

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

        private final List<CommentSearchDetailDto.Response> comments;

        public static Response from(Board board, List<CommentSearchDetailDto.Response> comments) {
            return Response.builder()
                    .id(board.getId())
                    .subject(board.getSubject())
                    .content(board.getContent())
                    .userId(board.getUser().getId())
                    .nickname(board.getUser().getNickname())
                    .comments(comments)
                    .createdAt(board.getCreatedAt())
                    .updatedAt(board.getUpdatedAt())
                    .deletedAt(board.getDeletedAt())
                    .build();
        }
    }
}