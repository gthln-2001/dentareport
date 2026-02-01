package de.dentareport.gui.views.fillings;

import de.dentareport.Metadata;
import de.dentareport.utils.Keys;

import javax.swing.*;
import java.awt.*;

import static de.dentareport.gui.util.SwingUtils.*;
import static de.dentareport.utils.Keys.*;

// TODO: Test?

// TODO: TEST?
public class FillingsView extends JPanel {

    private final FillingsPresenter presenter;
    private JButton buttonGeneralInformationFillings;
    private JButton buttonProbabilitiesFillings;
    private JButton buttonStartNewEvaluationFillings;
    private JProgressBar progressBar;

    public FillingsView(FillingsPresenter presenter) {
        this.presenter = presenter;
        presenter.setView(this);

        setLayout(createBorderLayout());
        addTitle();
        addContent();
        addMenu();
    }

    private void addTitle() {
        add(title(GUI_TEXT_FILLINGS), BorderLayout.NORTH);
    }

    private void addContent() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(Box.createVerticalStrut(80));
        addButtons(contentPanel);
        addProgressBar(contentPanel);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void addProgressBar(JPanel contentPanel) {
        progressBar = new JProgressBar(0, 100);
        Dimension size = new Dimension(400, 28);
        progressBar.setPreferredSize(size);
        progressBar.setMaximumSize(size);
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressBar.setStringPainted(true);

        contentPanel.add(progressBar);
    }

    private void addButtons(JPanel contentPanel) {
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

        buttonGeneralInformationFillings = buttonLarge(GUI_TEXT_GENERAL_INFORMATION,
                "general_information_telescopic_crowns", e -> presenter.onGeneralInformationFillings());
        buttonGeneralInformationFillings.setAlignmentX(CENTER_ALIGNMENT);
        buttons.add(buttonGeneralInformationFillings);
        buttons.add(Box.createVerticalStrut(40));

        buttonProbabilitiesFillings = buttonLarge(GUI_TEXT_PROBABILITIES,
                "general_information_telescopic_crowns", e -> presenter.onProbabilitiesFillings());
        buttonProbabilitiesFillings.setAlignmentX(CENTER_ALIGNMENT);
        buttons.add(buttonProbabilitiesFillings);
        buttons.add(Box.createVerticalStrut(120));

        buttonStartNewEvaluationFillings = buttonLarge(GUI_TEXT_START_NEW_EVALUATION,
                "start_new_evaluation_telescopic_crowns", e -> presenter.onStartNewEvaluationFillings());
        buttonStartNewEvaluationFillings.setAlignmentX(CENTER_ALIGNMENT);
        buttons.add(buttonStartNewEvaluationFillings);

        // TODO: implement cancel evaluation (?)
//        JButton buttonCancelDataImport = buttonLargeMuted(GUI_TEXT_CANCEL_DATA_IMPORT, "cancel_data_import",
//                e -> presenter.onBack());
//        buttonCancelDataImport.setAlignmentX(CENTER_ALIGNMENT);
//        buttonPanel.add(buttonCancelDataImport);

        if (!validEvaluationExists()) {
            buttonGeneralInformationFillings.setEnabled(false);
            buttonProbabilitiesFillings.setEnabled(false);
        }

        buttons.setMaximumSize(buttons.getPreferredSize());
        contentPanel.add(buttons);
        contentPanel.add(Box.createVerticalStrut(40));
    }

    private boolean validEvaluationExists() {
        return Metadata.has(Keys.METADATA_KEY_VALID_EVALUATION_FILLING);
    }

    private void addMenu() {
        JPanel menuPanel = new JPanel(new BorderLayout());

        JButton buttonBack = button(GUI_TEXT_BACK, "exit", e -> presenter.onBack());
        JPanel buttonsLeft = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonsLeft.add(buttonBack);
        menuPanel.add(buttonsLeft, BorderLayout.WEST);

        JButton buttonExit = button(GUI_TEXT_QUIT, "exit", e -> presenter.onExitRequested());
        JPanel buttonsRight = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonsRight.add(buttonExit);
        menuPanel.add(buttonsRight, BorderLayout.EAST);


        add(menuPanel, BorderLayout.SOUTH);
    }

    public void setEvaluationProgress(int value) {
        progressBar.setValue(value);
    }

    public void setEvaluationProgressText(String text) {
        progressBar.setString(text);
    }


    public void startEvaluation() {
        setEvaluationProgressText(GUI_TEXT_PREPARE_EVALUATION);
        buttonGeneralInformationFillings.setEnabled(false);
        buttonProbabilitiesFillings.setEnabled(false);
        buttonStartNewEvaluationFillings.setEnabled(false);
    }

    public void evaluationDone() {
        buttonProbabilitiesFillings.setEnabled(true);
        buttonGeneralInformationFillings.setEnabled(true);
        progressBar.setEnabled(false);
    }
}
