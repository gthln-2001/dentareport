package de.dentareport;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;


public class Dentareport {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(Dentareport::createAndShowGui);
    }

    static JFrame createAndShowGui() {
        JFrame frame = new JFrame("Hello FlatLaf");

        JLabel label = new JLabel("Hello World!", SwingConstants.CENTER);

        frame.add(label);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // center on screen
        frame.setVisible(true);

        return frame;
    }
}
