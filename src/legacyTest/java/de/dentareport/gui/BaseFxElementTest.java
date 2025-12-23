package de.dentareport.gui;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class BaseFxElementTest extends BaseFxTest {

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(pane(), 1024, 768);
        scene.getStylesheets().add("styles.css");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    protected Pane pane() {
        return new Pane();
    }
}
