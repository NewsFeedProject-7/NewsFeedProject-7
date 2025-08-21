package org.example.newsfeedproejct.like.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.global.consts.Const;
import org.example.newsfeedproejct.like.dto.LikeToggle;
import org.example.newsfeedproejct.like.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/boards/{boardId}/likes")
    @ResponseStatus(HttpStatus.OK)
    public LikeToggle.Response toggleBoardLike(
            @PathVariable Long boardId,
            @SessionAttribute(Const.LOGIN_USER) Long loginUserId
    ) {

        return likeService.toggleBoardLike(boardId, loginUserId);
    }

    @PostMapping("/comments/{commentId}/likes")
    @ResponseStatus(HttpStatus.OK)
    public LikeToggle.Response toggleCommentLike(
            @PathVariable Long commentId,
            @SessionAttribute(Const.LOGIN_USER) Long loginUserId
    ) {

        return likeService.toggleCommentLike(commentId, loginUserId);
    }

}
