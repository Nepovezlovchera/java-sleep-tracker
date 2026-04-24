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
    private static final String SLEEPLESS_NIGHTS_DESC = "Количество бессонных ночей";

    private static final LocalTime NIGHT_START = LocalTime.of(0, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(SLEEPLESS_NIGHTS_DESC, 0L);
        }

        Set<LocalDate> nightsWithSleep = sessions.stream()
                .filter(this::isNightSession)
                .map(this::getNightDate)
                .collect(Collectors.toSet());

        LocalDate firstNight = getFirstNight(sessions);
        LocalDate lastNight = getLastNight(sessions);

        long totalNights = firstNight.datesUntil(lastNight.plusDays(ONE_DAY)).count();

        long sleeplessNights = totalNights - nightsWithSleep.size();

        return new SleepAnalysisResult(SLEEPLESS_NIGHTS_DESC, sleeplessNights);
    }

    private boolean isNightSession(SleepingSession session) {
        LocalDateTime start = session.getStartDateANDTime();
        LocalDateTime end = session.getEndDateANDTime();

        LocalDateTime nightStart = LocalDateTime.of(start.toLocalDate(), NIGHT_START);
        LocalDateTime nightEnd = LocalDateTime.of(start.toLocalDate(), NIGHT_END);

        return start.isBefore(nightEnd) && end.isAfter(nightStart);
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