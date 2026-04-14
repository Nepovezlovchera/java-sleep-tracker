package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analysis.MinDurationFunction;
import ru.yandex.practicum.sleeptracker.analysis.TotalSessionsFunction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinDurationFunctionTest {
    private static final char ZERO = 0;

    @Test
    void should_ReturnZeroForEmptyList() {
        MinDurationFunction minDurationFunction = new MinDurationFunction();
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        SleepAnalysisResult test = minDurationFunction.apply(sleepingSessions);

        assertEquals(ZERO, test.getValue());
    }

    @Test
    void should_ReturnMinDurationForList_WithTwoSessions() {
        LocalDateTime start1 = LocalDateTime.of(2025, 10, 1, 23, 0);
        LocalDateTime end1 = LocalDateTime.of(2025, 10, 2, 7, 0);
        SleepingSession session1 = new SleepingSession(start1, end1, SleepQuality.GOOD);

        LocalDateTime start2 = LocalDateTime.of(2025, 10, 1, 22, 0);
        LocalDateTime end2 = LocalDateTime.of(2025, 10, 2, 7, 0);
        SleepingSession session2 = new SleepingSession(start2, end2, SleepQuality.GOOD);


        MinDurationFunction minDurationFunction = new MinDurationFunction();
        List<SleepingSession> sleepingSessions = List.of(session1, session2);
        SleepAnalysisResult test = minDurationFunction.apply(sleepingSessions);

        long expectedMin = Math.min(session1.getDurationMinutes(), session2.getDurationMinutes());

        assertEquals(expectedMin, test.getValue());
    }
}
