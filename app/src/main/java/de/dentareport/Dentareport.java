package de.dentareport;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Dentareport {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> {
            MainWindowImpl window = new MainWindowImpl();
            window.show();
        });
    }
}
