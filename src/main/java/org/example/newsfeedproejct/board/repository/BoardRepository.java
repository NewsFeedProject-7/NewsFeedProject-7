package org.example.newsfeedproejct.board.repository;

import org.example.newsfeedproejct.board.entity.Board;
import org.example.newsfeedproejct.global.exception.GlobalException;
import org.example.newsfeedproejct.global.exception.errorcode.BoardErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @EntityGraph(attributePaths = {"user"})
    Page<Board> findAllByDeletedAtIsNull(Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Optional<Board> findByIdAndDeletedAtIsNull(Long id);

    default Board findByIdOrElseThrow(Long id) {
        return findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new GlobalException(BoardErrorCode.BOARD_NOT_FOUND));
    }
}
