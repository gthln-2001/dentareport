package de.dentareport.gui.elements;

import de.dentareport.utils.Keys;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

// TODO: TEST?
public class InfoBoxElement {

    private String title;
    private String text;
    private Stage window;

    public void create() {
        prepareInfoBox().showAndWait();
    }

    public Stage prepareInfoBox() {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setScene(scene());
        return window;
    }

    public InfoBoxElement title(String value) {
        title = value;
        return this;
    }

    public InfoBoxElement text(String value) {
        text = value;
        return this;
    }

    private Scene scene() {
        Scene scene = new Scene(vBox());
        scene.getStylesheets().add("styles.css");
        return scene;
    }

    private VBox vBox() {
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().addAll(
                Element.label()
                        .text(text)
                        .styleClass("label-normal")
                        .alignment(TextAlignment.CENTER)
                        .create(),
                Element.button()
                        .text(Keys.GUI_TEXT_OK)
                        .action(e -> window.close())
                        .width(50)
                        .create()
        );
        return vBox;
    }
}
