package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.Chronotype;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final LocalTime OWL_START = LocalTime.of(23, 0);
    private static final LocalTime OWL_WAKE = LocalTime.of(9, 0);
    private static final LocalTime LARK_BED_END = LocalTime.of(22, 0);
    private static final LocalTime LARK_WAKE_END = LocalTime.of(7, 0);

    private static final LocalTime EVENING_START = LocalTime.of(20, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);
    private static final LocalTime MORNING_END = LocalTime.of(12, 0);

    private static final int HALF_DAY = 12;
    private static final int ONE_DAY = 1;
    private static final String CHRONOTYPE_USER = "Хронотип пользователя";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(CHRONOTYPE_USER, Chronotype.PIGEON);
        }

        Map<LocalDate, List<SleepingSession>> nightsWithSleep = sessions.stream()
                .filter(this::isNightSession)
                .collect(Collectors.groupingBy(session -> {
                    LocalDateTime start = session.getStartDateANDTime();
                    return start.getHour() < HALF_DAY
                            ? start.toLocalDate()
                            : start.toLocalDate().plusDays(ONE_DAY);
                }));

        if (nightsWithSleep.isEmpty()) {
            return new SleepAnalysisResult(CHRONOTYPE_USER, Chronotype.PIGEON);
        }

        Map<Chronotype, Long> countChronotype = nightsWithSleep.values().stream()
                .map(this::chronotypeForNight)  // ← передаем уже отфильтрованные сессии
                .collect(Collectors.groupingBy(chronotype -> chronotype, Collectors.counting()));

        return new SleepAnalysisResult(CHRONOTYPE_USER, getDominantChronotype(countChronotype));
    }

    private Chronotype chronotypeForNight(List<SleepingSession> nightSessions) {
        if (nightSessions.isEmpty()) {
            return Chronotype.PIGEON;
        }

        LocalTime startSleep = nightSessions.stream()
                .map(session -> session.getStartDateANDTime().toLocalTime())
                .min(LocalTime::compareTo)
                .orElse(LocalTime.MIDNIGHT);

        LocalTime endSleep = nightSessions.stream()
                .map(session -> session.getEndDateANDTime().toLocalTime())
                .max(LocalTime::compareTo)
                .orElse(LocalTime.MIDNIGHT);

        if (isOwl(startSleep, endSleep)) {
            return Chronotype.OWL;
        }
        if (isLark(startSleep, endSleep)) {
            return Chronotype.LARK;
        }
        return Chronotype.PIGEON;
    }

    private boolean isOwl(LocalTime start, LocalTime end) {
        if (start.isAfter(OWL_START) && end.isAfter(OWL_WAKE)) {
            return true;
        }
        if (start.isAfter(LocalTime.MIDNIGHT) && end.isAfter(OWL_WAKE)) {
            return true;
        }
        return false;
    }

    private boolean isLark(LocalTime start, LocalTime end) {
        return start.isBefore(LARK_BED_END) && end.isBefore(LARK_WAKE_END);
    }

    private boolean isNightSession(SleepingSession session) {
        LocalTime startTime = session.getStartDateANDTime().toLocalTime();
        LocalTime endTime = session.getEndDateANDTime().toLocalTime();

        if (startTime.isAfter(EVENING_START) && endTime.isBefore(MORNING_END)) {
            return true;
        }
        if (startTime.isBefore(NIGHT_END) && endTime.isBefore(NIGHT_END)) {
            return true;
        }
        if (startTime.isBefore(NIGHT_END) && endTime.isBefore(MORNING_END)) {
            return true;
        }
        return false;
    }

    private Chronotype getDominantChronotype(Map<Chronotype, Long> counts) {
        long maxCount = counts.values().stream()
                .max(Long::compareTo)
                .orElse(0L);

        List<Chronotype> dominant = counts.entrySet().stream()
                .filter(entry -> entry.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return dominant.size() == 1 ? dominant.get(0) : Chronotype.PIGEON;
    }
}