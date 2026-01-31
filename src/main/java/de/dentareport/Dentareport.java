package de.dentareport;

import de.dentareport.gui.app.ApplicationWindow;
import de.dentareport.gui.app.FlatLafInitializer;
import de.dentareport.gui.app.LookAndFeelInitializer;
import de.dentareport.gui.app.MainWindowAdapter;
import de.dentareport.gui.services.WindowTitleService;
import de.dentareport.utils.PreStarter;

import javax.swing.*;

// TODO: TEST?
public class Dentareport {

    private final PreStarter preStarter;
    private final LookAndFeelInitializer lookAndFeelInitializer;
    private final ApplicationWindow applicationWindow;
    private final WindowTitleService windowTitleService;

    public Dentareport(
            PreStarter preStarter,
            LookAndFeelInitializer lookAndFeelInitializer,
            ApplicationWindow applicationWindow,
            WindowTitleService windowTitleService
    ) {
        this.preStarter = preStarter;
        this.lookAndFeelInitializer = lookAndFeelInitializer;
        this.applicationWindow = applicationWindow;
        this.windowTitleService = windowTitleService;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dentareport(new PreStarter(),
                new FlatLafInitializer(),
                new MainWindowAdapter(),
                new WindowTitleService(new License())).run());
    }

    void run() {
        preStarter.runPreStartTasks();
        lookAndFeelInitializer.setup();
        applicationWindow.setWindowTitle(windowTitleService.title());
        applicationWindow.show();
    }
}
