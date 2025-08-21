package org.example.newsfeedproejct.comment.repository;

import org.example.newsfeedproejct.comment.entity.Comment;
import org.example.newsfeedproejct.global.exception.GlobalException;
import org.example.newsfeedproejct.global.exception.errorcode.CommentErrorCode;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndDeletedAtIsNull(Long id);
    default Comment findByIdOrElseThrow(Long commentId){
        return findByIdAndDeletedAtIsNull(commentId).orElseThrow(() -> new GlobalException(CommentErrorCode.COMMENT_NOT_FOUND));
    }

    @EntityGraph(attributePaths = {"user", "board"})
    List<Comment> findByBoardIdAndDeletedAtIsNullOrderByCreatedAt(Long boardId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Comment c SET c.likeCount = c.likeCount + 1 WHERE c.id = :commentId")
    int incrementLikeCount(@Param("commentId") Long commentId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Comment c SET c.likeCount = GREATEST(c.likeCount - 1, 0) WHERE c.id = :commentId")
    int decrementLikeCount(@Param("commentId") Long commentId);

    @Query("SELECT COALESCE(c.likeCount, 0) FROM Comment c WHERE c.id = :commentId")
    long findLikeCountById(@Param("commentId") Long commentId);
}