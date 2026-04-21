package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analysis.SleeplessNightsFunction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleeplessNightsFunctionTest {

    private static final int ZERO = 0;
    private static final int ONE_DAY = 1;

    @Test
    void should_ReturnZeroForEmptyList() {
        SleeplessNightsFunction sleeplessNightsFunction = new SleeplessNightsFunction();
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        SleepAnalysisResult test = sleeplessNightsFunction.apply(sleepingSessions);

        assertEquals(ZERO, test.getValue());
    }

    @Test
    void should_ReturnOneNightFunctionForList_WithTwoSessions() {
        LocalDateTime start1 = LocalDateTime.of(2025, 10, 1, 22, 0);
        LocalDateTime end1 = LocalDateTime.of(2025, 10, 2, 6, 0);
        SleepingSession session1 = new SleepingSession(start1, end1, SleepQuality.GOOD);

        LocalDateTime start2 = LocalDateTime.of(2025, 10, 3, 23, 0);
        LocalDateTime end2 = LocalDateTime.of(2025, 10, 4, 7, 0);
        SleepingSession session2 = new SleepingSession(start2, end2, SleepQuality.GOOD);


        SleeplessNightsFunction sleeplessNightsFunction = new SleeplessNightsFunction();
        List<SleepingSession> sleepingSessions = List.of(session1, session2);
        SleepAnalysisResult test = sleeplessNightsFunction.apply(sleepingSessions);

        assertEquals(ONE_DAY, test.getValue());
    }
}
