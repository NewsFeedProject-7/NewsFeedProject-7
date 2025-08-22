package org.example.newsfeedproejct.follow.repository;

import org.example.newsfeedproejct.follow.dto.FollowFriendAnniversaryDto;

import java.time.LocalDateTime;
import java.util.List;

public interface FollowRepositoryCustom {
    List<FollowFriendAnniversaryDto.Response> findTodayFriendAnniversaries(Long loginUserId, LocalDateTime today);
}
