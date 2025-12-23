package de.dentareport;

import com.formdev.flatlaf.FlatLightLaf;
import de.dentareport.gui.MainWindow;
import de.dentareport.gui.ViewId;
import de.dentareport.gui.WindowTitleService;
import de.dentareport.utils.PreStarter;

import javax.swing.*;

public class Dentareport {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dentareport().run());
    }

    void run() {
        new PreStarter().runPreStartTasks();
        FlatLightLaf.setup();
        MainWindow mainWindow = new MainWindow();
        mainWindow.setWindowTitle(new WindowTitleService(new License()).title());
        mainWindow.showView(ViewId.START);
        mainWindow.show();
    }
}
