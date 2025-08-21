package org.example.newsfeedproejct.board.repository;

import org.example.newsfeedproejct.board.entity.Board;
import org.example.newsfeedproejct.global.exception.GlobalException;
import org.example.newsfeedproejct.global.exception.errorcode.BoardErrorCode;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    @EntityGraph(attributePaths = {"user"})
    Optional<Board> findByIdAndDeletedAtIsNull(Long id);

    default Board findByIdOrElseThrow(Long id) {
        return findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new GlobalException(BoardErrorCode.BOARD_NOT_FOUND));
    }

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Board b SET b.likeCount = b.likeCount + 1 WHERE b.id = :boardId")
    int incrementLikeCount(@Param("boardId") Long boardId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Board b SET b.likeCount = GREATEST(b.likeCount - 1, 0) WHERE b.id = :boardId")
    int decrementLikeCount(@Param("boardId") Long boardId);

    @Query("SELECT COALESCE(b.likeCount, 0) FROM Board b WHERE b.id = :boardId")
    long findLikeCountById(@Param("boardId") Long boardId);
}
