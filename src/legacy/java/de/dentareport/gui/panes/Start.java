package de.dentareport.gui.panes;

import de.dentareport.Metadata;
import de.dentareport.gui.Gui;
import de.dentareport.gui.elements.Element;
import de.dentareport.utils.Keys;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.Map;

// TODO: TEST?
public class Start extends ContentPane {

    private Gui gui;

    public Start(Gui gui,
                 Map<String, String> options) {
        this.gui = gui;
    }

    @Override
    protected VBox content() {
        VBox box = new VBox(30);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(
                Element.label()
                        .text(Keys.GUI_TEXT_DENTAREPORT)
                        .styleClass("label-huge")
                        .create(),
                Element.label()
                        .text(Keys.GUI_TEXT_HEADING_START_PANE_1)
                        .styleClass("label-medium")
                        .alignment(TextAlignment.CENTER)
                        .create(),
                new ImageView(new Image("teaser.png")),
                Element.label()
                        .text(Keys.GUI_TEXT_HEADING_START_PANE_2)
                        .styleClass("label-medium")
                        .alignment(TextAlignment.CENTER)
                        .create(),
                buttons()
        );
        return box;
    }

    @Override
    protected BorderPane menu() {
        return Element.menu()
                .right(Element.button()
                        .text(Keys.GUI_TEXT_QUIT)
                        .action(e -> gui.closeProgram())
                        .create())
                .create();
    }

    private HBox buttons() {
        HBox box = new HBox(40);
        box.setPadding(new Insets(20, 20, 20, 20));
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(
                buttonImport(),
                buttonEvaluation()
        );
        return box;
    }

    private Button buttonImport() {
        return Element.button()
                .text(Keys.GUI_TEXT_IMPORT_DATA)
                .action(e -> gui.setContentPane(Keys.GUI_VIEW_COPY_FILES))
                .create();
    }

    private Button buttonEvaluation() {
        return Element.button()
                .text(Keys.GUI_TEXT_EVALUATIONS)
                .action(actionEvaluation())
                .create();
    }

    private EventHandler<ActionEvent> actionEvaluation() {
        return e -> {
            if (validImportExists()) {
                gui.setContentPane(Keys.GUI_VIEW_EVALUATION);
            } else {
                Element.infoBox()
                        .title(Keys.GUI_TEXT_DENTAREPORT)
                        .text(Keys.GUI_TEXT_ERROR_NO_VALID_IMPORT_FOUND)
                        .create();
            }
        };
    }

    private boolean validImportExists() {
        return Metadata.has(Keys.METADATA_KEY_VALID_IMPORT);
    }
}
