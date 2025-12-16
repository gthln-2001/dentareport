package de.dentareport;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainWindow {

    private final JFrame frame;
    private final JLabel label;

    public MainWindow() {
        frame = new JFrame("Hello FlatLaf");
        label = new JLabel("Hello World!", SwingConstants.CENTER);

        frame.add(label);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public void show() {
        frame.setVisible(true);
    }

    JFrame getFrame() {
        return frame;
    }

    JLabel getLabel() {
        return label;
    }
}
