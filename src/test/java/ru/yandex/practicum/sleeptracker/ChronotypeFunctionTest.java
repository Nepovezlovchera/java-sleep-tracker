package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analysis.BadSessionsCountFunction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChronotypeFunctionTest {

    private static final char ZERO = 0;
    private static final char ONE = 1;

    @Test
    void should_ReturnZeroForEmptyList() {
        BadSessionsCountFunction badSessionsCountFunction = new BadSessionsCountFunction();
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        SleepAnalysisResult test = badSessionsCountFunction.apply(sleepingSessions);

        assertEquals(ZERO, test.getValue());
    }

    @Test
    void should_ReturnCountBADSessionForList_WithTwoSessions() {
        LocalDateTime start1 = LocalDateTime.of(2025, 10, 1, 23 , 0);
        LocalDateTime end1 = LocalDateTime.of(2025, 10, 2, 7 , 0);
        SleepingSession session1 = new SleepingSession(start1, end1, SleepQuality.BAD);

        LocalDateTime start2 = LocalDateTime.of(2025, 10, 1, 22 , 0);
        LocalDateTime end2 = LocalDateTime.of(2025, 10, 2, 7 , 0);
        SleepingSession session2 = new SleepingSession(start2, end2, SleepQuality.GOOD);


        BadSessionsCountFunction badSessionsCountFunction = new BadSessionsCountFunction();
        List<SleepingSession> sleepingSessions = List.of(session1, session2);
        SleepAnalysisResult test = badSessionsCountFunction.apply(sleepingSessions);


        assertEquals(ONE, test.getValue());
    }
}
