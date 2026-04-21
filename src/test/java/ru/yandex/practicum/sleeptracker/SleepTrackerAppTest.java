package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analysis.TotalSessionsFunction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SleepTrackerAppTest {

    private static final long ZERO = 0L;
    private static final long ONE = 1L;

    @Test
    void should_ReturnZeroForEmptyList() {
        TotalSessionsFunction totalSessionsFunction = new TotalSessionsFunction();
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        SleepAnalysisResult test = totalSessionsFunction.apply(sleepingSessions);

        assertEquals(ZERO, test.getValue());
    }

    @Test
    void should_ReturnOneForList_WithOneSession() {
        LocalDateTime start = LocalDateTime.of(2025, 10, 1, 23, 15);
        LocalDateTime end = LocalDateTime.of(2025, 10, 2, 7, 30);
        SleepingSession session = new SleepingSession(start, end, SleepQuality.GOOD);

        TotalSessionsFunction totalSessionsFunction = new TotalSessionsFunction();
        List<SleepingSession> sleepingSessions = List.of(session);
        SleepAnalysisResult test = totalSessionsFunction.apply(sleepingSessions);

        assertEquals(ONE, test.getValue());
    }
}