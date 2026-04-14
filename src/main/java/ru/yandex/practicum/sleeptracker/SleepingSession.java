package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession {
    private LocalDateTime startDateANDTime;
    private LocalDateTime endDateANDTime;
    private SleepQuality quality;


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
