package org.example.newsfeedproejct.like.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.board.entity.Board;
import org.example.newsfeedproejct.board.repository.BoardRepository;
import org.example.newsfeedproejct.comment.entity.Comment;
import org.example.newsfeedproejct.comment.repository.CommentRepository;
import org.example.newsfeedproejct.global.exception.GlobalException;
import org.example.newsfeedproejct.global.exception.errorcode.LikeErrorCode;
import org.example.newsfeedproejct.like.dto.LikeToggle;
import org.example.newsfeedproejct.like.entity.BoardLike;
import org.example.newsfeedproejct.like.entity.BoardLikeId;
import org.example.newsfeedproejct.like.entity.CommentLike;
import org.example.newsfeedproejct.like.entity.CommentLikeId;
import org.example.newsfeedproejct.like.repository.BoardLikeRepository;
import org.example.newsfeedproejct.like.repository.CommentLikeRepository;
import org.example.newsfeedproejct.user.entity.User;
import org.example.newsfeedproejct.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;

    @Transactional
    public LikeToggle.Response toggleBoardLike(Long boardId, Long loginUserId) {

        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);

        if (findBoard.isOwnedBy(loginUserId)) {
            throw new GlobalException(LikeErrorCode.CANNOT_LIKE_OWN_BOARD);
        }

        User findUser = userRepository.findByIdOrElseThrow(loginUserId);

        BoardLikeId boardLikeId = BoardLikeId.builder()
                .boardId(findBoard.getId())
                .userId(findUser.getId())
                .build();

        // 현재 좋아요 여부
        boolean liked = boardLikeRepository.existsById(boardLikeId);

        if (liked) {
            boardLikeRepository.deleteById(boardLikeId);
            boardRepository.decrementLikeCount(findBoard.getId());
        } else {
            boardLikeRepository.save(BoardLike.of(findBoard, findUser));
            boardRepository.incrementLikeCount(findBoard.getId());
        }

        long likeCount = boardRepository.findLikeCountById(findBoard.getId());

        return LikeToggle.Response.builder()
                .liked(!liked)
                .likeCount(likeCount)
                .build();
    }

    @Transactional
    public LikeToggle.Response toggleCommentLike(Long commentId, Long loginUserId) {

        Comment findComment = commentRepository.findByIdOrElseThrow(commentId);

        if (findComment.isOwnedBy(loginUserId)) {
            throw new GlobalException(LikeErrorCode.CANNOT_LIKE_OWN_COMMENT);
        }

        User findUser = userRepository.findByIdOrElseThrow(loginUserId);

        CommentLikeId commentLikeId = CommentLikeId.builder()
                .commentId(findComment.getId())
                .userId(findUser.getId())
                .build();

        // 현재 좋아요 여부
        boolean liked = commentLikeRepository.existsById(commentLikeId);

        if (liked) {
            commentLikeRepository.deleteById(commentLikeId);
            commentRepository.decrementLikeCount(findComment.getId());
        } else {
            commentLikeRepository.save(CommentLike.of(findComment, findUser));
            commentRepository.incrementLikeCount(findComment.getId());
        }

        long likeCount = commentRepository.findLikeCountById(findComment.getId());

        return LikeToggle.Response.builder()
                .liked(!liked)
                .likeCount(likeCount)
                .build();
    }
}
