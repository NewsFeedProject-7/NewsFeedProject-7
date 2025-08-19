package org.example.newsfeedproejct.comment.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.comment.dto.CommentCreateDto;
import org.example.newsfeedproejct.comment.dto.CommentUpdateDto;
import org.example.newsfeedproejct.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/boards/{boardId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentCreateDto.Response createComment(@SessionAttribute("LOGIN_USER") Long userId, @PathVariable("boardId") Long boardId, @RequestBody CommentCreateDto.Request commentCreateDto) {
        return commentService.createComment(userId, boardId, commentCreateDto.getContent());
    }

    @PatchMapping("/boards/{boardId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentUpdateDto.Response updateComment(@SessionAttribute("LOGIN_USER") Long userId, @PathVariable("boardId") Long boardId, @PathVariable("commentId") Long commentId, @RequestBody CommentUpdateDto.Request commentUpdateDto) {
        return commentService.updateComment(userId, boardId, commentId, commentUpdateDto.getContent());
    }
}
