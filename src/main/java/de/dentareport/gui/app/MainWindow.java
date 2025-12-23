package de.dentareport.gui.app;

import de.dentareport.gui.navigation.ViewFactory;
import de.dentareport.gui.navigation.ViewId;

import javax.swing.*;
import java.awt.*;

import static de.dentareport.utils.Keys.*;

// TODO: Test
public class MainWindow implements UiController {

    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    private final JFrame frame;
    private final JPanel content;
    private final ViewFactory viewFactory;

    public MainWindow() {
        frame = new JFrame();
        content = new JPanel(new BorderLayout());

        viewFactory = new ViewFactory(this);

        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                confirmExit();
            }
        });
    }

    public void show() {
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
                GUI_TEXT_CONFIRM_EXIT,
                GUI_TEXT_QUIT,
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
        JOptionPane.showMessageDialog(frame, message, GUI_TEXT_ERROR, JOptionPane.ERROR_MESSAGE);
    }
}
