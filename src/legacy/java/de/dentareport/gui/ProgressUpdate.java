package de.dentareport.gui;

import de.dentareport.exceptions.DentereportInterruptedException;
import de.dentareport.gui.panes.ContentPane;
import de.dentareport.gui.tasks.BaseTask;

import java.util.Objects;

public class ProgressUpdate {

    private static BaseTask task;
    private static ContentPane contentPane;
    private static Integer total;
    private static Integer progress;
    private static String text;
    private static String textPrefix;

    public static void setTask(BaseTask task) {
        ProgressUpdate.task = task;
        progress = 1;
        total = 1;
        text = "";
        textPrefix = "";
    }

    public static void init(Integer total) {
        init(total, "");
    }

    public static void init(Integer total, String text) {
        progress = 1;
        ProgressUpdate.total = total;
        ProgressUpdate.text = text;
    }

    public static void setTextPrefix(String textPrefix) {
        ProgressUpdate.textPrefix = textPrefix;
    }

    public static void updateMessage(String message) {
        task.updateMessage(message);
    }

    public static void tick() {
        if (Thread.currentThread().isInterrupted()) {
            throw new DentereportInterruptedException();
        }
        task.updateProgress(progress, total);
        task.updateMessage(message());
        progress++;
    }

    public static void setGui(ContentPane value) {
        contentPane = value;
    }

    public static void finished() {
        contentPane.finished();
    }

    private static String message() {
        return String.format("%s%s\n%.2f%%",
                textPrefix(),
                text,
                currentProgress());
    }

    private static double currentProgress() {
        return (double) progress / (double) total * 100;
    }

    private static String textPrefix() {
        return Objects.equals(textPrefix, "") ? "" : String.format("%s\n", textPrefix);
    }
}
