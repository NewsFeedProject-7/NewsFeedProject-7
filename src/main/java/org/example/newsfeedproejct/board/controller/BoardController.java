package org.example.newsfeedproejct.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.board.dto.BoardCreateDto;
import org.example.newsfeedproejct.board.dto.BoardSearchDetailDto;
import org.example.newsfeedproejct.board.dto.BoardSearchDto;
import org.example.newsfeedproejct.board.dto.BoardUpdateDto;
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
    public BoardCreateDto.Response createBoard(@Valid @RequestBody BoardCreateDto.Request requestDto,
                                               @SessionAttribute(Const.LOGIN_USER) Long loginUserId) {
        return boardService.createBoard(loginUserId, requestDto.getSubject(), requestDto.getContent());
    }

    // 피드 전체 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<BoardSearchDto.Response> searchBoards(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        return boardService.searchBoards(page, size);
    }

    // 피드 단건 조회
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BoardSearchDetailDto.Response findById(@PathVariable Long id) {
        return boardService.findById(id);
    }

    // 피드 수정
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BoardUpdateDto.Response updateBoard(@PathVariable Long id,
                                               @Valid @RequestBody BoardUpdateDto.Request requestDto,
                                               @SessionAttribute(Const.LOGIN_USER) Long loginUserId) {
        return boardService.updateBoard(id, loginUserId, requestDto.getSubject(), requestDto.getContent());
    }

    // 피드 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBoard(@PathVariable Long id,
                            @SessionAttribute(Const.LOGIN_USER) Long loginUserId) {
        boardService.deleteBoard(id, loginUserId);
    }
}
