package de.dentareport;

import javax.swing.*;

public class MainWindowImpl implements MainWindow {

    private final JFrame frame;
    private final JLabel label;

    public MainWindowImpl() {
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

    @Override
    public String getWindowTitle() {
        return frame.getTitle();
    }

    @Override
    public String getMessageText() {
        return label.getText();
    }

    @Override
    public boolean isVisible() {
        return frame.isVisible();
    }

    @Override
    public boolean containsMessage() {
        return label.getParent() == frame.getContentPane();
    }

    void dispose() {
        frame.dispose();
    }
}
