package de.dentareport.gui.panes;

import de.dentareport.gui.Gui;
import de.dentareport.gui.elements.Element;
import de.dentareport.imports.dampsoft.Dampsoft;
import de.dentareport.utils.Keys;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Map;

// TODO: TEST?
public class CopyFiles extends ContentPane {

    private Gui gui;
    private final Dampsoft dampsoft;

    public CopyFiles(Gui gui,
                     Map<String, String> options) {
        this.gui = gui;
        this.dampsoft = new Dampsoft();
    }

    @Override
    protected Pane content() {
        BorderPane pane = new BorderPane();
        pane.setTop(heading());
        pane.setCenter(center());
        return pane;
    }

    @Override
    protected Pane menu() {
        return Element.menu()
                .left(
                        Element.button()
                                .text(Keys.GUI_TEXT_BACK)
                                .action(e -> gui.setContentPane(Keys.GUI_VIEW_START))
                                .create())
                .right(
                        Element.button()
                                .text(Keys.GUI_TEXT_QUIT)
                                .action(e -> gui.closeProgram())
                                .create())
                .create();
    }

    private VBox center() {
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(
                Element.button()
                        .text(Keys.GUI_TEXT_COPY_FILES_AUTOMATIC)
                        .action(e -> Element.notAvailableInDemoVersion())
                        .styleClass("button-function-not-available")
                        .create(),
                Element.button()
                        .text(Keys.GUI_TEXT_COPY_FILES_SELECT_FOLDER)
                        .action(e -> gui.setContentPane(Keys.GUI_VIEW_COPY_FILES_SELECT_FOLDER))
                        .create(),
                Element.button()
                        .text(Keys.GUI_TEXT_COPY_FILES_MANUALLY)
                        .action(e -> {
                            infoBox();
                            gui.setContentPane(Keys.GUI_VIEW_IMPORT);
                        })
                        .create()
        );
        return box;
    }

    private void infoBox() {
        Element.infoBox()
                .text(String.format("%s\n%s",
                        Keys.GUI_TEXT_COPY_FILES_MANUALLY_FOR_IMPORT,
                        String.join("\n", dampsoft.requiredFiles())))
                .create();
    }

    private VBox heading() {
        VBox box = new VBox(20);
        box.setPadding(new Insets(20, 20, 20, 20));
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(
                Element.label()
                        .text(Keys.GUI_TEXT_COPY_FILES)
                        .styleClass("label-big")
                        .create(),
                Element.label()
                        .text(Keys.GUI_TEXT_COPY_FILES_EXPLANATION)
                        .styleClass("label-normal")
                        .create()
        );
        return box;
    }
}
