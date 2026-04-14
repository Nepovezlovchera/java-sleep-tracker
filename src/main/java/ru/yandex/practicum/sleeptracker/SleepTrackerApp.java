package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.analysis.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;



public class SleepTrackerApp {

    private static List<Function<List<SleepingSession>, SleepAnalysisResult>> analysisFunctions =
            new ArrayList<>();
    static {
        analysisFunctions.add(new TotalSessionsFunction());
        analysisFunctions.add(new MinDurationFunction());
        analysisFunctions.add(new MaxDurationFunction());
        analysisFunctions.add(new AvgDurationFunction());
        analysisFunctions.add(new BadSessionsCountFunction());
        analysisFunctions.add(new SleeplessNightsFunction());
        analysisFunctions.add(new ChronotypeFunction());
    }

    public static void main(String[] args) {
        if(args.length == 0) {
            System.err.println("Укажите путь к файлу!");
            return;
        }
        String filePath = args[0];
        WorkWithFile workWithFile = new WorkWithFile(filePath);
        try {

           List<SleepingSession> sleepingSessions =  workWithFile.loadSessions();
           for (Function<List<SleepingSession>, SleepAnalysisResult> function : analysisFunctions ){
               SleepAnalysisResult result = function.apply(sleepingSessions);
               System.out.println(result);
           }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}