package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class MinDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final String MIN_DURATION_DESC = "Минимальная продолжительность сна (минуты)";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {


        long minDuration = sessions.stream().mapToLong(SleepingSession::getDurationMinutes).min().orElse(0L);

        return new SleepAnalysisResult(MIN_DURATION_DESC, minDuration);

    }
}
