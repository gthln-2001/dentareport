package de.dentareport;

import javax.swing.*;

public class Dentareport {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Dentareport::createAndShowGui);
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("Hello Swing");

        JLabel label = new JLabel("Hello World!", SwingConstants.CENTER);

        frame.add(label);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // center on screen
        frame.setVisible(true);
    }
}
