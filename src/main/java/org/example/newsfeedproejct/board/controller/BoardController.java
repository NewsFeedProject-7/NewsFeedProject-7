package org.example.newsfeedproejct.board.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.board.dto.BoardCreateDto;
import org.example.newsfeedproejct.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 피드 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardCreateDto.Response createBoard(@RequestBody BoardCreateDto.Request requestDto,
                                               @SessionAttribute("LOGIN_USER") Long userId) {
        return boardService.createBoard(userId, requestDto.getSubject(), requestDto.getContent());
    }
}
