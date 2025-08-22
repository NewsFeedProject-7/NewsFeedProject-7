package org.example.newsfeedproejct.follow.repository;

import org.example.newsfeedproejct.follow.dto.FollowFriendAnniversaryDto;

import java.time.LocalDate;
import java.util.List;

public interface FollowRepositoryCustom {
    List<FollowFriendAnniversaryDto.Response> findTodayFriendAnniversaries(Long loginUserId, LocalDate today);
}
