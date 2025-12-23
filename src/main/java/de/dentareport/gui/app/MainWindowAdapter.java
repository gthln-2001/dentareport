package de.dentareport.gui.app;

import de.dentareport.gui.navigation.ViewFactory;

/**
 * Adapter between application core and Swing MainWindow.
 * Behavior tested indirectly via application startup wiring.
 */
public class MainWindowAdapter implements ApplicationWindow {

    private final MainWindow window;

    public MainWindowAdapter() {
        this.window = new MainWindow(new ViewFactory());
    }

    @Override
    public void setWindowTitle(String title) {
        window.setWindowTitle(title);
    }

    @Override
    public void show() {
        window.show();
    }
}
