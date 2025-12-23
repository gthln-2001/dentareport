package de.dentareport;

import com.formdev.flatlaf.FlatLightLaf;
import de.dentareport.gui.app.MainWindow;
import de.dentareport.gui.navigation.ViewId;
import de.dentareport.gui.services.WindowTitleService;
import de.dentareport.utils.PreStarter;

import javax.swing.*;

// TODO: Test
public class Dentareport {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dentareport().run());
    }

    private void run() {
        new PreStarter().runPreStartTasks();
        FlatLightLaf.setup();
        MainWindow mainWindow = new MainWindow();
        mainWindow.setWindowTitle(new WindowTitleService(new License()).title());
        mainWindow.showView(ViewId.START);
        mainWindow.show();
    }
}
