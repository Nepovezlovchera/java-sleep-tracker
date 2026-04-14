package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult {
    private final String description;
    private final long value;
    private final ChronotypeName chronotypeName;

    public SleepAnalysisResult(String description, ChronotypeName chronotypeName) {
        this.description = description;
        this.value = -1;
        this.chronotypeName = chronotypeName;
    }

    public long getValue() {
        return value;
    }

    public SleepAnalysisResult(String description, long value) {
        this.description = description;
        this.value = value;
        this.chronotypeName = null;
    }

    @Override
    public String toString() {
        if (chronotypeName != null) {
            String chronotypeString;
            switch (chronotypeName.getName()) {
                case OWL:
                    chronotypeString = "Сова";
                    break;
                case LARK:
                    chronotypeString = "Жаворонок";
                    break;
                default:
                    chronotypeString = "Голубь";
                    break;
            }
            return description + ": " + chronotypeString;
        } else {
            // Это результат с числом
            return description + ": " + value;
        }
    }

    public ChronotypeName getChronotypeName() {
        return chronotypeName;
    }
}
