package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analysis.ChronotypeFunction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChronotypeFunctionTest {

    private static final char ZERO = 0;
    private static final char ONE = 1;


    @Test
    void should_ReturnPigeonForEmptyList() {
        ChronotypeFunction chronotypeFunction = new ChronotypeFunction();
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        SleepAnalysisResult test = chronotypeFunction.apply(sleepingSessions);
        ChronotypeName chronotypeName = test.getChronotypeName();

        assertEquals(Chronotype.PIGEON, chronotypeName.getName());
    }

    @Test
    void should_DetectOwl() {
        LocalDateTime start = LocalDateTime.of(2025, 10, 1, 23, 30);
        LocalDateTime end = LocalDateTime.of(2025, 10, 2, 10, 0);
        SleepingSession session = new SleepingSession(start, end, SleepQuality.GOOD);

        ChronotypeFunction chronotypeFunction = new ChronotypeFunction();
        List<SleepingSession> sessions = List.of(session);
        SleepAnalysisResult result = chronotypeFunction.apply(sessions);

        ChronotypeName chronotypeName = result.getChronotypeName();
        assertEquals(Chronotype.OWL, chronotypeName.getName());
    }

    @Test
    void should_DetectLark() {
        LocalDateTime start = LocalDateTime.of(2025, 10, 1, 21, 0);
        LocalDateTime end = LocalDateTime.of(2025, 10, 2, 6, 30);
        SleepingSession session = new SleepingSession(start, end, SleepQuality.GOOD);

        ChronotypeFunction chronotypeFunction = new ChronotypeFunction();
        List<SleepingSession> sessions = List.of(session);
        SleepAnalysisResult result = chronotypeFunction.apply(sessions);

        ChronotypeName chronotypeName = result.getChronotypeName();
        assertEquals(Chronotype.LARK, chronotypeName.getName());
    }

    @Test
    void should_DetectPigeon() {
        LocalDateTime start = LocalDateTime.of(2025, 10, 1, 22, 30);
        LocalDateTime end = LocalDateTime.of(2025, 10, 2, 8, 0);
        SleepingSession session = new SleepingSession(start, end, SleepQuality.GOOD);

        ChronotypeFunction chronotypeFunction = new ChronotypeFunction();
        List<SleepingSession> sessions = List.of(session);
        SleepAnalysisResult result = chronotypeFunction.apply(sessions);

        ChronotypeName chronotypeName = result.getChronotypeName();
        assertEquals(Chronotype.PIGEON, chronotypeName.getName());
    }
}
