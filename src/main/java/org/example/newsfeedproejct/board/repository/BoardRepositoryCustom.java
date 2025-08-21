package org.example.newsfeedproejct.board.repository;

import org.example.newsfeedproejct.board.dto.BoardSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {
    Page<BoardSearchDto.Response> findAllWithUserDto(Pageable pageable);
}
