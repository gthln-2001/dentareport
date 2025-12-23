package de.dentareport.gui.elements;

import de.dentareport.utils.Keys;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBoxElement {

    private String title;
    private String text;
    private static final boolean[] answer = new boolean[1];
    private Stage window;

    public static Boolean answer() {
        return answer[0];
    }

    public Boolean create() {
        prepareConfirmBox().showAndWait();

        return answer[0];
    }

    public Stage prepareConfirmBox() {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setScene(scene());

        return window;
    }

    public ConfirmBoxElement title(String value) {
        title = value;
        return this;
    }

    public ConfirmBoxElement text(String value) {
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
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(
                Element.label().text(text).styleClass("label-normal").create(),
                buttonConfirm(),
                buttonDiscard()
        );
        return vBox;
    }

    private Button buttonDiscard() {
        return Element.button().text(Keys.GUI_TEXT_NO).width(50).action(e -> {
            answer[0] = false;
            window.close();
        }).create();
    }

    private Button buttonConfirm() {
        return Element.button().text(Keys.GUI_TEXT_YES).width(50).action(e -> {
            answer[0] = true;
            window.close();
        }).create();
    }
}
