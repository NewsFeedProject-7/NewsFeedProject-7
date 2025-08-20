package org.example.newsfeedproejct.board.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.board.dto.BoardCreateDto;
import org.example.newsfeedproejct.board.dto.BoardSearchDto;
import org.example.newsfeedproejct.board.service.BoardService;
import org.example.newsfeedproejct.global.consts.Const;
import org.springframework.data.domain.Page;
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
                                               @SessionAttribute(Const.LOGIN_USER) Long userId) {
        return boardService.createBoard(userId, requestDto.getSubject(), requestDto.getContent());
    }

    // 피드 전체 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<BoardSearchDto.Response> searchBoards(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        return boardService.searchBoards(page, size);
    }
}
