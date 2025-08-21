package org.example.newsfeedproejct.board.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.board.dto.BoardSearchDto;
import org.example.newsfeedproejct.board.dto.QBoardSearchDto_Response;
import org.example.newsfeedproejct.board.entity.QBoard;
import org.example.newsfeedproejct.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public Page<BoardSearchDto.Response> findAllWithUserDto(Pageable pageable) {
        QBoard board = QBoard.board;
        QUser user = QUser.user;

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
    public Page<BoardSearchDto.Response> findBoardsByDateWithUserDto(LocalDateTime startAt, LocalDateTime endExclusive, Pageable pageable) {
        QBoard board = QBoard.board;
        QUser user = QUser.user;

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

}
