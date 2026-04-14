package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analysis.MaxDurationFunction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxDurationFunctionTest {

    private static final char ZERO = 0;

    @Test
    void should_ReturnZeroForEmptyList() {
        MaxDurationFunction maxDurationFunction = new MaxDurationFunction();
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        SleepAnalysisResult test = maxDurationFunction.apply(sleepingSessions);

        assertEquals(ZERO, test.getValue());
    }

    @Test
    void should_ReturnMaxDurationForList_WithTwoSessions() {
        LocalDateTime start1 = LocalDateTime.of(2025, 10, 1, 23, 0);
        LocalDateTime end1 = LocalDateTime.of(2025, 10, 2, 7, 0);
        SleepingSession session1 = new SleepingSession(start1, end1, SleepQuality.GOOD);

        LocalDateTime start2 = LocalDateTime.of(2025, 10, 1, 22, 0);
        LocalDateTime end2 = LocalDateTime.of(2025, 10, 2, 7, 0);
        SleepingSession session2 = new SleepingSession(start2, end2, SleepQuality.GOOD);


        MaxDurationFunction maxDurationFunction = new MaxDurationFunction();
        List<SleepingSession> sleepingSessions = List.of(session1, session2);
        SleepAnalysisResult test = maxDurationFunction.apply(sleepingSessions);

        long expectedMax = Math.max(session1.getDurationMinutes(), session2.getDurationMinutes());

        assertEquals(expectedMax, test.getValue());
    }
}
