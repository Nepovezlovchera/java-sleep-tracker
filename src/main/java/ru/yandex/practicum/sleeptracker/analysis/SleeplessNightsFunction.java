package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleeplessNightsFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final LocalTime NOON = LocalTime.of(12, 0, 0);
    private static final int ONE_DAY = 1;
    private static final String COUNT_BAD_NIGHT_SESSIONS = "Количество бессонных ночей";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(COUNT_BAD_NIGHT_SESSIONS, 0);
        }

        Set<LocalDate> nightsWithSleep = sessions.stream()
                .map(this::getNightDate)
                .collect(Collectors.toSet());

        LocalDate firstNight = getFirstNight(sessions);
        LocalDate lastNight = getLastNight(sessions);

        long totalNights = firstNight.datesUntil(lastNight.plusDays(ONE_DAY)).count();

        long sleeplessNights = totalNights - nightsWithSleep.size();

        return new SleepAnalysisResult(COUNT_BAD_NIGHT_SESSIONS, sleeplessNights);
    }

    private LocalDate getNightDate(SleepingSession session) {
        LocalDateTime start = session.getStartDateANDTime();
        return start.toLocalTime().isBefore(NOON)
                ? start.toLocalDate()
                : start.toLocalDate().plusDays(ONE_DAY);
    }

    private LocalDate getFirstNight(List<SleepingSession> sessions) {
        LocalDateTime firstStart = sessions.get(0).getStartDateANDTime();
        return firstStart.toLocalTime().isBefore(NOON)
                ? firstStart.toLocalDate()
                : firstStart.toLocalDate().plusDays(ONE_DAY);
    }

    private LocalDate getLastNight(List<SleepingSession> sessions) {
        return sessions.get(sessions.size() - ONE_DAY).getEndDateANDTime().toLocalDate();
    }
}