package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession {
    private final LocalDateTime startDateANDTime;
    private final LocalDateTime endDateANDTime;
    private final SleepQuality quality;


    public LocalDateTime getStartDateANDTime() {
        return startDateANDTime;
    }

    public LocalDateTime getEndDateANDTime() {
        return endDateANDTime;
    }

    public SleepQuality getQuality() {
        return quality;
    }

    public SleepingSession(LocalDateTime startDateANDTime, LocalDateTime endDateANDTime, SleepQuality quality) {
        this.startDateANDTime = startDateANDTime;
        this.endDateANDTime = endDateANDTime;
        this.quality = quality;
    }

    public long getDurationMinutes() {
        return Duration.between(startDateANDTime, endDateANDTime).toMinutes();
    }

}
