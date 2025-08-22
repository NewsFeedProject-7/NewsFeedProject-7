package org.example.newsfeedproejct.board.repository;

import org.example.newsfeedproejct.board.dto.BoardSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface BoardRepositoryCustom {
    Page<BoardSearchDto.Response> findAllWithUserDto(Pageable pageable);

    Page<BoardSearchDto.Response> findBoardsByDateWithUserDto(LocalDateTime startAt,
                                                              LocalDateTime endExclusive,
                                                              Pageable pageable);

    Page<BoardSearchDto.Response> findBoardsByDatePrioritizeFollowingsWithUserDto(Long currentUserId,
                                                                                  LocalDateTime startAt,
                                                                                  LocalDateTime endExclusive,
                                                                                  Pageable pageable);
}
