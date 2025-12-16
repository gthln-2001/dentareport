package de.dentareport;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Dentareport {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.show();
        });
    }
}
