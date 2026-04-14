package ru.yandex.practicum.sleeptracker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class WorkWithFile {
    private final String fileName;

    public WorkWithFile(String fileName) {
        this.fileName = fileName;
    }

    public List<SleepingSession> loadSessions() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {

            return reader.lines()
                    .filter(line -> line != null && !line.trim().isEmpty())
                    .map(line -> line.split(";"))
                    .filter(parts -> parts.length == 3)
                    .map(parts -> {
                        LocalDateTime start = LocalDateTime.parse(parts[0], formatter);
                        LocalDateTime end = LocalDateTime.parse(parts[1], formatter);
                        SleepQuality quality = SleepQuality.valueOf(parts[2].trim());
                        return new SleepingSession(start, end, quality);
                    })
                    .collect(Collectors.toList());
        }
    }
}


