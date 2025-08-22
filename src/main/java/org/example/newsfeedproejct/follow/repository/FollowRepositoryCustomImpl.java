package org.example.newsfeedproejct.follow.repository;

import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.follow.dto.FollowFriendAnniversaryDto;
import org.example.newsfeedproejct.follow.entity.QFollow;
import org.example.newsfeedproejct.user.entity.QUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FollowRepositoryCustomImpl implements FollowRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<FollowFriendAnniversaryDto.Response> findTodayFriendAnniversaries(Long loginUserId, LocalDate today) {
        QFollow f1 = QFollow.follow;
        QFollow f2 = new QFollow("f2");
        QUser u = QUser.user;

        DateTemplate<LocalDateTime> friendshipDateTemplate = Expressions.dateTemplate(
                LocalDateTime.class,
                "CASE WHEN {0} > {1} THEN {0} ELSE {1} END",
                f1.createdAt, f2.createdAt
        );

        return queryFactory
                .select(u.id, u.nickname, friendshipDateTemplate)
                .from(f1)
                .join(f2).on(f1.followingId.eq(f2.followerId))
                .join(u).on(u.id.eq(f1.followingId))
                .where(
                        f1.followerId.eq(loginUserId),
                        f2.followingId.eq(loginUserId)
                )
                .fetch()
                .stream()
                .map(tuple -> {
                    Long friendId = tuple.get(u.id);
                    String nickname = tuple.get(u.nickname);
                    LocalDate friendshipDate = Optional.ofNullable(tuple.get(friendshipDateTemplate))
                            .map(LocalDateTime::toLocalDate)
                            .orElse(null);

                    // 오늘 날짜와 비교
                    if (friendshipDate != null) {
                        if (friendshipDate.getMonthValue() == today.getMonthValue()
                                && friendshipDate.getDayOfMonth() == today.getDayOfMonth()) {
                            long years = ChronoUnit.YEARS.between(friendshipDate, today);
                            return new FollowFriendAnniversaryDto.Response(friendId, nickname, years, friendshipDate);
                        }
                    }
                    return null; // 오늘 기념일이 아니면 null
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}