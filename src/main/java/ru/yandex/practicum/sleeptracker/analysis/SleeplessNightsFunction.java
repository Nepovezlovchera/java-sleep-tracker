package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleeplessNightsFunction  implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final int HALF_DAY = 12;
    private static final char ONE_DAY = 1;

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Кол-во бессонных ночей ",
                    0);
        }

        Set<LocalDate> nightsWithSleep = sessions.stream()
                .map(session -> {
                    LocalDateTime start = session.getStartDateANDTime();
                    return start.getHour() < HALF_DAY
                            ? start.toLocalDate()
                            : start.toLocalDate().plusDays(ONE_DAY);
                })
                .collect(Collectors.toSet());

        LocalDateTime firstSessionStart = sessions.get(0).getStartDateANDTime();
        LocalDate firstNight;
        if (firstSessionStart.getHour() < HALF_DAY) {
            firstNight = firstSessionStart.toLocalDate();
        } else {
            firstNight = firstSessionStart.toLocalDate().plusDays(ONE_DAY);
        }

        LocalDateTime lastSessionEnd = sessions.get(sessions.size() - ONE_DAY).getEndDateANDTime();
        LocalDate lastNight = lastSessionEnd.toLocalDate();

        Period period = Period.between(firstNight, lastNight);
        long totalNights = period.getDays() + 1;
        long countNotNightSession = totalNights - nightsWithSleep.size();


        return new SleepAnalysisResult("Количество бессонных ночей", countNotNightSession);

    }
    private boolean isSleeplessNight(SleepingSession sessions, LocalDate date) {
        LocalDateTime nightStart = LocalDateTime.of(date, LocalTime.of(0, 0));
        LocalDateTime nightEnd = LocalDateTime.of(date, LocalTime.of(6, 0));

        boolean sleepNightSession = sessions.getStartDateANDTime().isBefore(nightEnd) &&
                sessions.getEndDateANDTime().isAfter(nightStart);

        return !sleepNightSession;
    }
}
