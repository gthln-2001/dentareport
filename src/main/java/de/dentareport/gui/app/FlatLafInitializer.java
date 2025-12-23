package de.dentareport.gui.app;

import com.formdev.flatlaf.FlatLightLaf;

/**
 * Thin adapter around FlatLaf.
 * Tested indirectly via application startup wiring.
 */
public class FlatLafInitializer implements LookAndFeelInitializer {

    @Override
    public void setup() {
        FlatLightLaf.setup();
    }
}
