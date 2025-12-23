package de.dentareport.gui;

import de.dentareport.License;

import javax.swing.*;
import java.awt.*;

public class MainWindow implements UiController {

    private final JFrame frame;
    private final JPanel content;
    private final SwingViewFactory viewFactory;
    private final WindowTitleService titleService;


    public MainWindow() {
        frame = new JFrame();
        content = new JPanel(new BorderLayout());

        titleService = new WindowTitleService(new License());
        viewFactory = new SwingViewFactory(this);

        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                confirmExit();
            }
        });
    }

    public void show() {
        setWindowTitle(titleService.title());
        showView(ViewId.START);
        frame.setVisible(true);
    }

    @Override
    public void showView(ViewId viewId) {
        content.removeAll();
        content.add(viewFactory.create(viewId), BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    @Override
    public void confirmExit() {
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Really exit?",
                "Confirm exit",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            closeApplication();
        }
    }

    @Override
    public void closeApplication() {
        frame.dispose();
        System.exit(0);
    }

    @Override
    public void setWindowTitle(String title) {
        frame.setTitle(title);
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
