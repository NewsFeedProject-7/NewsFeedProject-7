package org.example.newsfeedproejct.board.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.newsfeedproejct.board.entity.Board;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardResponseDto {
    private final Long id;
    private final String subject;
    private final String content;
    private final Long userId;
    private final String nickname;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;

    public static BoardResponseDto from(Board board) {
        return BoardResponseDto.builder()
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
