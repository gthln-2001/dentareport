package de.dentareport;

import de.dentareport.gui.FxApplicationGui;
import de.dentareport.utils.PreStarter;
import javafx.application.Application;
import javafx.stage.Stage;

public class Dentareport extends Application {

    // TODO: Find a way to handle exceptions better. For now, e.g. InvalidargumentExceptions seem to disappear...

    private final PreStarter preStarter;

    public Dentareport() {
        this.preStarter = new PreStarter();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        preStarter.runPreStartTasks();
        FxApplicationGui gui = new FxApplicationGui(primaryStage);
        gui.start();
    }
}