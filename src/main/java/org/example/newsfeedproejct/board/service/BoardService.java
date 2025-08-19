package org.example.newsfeedproejct.board.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.board.dto.BoardCreateDto;
import org.example.newsfeedproejct.board.entity.Board;
import org.example.newsfeedproejct.board.repository.BoardRepository;
import org.example.newsfeedproejct.user.entity.User;
import org.example.newsfeedproejct.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 피드 생성
    public BoardCreateDto.Response createBoard(Long userId, String subject, String content) {
        User findUser = userRepository.findByIdOrElseThrow(userId);
        Board savedBoard = boardRepository.save(new Board(subject, content, findUser));
        return BoardCreateDto.Response.from(savedBoard);
    }
}
