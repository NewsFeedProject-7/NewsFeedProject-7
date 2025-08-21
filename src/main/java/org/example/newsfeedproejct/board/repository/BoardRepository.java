package org.example.newsfeedproejct.board.repository;

import org.example.newsfeedproejct.board.entity.Board;
import org.example.newsfeedproejct.global.exception.GlobalException;
import org.example.newsfeedproejct.global.exception.errorcode.BoardErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    Optional<Board> findByIdAndDeletedAtIsNull(Long id);

    default Board findByIdOrElseThrow(Long id) {
        return findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new GlobalException(BoardErrorCode.BOARD_NOT_FOUND));
    }
}
