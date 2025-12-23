package de.dentareport;

import de.dentareport.gui.MainWindow;
import de.dentareport.gui.ViewId;
import de.dentareport.gui.WindowTitleService;
import de.dentareport.utils.PreStarter;

public class Dentareport {

    public static void main(String[] args) {
        new Dentareport().run();
    }

    void run() {
        new PreStarter().runPreStartTasks();

        MainWindow mainWindow = new MainWindow();
        mainWindow.setWindowTitle(new WindowTitleService(new License()).title());
        mainWindow.showView(ViewId.START);
        mainWindow.show();
    }
}
