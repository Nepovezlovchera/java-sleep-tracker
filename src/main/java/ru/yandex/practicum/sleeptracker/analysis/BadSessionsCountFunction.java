package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class BadSessionsCountFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final String BAD_SESSIONS = "Кол-во BAD сессий";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long countBadQuality = sessions.stream().filter(session -> session.getQuality() == SleepQuality.BAD).count();

        return new SleepAnalysisResult(BAD_SESSIONS, countBadQuality);

    }
}
