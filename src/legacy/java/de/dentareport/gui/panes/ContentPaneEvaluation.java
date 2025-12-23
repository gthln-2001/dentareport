package de.dentareport.gui.panes;

import com.google.common.collect.ImmutableMap;
import de.dentareport.Metadata;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.gui.Gui;
import de.dentareport.gui.ProgressUpdate;
import de.dentareport.gui.elements.Element;
import de.dentareport.gui.tasks.EvaluatorTask;
import de.dentareport.utils.Keys;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public abstract class ContentPaneEvaluation extends ContentPane {

    private ProgressBar progressBar;
    private Label progressBarLabel;
    private Button buttonGeneralInformation;
    private Button buttonProbabilities;
    private Button buttonStartEvaluation;
    private Button buttonCancelEvaluation;
    private EvaluatorTask evaluatorTask;
    private Thread thread;
    private VBox progressBox;

    protected abstract String guiText();

    protected abstract String contentPaneViewGeneral();

    protected abstract String contentPaneViewProbabilities();

    protected abstract String metadataKey();

    protected abstract String errorNoValidEvaluation();

    protected abstract Class<? extends Evaluation> evaluationClass();

    protected abstract Gui getGui();

    @Override
    public void finished() {
        buttonStatusNoEvaluationRunning();
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
        HBox box = new HBox();
        box.setPadding(new Insets(20, 20, 20, 20));
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(
                Element.label()
                       .text(guiText())
                       .styleClass("label-big")
                       .create());
        return box;
    }

    private VBox center() {
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        progressBox = progressBox();
        buttonStartEvaluation = buttonStartEvaluation();
        buttonCancelEvaluation = buttonCancelEvaluation();
        box.getChildren().addAll(
                buttonsAccessEvaluations(),
                buttonStartEvaluation,
                buttonCancelEvaluation,
                progressBox);
        return box;
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
                      .action(e -> getGui().setContentPane(Keys.GUI_VIEW_EVALUATION))
                      .create();
    }

    private Button buttonQuit() {
        return Element.button()
                      .text(Keys.GUI_TEXT_QUIT)
                      .id("button-quit")
                      .action(e -> getGui().closeProgram())
                      .create();
    }

    private HBox buttonsAccessEvaluations() {
        HBox box = new HBox(20);
        box.setPadding(new Insets(0, 0, 40, 0));
        box.setAlignment(Pos.CENTER);
        buttonGeneralInformation = buttonGeneralInformation();
        buttonProbabilities = buttonProbabilities();
        box.getChildren().addAll(
                buttonGeneralInformation,
                buttonProbabilities
        );
        return box;
    }

    private Button buttonGeneralInformation() {
        return Element.button()
                      .text(Keys.GUI_TEXT_GENERAL_INFORMATION)
                      .id("button-general-information")
                      .action(actionGeneralInformation())
                      .create();
    }

    private EventHandler<ActionEvent> actionGeneralInformation() {
        return e -> {
            if (validEvaluationExists()) {
                getGui().setContentPane(ImmutableMap.of("view",
                                                        contentPaneViewGeneral(),
                                                        "evaluationId",
                                                        evaluationId()));
            } else {
                showInfoBoxNoValidEvaluation();
            }
        };
    }

    private String evaluationId() {
        return evaluationClass().getSimpleName().replace("Evaluation", "");
    }

    private Button buttonProbabilities() {
        return Element.button()
                      .text(Keys.GUI_TEXT_PROBABILITIES)
                      .id("button-probabilities")
                      .action(actionProbabilities())
                      .create();
    }

    private EventHandler<ActionEvent> actionProbabilities() {
        return e -> {
            if (validEvaluationExists()) {
                getGui().setContentPane(ImmutableMap.of("view",
                                                        contentPaneViewProbabilities(),
                                                        "evaluationId",
                                                        evaluationId()));
            } else {
                showInfoBoxNoValidEvaluation();
            }
        };
    }

    private boolean validEvaluationExists() {
        return Metadata.has(metadataKey());
    }

    private void showInfoBoxNoValidEvaluation() {
        Element.infoBox()
               .title(Keys.GUI_TEXT_DENTAREPORT)
               .text(errorNoValidEvaluation())
               .create();
    }

    private Button buttonStartEvaluation() {
        return Element.button()
                      .text(Keys.GUI_TEXT_START_NEW_EVALUATION)
                      .id("button-start-evaluation")
                      .action(actionStartEvaluation())
                      .create();
    }

    private EventHandler<ActionEvent> actionStartEvaluation() {
        return e -> {
            ProgressUpdate.setGui(this);
            buttonStatusEvaluationRunning();
            evaluatorTask = new EvaluatorTask(metadataKey(), evaluationClass());
            bindTaskToProgressBox();
            thread = new Thread(evaluatorTask);
            thread.start();
        };
    }

    private void buttonStatusEvaluationRunning() {
        progressBox.setVisible(true);
        buttonGeneralInformation.setDisable(true);
        buttonProbabilities.setDisable(true);
        buttonStartEvaluation.setDisable(true);
        buttonCancelEvaluation.setDisable(false);
        disableMenuButtons();
    }

    private void bindTaskToProgressBox() {
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(evaluatorTask.progressProperty());
        progressBarLabel.textProperty().unbind();
        progressBarLabel.textProperty().bind(evaluatorTask.messageProperty());
    }

    private Button buttonCancelEvaluation() {
        return Element.button()
                      .text(Keys.GUI_TEXT_CANCEL_EVALUATION)
                      .id("button-cancel-evaluation")
                      .action(actionCancelEvaluation())
                      .disabled()
                      .create();
    }

    private EventHandler<ActionEvent> actionCancelEvaluation() {
        return e -> {
            if (Element.confirmBox()
                       .title(Keys.GUI_WINDOW_TITLE_CONFIRM_CANCEL_EVALUATION)
                       .text(Keys.GUI_TEXT_CONFIRM_CANCEL_EVALUATION)
                       .create()) {
                progressBox.setVisible(false);
                buttonStatusNoEvaluationRunning();
                enableMenuButtons();
                thread.interrupt();
                unbindProgressBox();
            }
        };
    }

    private void buttonStatusNoEvaluationRunning() {
        buttonGeneralInformation.setDisable(false);
        buttonProbabilities.setDisable(false);
        buttonStartEvaluation.setDisable(false);
        buttonCancelEvaluation.setDisable(true);
        enableMenuButtons();
    }

    private void unbindProgressBox() {
        progressBar.progressProperty().unbind();
        progressBarLabel.textProperty().unbind();
        progressBar.setProgress(0);
    }
}
