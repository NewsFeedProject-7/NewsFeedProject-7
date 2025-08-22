package org.example.newsfeedproejct.board.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.board.dto.BoardSearchDto;
import org.example.newsfeedproejct.board.dto.QBoardSearchDto_Response;
import org.example.newsfeedproejct.board.entity.QBoard;
import org.example.newsfeedproejct.follow.entity.QFollow;
import org.example.newsfeedproejct.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QBoard board = QBoard.board;
    private final QUser user = QUser.user;
    private final QFollow follow = QFollow.follow;


    @Override
    public Page<BoardSearchDto.Response> findAllWithUserDto(Pageable pageable) {
        List<BoardSearchDto.Response> content = queryFactory
                .select(new QBoardSearchDto_Response(
                        board.id,
                        board.subject,
                        board.content,
                        board.likeCount,
                        user.id,
                        user.nickname,
                        board.createdAt,
                        board.updatedAt
                ))
                .from(board)
                .join(board.user, user)
                .where(board.deletedAt.isNull())
                .orderBy(board.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(board.count())
                .from(board)
                .where(board.deletedAt.isNull())
                .fetchOne();

        long total = totalCount != null ? totalCount : 0L;

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<BoardSearchDto.Response> findBoardsByDateWithUserDto(LocalDateTime startAt,
                                                                     LocalDateTime endExclusive,
                                                                     Pageable pageable) {
        BooleanExpression geStart = (startAt == null) ? null : board.createdAt.goe(startAt);
        BooleanExpression ltEnd   = (endExclusive == null) ? null : board.createdAt.lt(endExclusive);

        List<BoardSearchDto.Response> content = queryFactory
                .select(new QBoardSearchDto_Response(
                        board.id,
                        board.subject,
                        board.content,
                        board.likeCount,
                        user.id,
                        user.nickname,
                        board.createdAt,
                        board.updatedAt
                ))
                .from(board)
                .join(board.user, user)
                .where(geStart,
                        ltEnd,
                        board.deletedAt.isNull())
                .orderBy(board.createdAt.desc(), board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(board.count())
                .from(board)
                .where(geStart,
                        ltEnd,
                        board.deletedAt.isNull())
                .fetchOne();

        long total = totalCount != null ? totalCount : 0L;

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<BoardSearchDto.Response> findBoardsByDatePrioritizeFollowingsWithUserDto(Long loginUserId,
                                                                                         LocalDateTime startAt,
                                                                                         LocalDateTime endExclusive,
                                                                                         Pageable pageable) {
        BooleanExpression geStart = (startAt == null) ? null : board.createdAt.goe(startAt);
        BooleanExpression ltEnd   = (endExclusive == null) ? null : board.createdAt.lt(endExclusive);

        List<Long> followingIds = queryFactory
                .select(follow.followingId)
                .from(follow)
                .where(follow.followerId.eq(loginUserId))
                .fetch();

        NumberExpression<Integer> followRank =
                new CaseBuilder().when(board.user.id.in(followingIds)).then(1).otherwise(0);

        List<BoardSearchDto.Response> content = queryFactory
                .select(new QBoardSearchDto_Response(
                        board.id,
                        board.subject,
                        board.content,
                        board.likeCount,
                        user.id,
                        user.nickname,
                        board.createdAt,
                        board.updatedAt
                ))
                .from(board)
                .join(board.user, user)
                .where(geStart,
                        ltEnd,
                        board.deletedAt.isNull())
                .orderBy(followRank.desc(), board.createdAt.desc(), board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(board.count())
                .from(board)
                .where(geStart,
                        ltEnd,
                        board.deletedAt.isNull())
                .fetchOne();

        long total = totalCount != null ? totalCount : 0L;

        return new PageImpl<>(content, pageable, total);
    }

}
