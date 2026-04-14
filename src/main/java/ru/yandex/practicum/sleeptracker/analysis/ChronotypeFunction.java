package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.Chronotype;
import ru.yandex.practicum.sleeptracker.ChronotypeName;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final LocalTime OWL_SLEEP_START = LocalTime.of(23, 0);
    private static final LocalTime OWL_SLEEP_END = LocalTime.of(9, 0);
    private static final LocalTime LARK_SLEEP_START = LocalTime.of(22, 0);
    private static final LocalTime LARK_SLEEP_END = LocalTime.of(7, 0);

    private static final int HALF_DAY = 12;
    private static final char ONE_DAY = 1;

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Хронотип пользователя ",
                    new ChronotypeName(Chronotype.PIGEON));
        }
        Map<LocalDate, List<SleepingSession>> nightsWithSleep = sessions.stream()
                .collect(Collectors.groupingBy(session -> {
                    LocalDateTime start = session.getStartDateANDTime();
                    return start.getHour() < HALF_DAY
                            ? start.toLocalDate()
                            : start.toLocalDate().plusDays(ONE_DAY);
                }));

        Map<Chronotype, Integer> countsChronotype = new HashMap<>();
        countsChronotype.put(Chronotype.OWL, 0);
        countsChronotype.put(Chronotype.LARK, 0);
        countsChronotype.put(Chronotype.PIGEON, 0);

        Map<LocalDate, Chronotype> nightTypes = nightsWithSleep.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> chronotypeForNight(entry.getValue())
                ));

        Map<Chronotype, Long> countChronotype = nightTypes.values().stream()
                .collect(Collectors.groupingBy(
                        chronotype -> chronotype,Collectors.counting()));

        return new SleepAnalysisResult("Твой хронатип", new ChronotypeName(
                getDominantChronotype(countChronotype)));
    }

    public Chronotype chronotypeForNight(List<SleepingSession> sessions) {
        LocalTime starSleep = sessions.stream()
                .map(session -> session.getStartDateANDTime().toLocalTime())
                .min(LocalTime::compareTo)
                .orElse(LocalTime.MIDNIGHT);

        LocalTime endSleep = sessions.stream()
                .map(session -> session.getEndDateANDTime().toLocalTime())
                .max(LocalTime::compareTo)
                .orElse(LocalTime.MIDNIGHT);

        if (starSleep.isAfter(OWL_SLEEP_START) && endSleep.isAfter(OWL_SLEEP_END)) {
            return Chronotype.OWL;
        } if (starSleep.isBefore(LARK_SLEEP_START) && endSleep.isBefore(LARK_SLEEP_END)) {
            return Chronotype.LARK;
        } return Chronotype.PIGEON;
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
