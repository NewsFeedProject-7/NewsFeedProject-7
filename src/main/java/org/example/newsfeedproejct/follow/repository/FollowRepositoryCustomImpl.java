package org.example.newsfeedproejct.follow.repository;

import com.querydsl.core.types.dsl.DateTimeTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.follow.dto.FollowFriendAnniversaryDto;
import org.example.newsfeedproejct.follow.dto.QFollowFriendAnniversaryDto_Response;
import org.example.newsfeedproejct.follow.entity.QFollow;
import org.example.newsfeedproejct.user.entity.QUser;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class FollowRepositoryCustomImpl implements FollowRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<FollowFriendAnniversaryDto.Response> findTodayFriendAnniversaries(Long loginUserId, LocalDateTime today) {
        QFollow f1 = QFollow.follow;
        QFollow f2 = new QFollow("f2");
        QUser u = QUser.user;

        DateTimeTemplate<LocalDateTime> friendshipDate = Expressions.dateTimeTemplate(
                LocalDateTime.class,
                "CASE WHEN {0} > {1} THEN {0} ELSE {1} END",
                f1.createdAt, f2.createdAt
        );

        NumberTemplate<Integer> friendshipMonth = Expressions.numberTemplate(Integer.class, "MONTH({0})", friendshipDate);
        NumberTemplate<Integer> friendshipDay = Expressions.numberTemplate(Integer.class, "DAY({0})", friendshipDate);

        return queryFactory
                .select(new QFollowFriendAnniversaryDto_Response(
                        u.id,
                        u.nickname,
                        Expressions.numberTemplate(Long.class, "0"), // years 나중 계산
                        friendshipDate
                ))
                .from(f1)
                .join(f2).on(f1.followingId.eq(f2.followerId))
                .join(u).on(u.id.eq(f1.followingId))
                .where(
                        f1.followerId.eq(loginUserId),
                        f2.followingId.eq(loginUserId),
                        friendshipMonth.eq(today.getMonthValue()),
                        friendshipDay.eq(today.getDayOfMonth())
                )
                .fetch();
    }
}