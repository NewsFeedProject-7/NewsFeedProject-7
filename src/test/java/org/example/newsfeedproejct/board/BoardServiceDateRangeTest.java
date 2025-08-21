package org.example.newsfeedproejct.board;

import jakarta.transaction.Transactional;
import org.example.newsfeedproejct.board.service.BoardService;
import org.example.newsfeedproejct.global.exception.GlobalException;
import org.example.newsfeedproejct.global.exception.errorcode.BoardErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceDateRangeTest {

    @Autowired BoardService boardService;

    @Test
    @Transactional
    @DisplayName("시작일/종료일: 종료가 시작보다 빠르면 INVALID_DATE_RANGE")
    void invalid_range_throws() {
        LocalDate start = LocalDate.of(2025, 8, 22);
        LocalDate end   = LocalDate.of(2025, 8, 21);

        GlobalException ex = assertThrows(GlobalException.class,
                () -> boardService.searchBoards(0, 10, start, end));

        assertEquals(BoardErrorCode.INVALID_DATE_RANGE, ex.getErrorCode());
    }

    @Test
    @Transactional
    @DisplayName("같은 날짜(start==end)는 허용 (해당 날짜 하루만 검색)")
    void same_day_is_allowed() {
        LocalDate day = LocalDate.of(2025, 8, 21);
        assertDoesNotThrow(() -> boardService.searchBoards(0, 10, day, day));
    }

    @Test
    @Transactional
    @DisplayName("start만 있거나 end만 있어도 예외 없이 동작")
    void null_params_ok() {
        assertDoesNotThrow(() -> boardService.searchBoards(0, 10, LocalDate.of(2025, 8, 21), null));
        assertDoesNotThrow(() -> boardService.searchBoards(0, 10, null, LocalDate.of(2025, 8, 21)));
    }
}
