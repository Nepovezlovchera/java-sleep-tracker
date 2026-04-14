package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class TotalSessionsFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long sessionCount  = sessions.size();
        SleepAnalysisResult sleepAnalysisResult = new SleepAnalysisResult("Кол-во сессий сна"
                ,sessionCount);
        return sleepAnalysisResult;
    }
}
