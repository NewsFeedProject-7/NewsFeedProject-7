package org.example.newsfeedproejct.board.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.board.dto.BoardCreateDto;
import org.example.newsfeedproejct.board.dto.BoardSearchDetailDto;
import org.example.newsfeedproejct.board.dto.BoardSearchDto;
import org.example.newsfeedproejct.board.dto.BoardUpdateDto;
import org.example.newsfeedproejct.board.entity.Board;
import org.example.newsfeedproejct.board.repository.BoardRepository;
import org.example.newsfeedproejct.comment.dto.CommentSearchDetailDto;
import org.example.newsfeedproejct.comment.repository.CommentRepository;
import org.example.newsfeedproejct.global.exception.GlobalException;
import org.example.newsfeedproejct.global.exception.errorcode.BoardErrorCode;
import org.example.newsfeedproejct.user.entity.User;
import org.example.newsfeedproejct.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    // 피드 생성
    public BoardCreateDto.Response createBoard(Long loginUserId, String subject, String content) {
        User findUser = userRepository.findByIdOrElseThrow(loginUserId);
        Board savedBoard = boardRepository.save(new Board(subject, content, findUser));
        return BoardCreateDto.Response.from(savedBoard);
    }

    // 피드 전체 조회
    @Transactional(readOnly = true)
    public Page<BoardSearchDto.Response> searchBoards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        Page<Board> boards = boardRepository.findAll(pageable);
        return boards.map(BoardSearchDto.Response::from);
    }

    // 피드 단건 조회
    @Transactional(readOnly = true)
    public BoardSearchDetailDto.Response findById(Long id) {
        Board findBoard = boardRepository.findByIdOrElseThrow(id);
        List<CommentSearchDetailDto.Response> comments = commentRepository.findAllByBoardIdOrderByCreatedAt(id)
                .stream()
                .map(CommentSearchDetailDto.Response::from)
                .toList();
        return BoardSearchDetailDto.Response.from(findBoard, comments);
    }

    // 피드 수정
    public BoardUpdateDto.Response updateBoard(Long id, Long loginUserId, String subject, String content) {
        if (subject == null && content == null) {
            throw new GlobalException(BoardErrorCode.BOARD_VALIDATION_FAILED);
        }
        Board findBoard = boardRepository.findByIdOrElseThrow(id);

        if (!findBoard.getUser().getId().equals(loginUserId)) {
            throw new GlobalException(BoardErrorCode.BOARD_UNAUTHORIZED);
        }
        findBoard.updateBoard(subject, content);
        return BoardUpdateDto.Response.from(findBoard);
    }
}
