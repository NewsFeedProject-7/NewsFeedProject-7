package org.example.newsfeedproejct.follow.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;


public class FollowFriendAnniversaryDto {
    @Getter
    public static class Response {
        private final Long friendId;
        private final String nickname;
        private final long years;
        private final LocalDateTime friendshipDate;

        @QueryProjection
        public Response(Long friendId, String nickname, long years, LocalDateTime friendshipDate) {
            this.friendId = friendId;
            this.nickname = nickname;
            this.years = years;
            this.friendshipDate = friendshipDate;
        }
    }
}
