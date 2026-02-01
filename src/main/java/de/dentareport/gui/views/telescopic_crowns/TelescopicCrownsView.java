package de.dentareport.gui.views.telescopic_crowns;

import de.dentareport.Metadata;

import javax.swing.*;
import java.awt.*;

import static de.dentareport.gui.util.SwingUtils.*;
import static de.dentareport.utils.Keys.*;

// TODO: Test?

// TODO: TEST?
public class TelescopicCrownsView extends JPanel {

    private final TelescopicCrownsPresenter presenter;
    private JButton buttonGeneralInformationTelescopicCrowns;
    private JButton buttonProbabilitiesTelescopicCrowns;
    private JButton buttonStartNewEvaluationTelescopicCrowns;
    private JProgressBar progressBar;

    public TelescopicCrownsView(TelescopicCrownsPresenter presenter) {
        this.presenter = presenter;
        setLayout(createBorderLayout());
        addTitle();
        addContent();
        addMenu();
    }

    private void addTitle() {
        add(title(GUI_TEXT_TELESCOPIC_CROWNS), BorderLayout.NORTH);
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

        buttonGeneralInformationTelescopicCrowns = buttonLarge(GUI_TEXT_GENERAL_INFORMATION,
                "general_information_telescopic_crowns", e -> presenter.onGeneralInformationTelescopicCrowns());
        buttonGeneralInformationTelescopicCrowns.setAlignmentX(CENTER_ALIGNMENT);
        buttons.add(buttonGeneralInformationTelescopicCrowns);
        buttons.add(Box.createVerticalStrut(40));

        buttonProbabilitiesTelescopicCrowns = buttonLarge(GUI_TEXT_PROBABILITIES,
                "general_information_telescopic_crowns", e -> presenter.onProbabilitiesTelescopicCrowns());
        buttonProbabilitiesTelescopicCrowns.setAlignmentX(CENTER_ALIGNMENT);
        buttons.add(buttonProbabilitiesTelescopicCrowns);
        buttons.add(Box.createVerticalStrut(120));

        buttonStartNewEvaluationTelescopicCrowns = buttonLarge(GUI_TEXT_START_NEW_EVALUATION,
                "start_new_evaluation_telescopic_crowns", e -> presenter.onStartNewEvaluationTelescopicCrowns());
        buttonStartNewEvaluationTelescopicCrowns.setAlignmentX(CENTER_ALIGNMENT);
        buttons.add(buttonStartNewEvaluationTelescopicCrowns);

        // TODO: implement cancel evaluation (?)
//        JButton buttonCancelDataImport = buttonLargeMuted(GUI_TEXT_CANCEL_DATA_IMPORT, "cancel_data_import",
//                e -> presenter.onBack());
//        buttonCancelDataImport.setAlignmentX(CENTER_ALIGNMENT);
//        buttonPanel.add(buttonCancelDataImport);
        if (!validEvaluationExists()) {
            buttonGeneralInformationTelescopicCrowns.setEnabled(false);
            buttonProbabilitiesTelescopicCrowns.setEnabled(false);
        }

        buttons.setMaximumSize(buttons.getPreferredSize());
        contentPanel.add(buttons);
        contentPanel.add(Box.createVerticalStrut(40));
    }

    private boolean validEvaluationExists() {
        return Metadata.has(METADATA_KEY_VALID_EVALUATION_TELESCOPIC_CROWN);
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
}
