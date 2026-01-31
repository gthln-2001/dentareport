package de.dentareport.utils;

// TODO: TEST?
public class Log {

    private static long startTime;

    public static void log(String message){
        System.out.println(message);
    }

    public static void error(String message){
        System.out.println("ERROR: " + message);
    }

    public static void logTimeStart() {
        startTime = System.currentTimeMillis();
    }

    public static void logTimeEnd() {
        log("Duration: " + String.valueOf((System.currentTimeMillis() - startTime) / 1000) + "s");
    }
}
