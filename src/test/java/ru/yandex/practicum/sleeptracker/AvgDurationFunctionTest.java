package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analysis.AvgDurationFunction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AvgDurationFunctionTest {
    private static final char ZERO = 0;

    @Test
    void should_ReturnZeroForEmptyList() {
        AvgDurationFunction avgDurationFunction = new AvgDurationFunction();
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        SleepAnalysisResult test = avgDurationFunction.apply(sleepingSessions);

        assertEquals(ZERO, test.getValue());
    }

    @Test
    void should_ReturnAvgDurationForList_WithTwoSessions() {
        LocalDateTime start1 = LocalDateTime.of(2025, 10, 1, 23, 0);
        LocalDateTime end1 = LocalDateTime.of(2025, 10, 2, 7, 0);
        SleepingSession session1 = new SleepingSession(start1, end1, SleepQuality.GOOD);

        LocalDateTime start2 = LocalDateTime.of(2025, 10, 1, 22, 0);
        LocalDateTime end2 = LocalDateTime.of(2025, 10, 2, 7, 0);
        SleepingSession session2 = new SleepingSession(start2, end2, SleepQuality.GOOD);


        AvgDurationFunction avgDurationFunction = new AvgDurationFunction();
        List<SleepingSession> sleepingSessions = List.of(session1, session2);
        SleepAnalysisResult test = avgDurationFunction.apply(sleepingSessions);

        long sum = sleepingSessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .sum();

        long expectedAvg = sum / sleepingSessions.size();

        assertEquals(expectedAvg, test.getValue());
    }
}
