package de.dentareport.gui.app;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

/**
 * Thin adapter around FlatLaf.
 * Tested indirectly via application startup wiring.
 */
public class FlatLafInitializer implements LookAndFeelInitializer {

    @Override
    public void setup() {
        UIManager.put("Button.background", new Color(59, 130, 246));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.hoverBackground", new Color(37, 99, 235));
        UIManager.put("Button.pressedBackground", new Color(29, 78, 216));

        FlatLightLaf.setup();
    }
}
