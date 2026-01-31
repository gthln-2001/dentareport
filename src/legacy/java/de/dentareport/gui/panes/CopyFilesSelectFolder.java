package de.dentareport.gui.panes;

import de.dentareport.gui.Gui;
import de.dentareport.gui.ProgressUpdate;
import de.dentareport.gui.elements.Element;
import de.dentareport.gui.tasks.CopyTask;
import de.dentareport.imports.dampsoft.Dampsoft;
import de.dentareport.imports.dampsoft.dampsoft_files.DampsoftFile;
import de.dentareport.utils.Keys;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// TODO: TEST?
public class CopyFilesSelectFolder extends ContentPane {

    private Gui gui;
    private final Dampsoft dampsoft;
    private Label labelSelectedDirectory;
    private Button buttonStartCopy;
    private File selectedDirectory;
    private Button buttonGoToImport;

    public CopyFilesSelectFolder(Gui gui, Map<String, String> options) {
        this.gui = gui;
        this.dampsoft = new Dampsoft();
    }

    @Override
    public void finished() {
        buttonStartCopy.setDisable(false);
        buttonGoToImport.setDisable(false);
//        gui.enableMenuButtons();
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

    private VBox center() {
        labelSelectedDirectory = Element.label()
                .text(Keys.GUI_TEXT_NO_DIRECTORY_SELECTED)
                .styleClass("label-medium")
                .create();
        Button buttonSelectDirectory = buttonSelectDirectory();
        buttonStartCopy = buttonStartCopy();
        buttonGoToImport = buttonGoToImport();

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(
                buttonSelectDirectory,
                labelSelectedDirectory,
                buttonStartCopy,
                buttonGoToImport
        );
        return box;
    }

    private Button buttonQuit() {
        return Element.button()
                .text(Keys.GUI_TEXT_QUIT)
                .action(e -> gui.closeProgram())
                .create();
    }

    private Button buttonBack() {
        return Element.button()
                .text(Keys.GUI_TEXT_BACK)
                .action(e -> gui.setContentPane(Keys.GUI_VIEW_COPY_FILES))
                .create();
    }

    private HBox heading() {
        HBox box = new HBox();
        box.setPadding(new Insets(20, 20, 20, 20));
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(
                Element.label()
                        .text(Keys.GUI_TEXT_COPY_FILES)
                        .styleClass("label-big")
                        .create());
        return box;
    }

    private Button buttonSelectDirectory() {
        return Element.button()
                .text(Keys.GUI_TEXT_SELECT_SOURCE_DIRECTORY)
                .action(e -> directoryChooser())
                .create();
    }

    private void directoryChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(Keys.GUI_TEXT_DENTAREPORT);
        selectedDirectory = directoryChooser.showDialog(gui.primaryStage());
        buttonGoToImport.setDisable(true);
        if (selectedDirectory == null) {
            labelSelectedDirectory.setText(Keys.GUI_TEXT_NO_DIRECTORY_SELECTED);
            buttonStartCopy.setDisable(true);
        } else {
            labelSelectedDirectory.setText(selectedDirectory.getAbsolutePath());
            buttonStartCopy.setDisable(false);
        }
    }

    private Button buttonStartCopy() {
        return Element.button()
                .text(Keys.GUI_TEXT_COPY_FILES_FROM_SOURCE_DIRECTORY)
                .action(e -> copyFiles())
                .disabled()
                .create();
    }

    private Button buttonGoToImport() {
        return Element.button()
                .text(Keys.GUI_TEXT_IMPORT_DATA)
                .action(e -> gui.setContentPane(Keys.GUI_VIEW_IMPORT))
                .disabled()
                .create();
    }

    private void copyFiles() {
        if (isValidSourceDirectory()) {
            ProgressUpdate.setGui(this);
            buttonStartCopy.setDisable(true);
            buttonGoToImport.setDisable(true);
//            gui.disableMenuButtons();
            CopyTask copyWorker = new CopyTask(filenames(), selectedDirectory);
            labelSelectedDirectory.textProperty().unbind();
            labelSelectedDirectory.textProperty().bind(copyWorker.messageProperty());
            Thread copyThread = new Thread(copyWorker);
            copyThread.start();
        }
    }

    private boolean isValidSourceDirectory() {
        for (String filename : filenames()) {
            File file = new File(String.format("%s%s%s", selectedDirectory, File.separator, filename));
            if (!file.exists() || !file.isFile()) {
                Element.infoBox()
                        .title(Keys.GUI_TEXT_DENTAREPORT)
                        .text(Keys.GUI_TEXT_INVALID_SOURCE_DIRECTORY)
                        .create();
                return false;
            }
        }
        return true;
    }

    private List<String> filenames() {
        return dampsoft.files().stream()
                .filter(DampsoftFile::isRealFile)
                .map(DampsoftFile::filename)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
