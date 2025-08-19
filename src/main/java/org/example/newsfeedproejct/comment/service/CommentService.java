package org.example.newsfeedproejct.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.board.entity.Board;
import org.example.newsfeedproejct.board.repository.BoardRepository;
import org.example.newsfeedproejct.comment.dto.CommentCreateDto;
import org.example.newsfeedproejct.comment.dto.CommentSearchDto;
import org.example.newsfeedproejct.comment.dto.CommentUpdateDto;
import org.example.newsfeedproejct.comment.entity.Comment;
import org.example.newsfeedproejct.comment.repository.CommentRepository;
import org.example.newsfeedproejct.global.exception.GlobalException;
import org.example.newsfeedproejct.global.exception.errorcode.CommentErrorCode;
import org.example.newsfeedproejct.user.entity.User;
import org.example.newsfeedproejct.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentCreateDto.Response createComment(Long userId, Long boardId, String content) {
        User user = userRepository.findByIdOrElseThrow(userId);
        Board board = boardRepository.findByIdOrElseThrow(boardId);

        Comment comment = new Comment(user, board, content);

        commentRepository.save(comment);
        return CommentCreateDto.Response.from(comment);
    }
}
