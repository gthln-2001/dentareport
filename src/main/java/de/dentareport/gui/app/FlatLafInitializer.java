package de.dentareport.gui.app;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

/**
 * Thin adapter around FlatLaf.
 * Tested indirectly via application startup wiring.
 */
// TODO: TEST?
public class FlatLafInitializer implements LookAndFeelInitializer {

    public static final Color BLUE_DARK = new Color(59, 92, 162);
    public static final Color BLUE_LIGHT = new Color(37, 99, 235);
    public static final Color BLUE_VERY_LIGHT = new Color(182, 202, 245);
    public static final Color BLUE_MEDIUM = new Color(29, 78, 216);
    public static final Color GREY_VERY_LIGHT = new Color(240, 240, 240);
    public static final Color WHITE = new Color(255, 255, 255);

    private static void button() {
        UIManager.put("Button.background", BLUE_DARK);
        UIManager.put("Button.foreground", WHITE);
        UIManager.put("Button.hoverBackground", BLUE_LIGHT);
        UIManager.put("Button.pressedBackground", BLUE_MEDIUM);
    }

    private static void optionPane() {
        UIManager.put("OptionPane.background", WHITE);
    }

    private static void panel() {
        UIManager.put("Panel.background", WHITE);
    }

    private static void progressBar() {
        UIManager.put("ProgressBar.foreground", BLUE_DARK);
        UIManager.put("ProgressBar.selectionForeground", Color.WHITE);
        UIManager.put("ProgressBar.selectionBackground", Color.WHITE);
    }

    private static void table() {
        UIManager.put("TableHeader.height", 32);
        UIManager.put("Table.rowHeight", 28);
        UIManager.put("TableHeader.background", BLUE_VERY_LIGHT);
        UIManager.put("Table.alternateRowColor", GREY_VERY_LIGHT);
        UIManager.put("Table.background", WHITE);
    }

    @Override
    public void setup() {
        button();
        optionPane();
        panel();
        progressBar();
        table();

        FlatLightLaf.setup();
    }
}
