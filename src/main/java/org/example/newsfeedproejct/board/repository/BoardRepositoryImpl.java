package org.example.newsfeedproejct.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.board.dto.BoardSearchDto;
import org.example.newsfeedproejct.board.dto.QBoardSearchDto_Response;
import org.example.newsfeedproejct.board.entity.QBoard;
import org.example.newsfeedproejct.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
}
