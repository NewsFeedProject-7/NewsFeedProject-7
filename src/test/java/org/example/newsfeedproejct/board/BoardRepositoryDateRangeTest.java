// src/test/java/.../BoardRepositoryDateRangeTest.java
package org.example.newsfeedproejct.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.example.newsfeedproejct.board.entity.Board;
import org.example.newsfeedproejct.board.repository.BoardRepository;
import org.example.newsfeedproejct.user.entity.User;
import org.example.newsfeedproejct.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@Import(BoardRepositoryDateRangeTest.TestQuerydslConfig.class)
class BoardRepositoryDateRangeTest {
    @TestConfiguration
    static class TestQuerydslConfig {
        @Bean
        JPAQueryFactory jpaQueryFactory(EntityManager em) {
            return new JPAQueryFactory(em);
        }
    }
    @Autowired BoardRepository boardRepository;
    @Autowired BoardRepository boardRepositoryCustom;
    @Autowired UserRepository userRepository;

    User u;

    @BeforeEach
    void setUp() {
        u = userRepository.save(new User("alice", "a@a.com", "Pw11!!qq"));

        // 2025-08-21 00:00, 12:00, 23:59에 해당하는 보드
        Board b = new Board("subj1", "c1", u);
        boardRepository.save(b);
        boardRepository.setCreatedAtForTest(b.getId(), LocalDateTime.of(2025,8,21,0,0));

        b = new Board("subj2", "c2", u);
        boardRepository.save(b);
        boardRepository.setCreatedAtForTest(b.getId(), LocalDateTime.of(2025,8,21,12,0));

        b = new Board("subj3", "c3", u);
        boardRepository.save(b);
        boardRepository.setCreatedAtForTest(b.getId(), LocalDateTime.of(2025,8,21,23,59));

        // 2025-08-22 00:00 (다음 날 시작 지점)
        b = new Board("subj4", "c4", u);
        boardRepository.save(b);
        boardRepository.setCreatedAtForTest(b.getId(), LocalDateTime.of(2025,8,22,0,0));
    }

    @Test
    @Transactional
    void start_inclusive_end_exclusive_works() {
        LocalDate start = LocalDate.of(2025, 8, 21);
        LocalDate end   = LocalDate.of(2025, 8, 21);
        LocalDateTime startAt = start.atStartOfDay();                           // 21 00:00
        LocalDateTime endExclusive = end.plusDays(1).atStartOfDay(); // 22 00:00

        var page = boardRepositoryCustom.findBoardsByDateWithUserDto(
                startAt, endExclusive,
                PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdAt"))
        );

        // 21일 0시~23:59는 포함, 22일 0시는 제외되어야 함
        assertThat(page.getTotalElements()).isEqualTo(3);
        assertThat(page.getContent())
                .allMatch(b -> !b.getCreatedAt().isBefore(startAt) && b.getCreatedAt().isBefore(endExclusive));
    }
}
