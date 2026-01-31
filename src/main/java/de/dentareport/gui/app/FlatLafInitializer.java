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

    @Override
    public void setup() {
        UIManager.put("Panel.background", new Color(255, 255, 255));

        UIManager.put("Button.background", new Color(59, 92, 162));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.hoverBackground", new Color(37, 99, 235));
        UIManager.put("Button.pressedBackground", new Color(29, 78, 216));

        FlatLightLaf.setup();
    }
}
