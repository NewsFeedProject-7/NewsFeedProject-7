package org.example.newsfeedproejct.like.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class LikeToggle {

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class Response {

        private final boolean liked;
        private final long likeCount;
    }

}
