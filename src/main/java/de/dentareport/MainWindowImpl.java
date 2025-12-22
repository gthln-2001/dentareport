package de.dentareport;

import javax.swing.*;
import java.awt.*;

public class MainWindowImpl implements MainWindow {

    private final JFrame frame;
    private final JLabel label;
    private final JButton button;

    public MainWindowImpl() {
        frame = new JFrame("Hello FlatLaf");
        label = new JLabel("Hello World!", SwingConstants.CENTER);
        button = new JButton("Click me");
        MainWindowController controller = new MainWindowController(this);
        button.addActionListener(e -> controller.onButtonClicked());

        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
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
    public void setMessageText(String text) {
        label.setText(text);
    }

    @Override
    public boolean containsMessage() {
        return label.getParent() == frame.getContentPane();
    }

    @Override
    public void clickButton() {
        button.doClick();
    }

    void dispose() {
        frame.dispose();
    }
}
