package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class MaxDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final String MAX_DURATION_DESC = "Максимальная продолжительность сна (минуты)";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(MAX_DURATION_DESC, 0);
        }

        long maxDuration = sessions.stream().mapToLong(SleepingSession::getDurationMinutes).max().orElse(0);

        return new SleepAnalysisResult(MAX_DURATION_DESC, maxDuration);

    }
}
