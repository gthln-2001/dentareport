package de.dentareport;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

// TODO: To Remove
public class Dentareport_TO_REMOVE {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> {
            MainWindowImpl window = new MainWindowImpl();
            window.show();
        });
    }
}
