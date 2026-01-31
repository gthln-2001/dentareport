package de.dentareport.gui.panes;

import de.dentareport.gui.Gui;
import de.dentareport.gui.ProgressUpdate;
import de.dentareport.gui.elements.Element;
import de.dentareport.gui.tasks.ImporterTask;
import de.dentareport.imports.dampsoft.Dampsoft;
import de.dentareport.utils.Keys;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.List;
import java.util.Map;

// TODO: TEST?
public class Import extends ContentPane {

    private final Gui gui;
    private final Dampsoft dampsoft;
    private ProgressBar progressBar;
    private Label progressBarLabel;
    private Button buttonStartImport;
    private Button buttonCancelImport;
    private Button buttonEvaluation;
    private Thread thread;
    private VBox progressBox;
    private ImporterTask importerTask;

    public Import(Gui gui, Map<String, String> options) {
        this.gui = gui;
        this.dampsoft = new Dampsoft();
    }

    @Override
    public void finished() {
        buttonCancelImport.setDisable(true);
        buttonEvaluation.setVisible(true);
        enableMenuButtons();
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
                .left(buttonBack())
                .right(buttonQuit())
                .create();
    }

    private HBox heading() {
        HBox heading = new HBox();
        heading.setPadding(new Insets(20, 20, 20, 20));
        heading.setAlignment(Pos.CENTER);
        heading.getChildren().addAll(
                Element.label()
                        .text(Keys.GUI_TEXT_IMPORT_DATA)
                        .styleClass("label-big")
                        .create());
        return heading;
    }

    private VBox center() {
        progressBox = progressBox();
        buttonStartImport = buttonStartImport();
        buttonCancelImport = buttonCancelImport();
        buttonEvaluation = buttonEvaluation();
        buttonEvaluation.setVisible(false);
        VBox center = new VBox(30);
        center.setAlignment(Pos.CENTER);
        center.getChildren().addAll(
                buttonStartImport,
                buttonCancelImport,
                progressBox,
                buttonEvaluation
        );
        return center;
    }

    private VBox progressBox() {
        progressBar = new ProgressBar(0);
        progressBarLabel = Element.label()
                .text(Keys.GUI_TEXT_INITIALIZE_PROGRESSBAR_LABEL)
                .styleClass("label-medium")
                .alignment(TextAlignment.CENTER)
                .create();
        VBox box = new VBox(30);
        box.setAlignment(Pos.CENTER);
        box.setMinHeight(150);
        box.setVisible(false);
        box.getChildren().addAll(progressBar, progressBarLabel);
        return box;
    }

    private Button buttonBack() {
        return Element.button()
                .text(Keys.GUI_TEXT_BACK)
                .id("button-back")
                .action(e -> gui.setContentPane(Keys.GUI_VIEW_COPY_FILES))
                .create();
    }

    private Button buttonQuit() {
        return Element.button()
                .text(Keys.GUI_TEXT_QUIT)
                .id("button-quit")
                .action(e -> gui.closeProgram())
                .create();
    }

    private Button buttonStartImport() {
        return Element.button()
                .text(Keys.GUI_TEXT_START_DATA_IMPORT)
                .id("button-start-import")
                .action(e -> actionStartImport())
                .create();
    }

    private void actionStartImport() {
        if (checkRequiredFiles()) {
            ProgressUpdate.setGui(this);
            buttonStatusImportRunning();
            importerTask = new ImporterTask();
            bindTaskToProgressBox();
            thread = new Thread(importerTask);
            thread.start();
        }
    }

    private void buttonStatusImportRunning() {
        progressBox.setVisible(true);
        buttonStartImport.setDisable(true);
        buttonCancelImport.setDisable(false);
        buttonEvaluation.setVisible(false);
        disableMenuButtons();
    }

    private void bindTaskToProgressBox() {
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(importerTask.progressProperty());
        progressBarLabel.textProperty().unbind();
        progressBarLabel.textProperty().bind(importerTask.messageProperty());
    }

    private boolean checkRequiredFiles() {
        List<String> missingFiles = dampsoft.missingFiles();
        if (missingFiles.isEmpty()) {
            return true;
        }
        Element.infoBox()
                .title(Keys.GUI_TEXT_DENTAREPORT)
                .text(String.format("%s%s",
                        Keys.GUI_TEXT_MISSING_FILES_FOR_IMPORT,
                        String.join("\n", missingFiles)))
                .create();
        return false;
    }

    private Button buttonCancelImport() {
        return Element.button()
                .text(Keys.GUI_TEXT_CANCEL_DATA_IMPORT)
                .id("button-cancel-import")
                .action(e -> actionCancelImport())
                .disabled()
                .create();
    }

    private Button buttonEvaluation() {
        return Element.button()
                .text(Keys.GUI_TEXT_EVALUATIONS)
                .id("button-evaluation")
                .action(e -> gui.setContentPane(Keys.GUI_VIEW_EVALUATION))
                .create();
    }

    private void actionCancelImport() {
        if (Element.confirmBox()
                .title(Keys.GUI_WINDOW_TITLE_CONFIRM_CANCEL_IMPORT)
                .text(Keys.GUI_TEXT_CONFIRM_CANCEL_IMPORT)
                .create()) {
            progressBox.setVisible(false);
            buttonStatusNoImportRunnning();
            thread.interrupt();
            unbindProgressBox();
        }
    }

    private void unbindProgressBox() {
        progressBar.progressProperty().unbind();
        progressBarLabel.textProperty().unbind();
        progressBar.setProgress(0);
    }

    private void buttonStatusNoImportRunnning() {
        buttonStartImport.setDisable(false);
        buttonCancelImport.setDisable(true);
        enableMenuButtons();
    }
}
