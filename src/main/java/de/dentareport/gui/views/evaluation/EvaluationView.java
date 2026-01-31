package de.dentareport.gui.views.evaluation;

import javax.swing.*;
import java.awt.*;

import static de.dentareport.gui.util.SwingUtils.*;
import static de.dentareport.utils.Keys.*;

// TODO: Test?
// TODO: TEST?
public class EvaluationView extends JPanel {

    private final EvaluationPresenter presenter;

    public EvaluationView(EvaluationPresenter presenter) {
        this.presenter = presenter;

        setLayout(createBorderLayout());
        addTitle();
        addContent();
        addMenu();
    }

    void addTitle() {
        add(title(GUI_TEXT_EVALUATIONS), BorderLayout.NORTH);
    }

    void addContent() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(Box.createVerticalStrut(80));
        contentPanel.add(heading2(GUI_TEXT_CHOOSE_EVALUATION));
        contentPanel.add(Box.createVerticalStrut(20));

        JPanel buttonGrid = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(16, 16, 16, 16);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonGrid.add(buttonMedium(GUI_TEXT_FILLINGS, "button_fillings", e -> presenter.onShowFillings()), gbc);
        gbc.gridy = 1;
        buttonGrid.add(buttonMediumMuted(GUI_TEXT_ENDODONTICS, "button_endodontics", e -> presenter.onNotAvailableInDemo()), gbc);
        gbc.gridy = 2;
        buttonGrid.add(buttonMediumMuted(GUI_TEXT_CROWNS, "button_crowns", e -> presenter.onNotAvailableInDemo()), gbc);
        gbc.gridy = 3;
        buttonGrid.add(buttonMediumMuted(GUI_TEXT_BRIDGES, "button_bridges", e -> presenter.onNotAvailableInDemo()), gbc);
        gbc.gridy = 4;
        buttonGrid.add(buttonMedium(GUI_TEXT_TELESCOPIC_CROWNS, "button_telescopic_crowns", e -> presenter.onShowTelescopicCrowns()), gbc);
        gbc.gridy = 5;
        buttonGrid.add(buttonMediumMuted(GUI_TEXT_IMPLANTS, "button_implants", e -> presenter.onNotAvailableInDemo()), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonGrid.add(buttonMediumMuted(GUI_TEXT_SEALINGS, "button_sealings", e -> presenter.onNotAvailableInDemo()), gbc);
        gbc.gridy = 1;
        buttonGrid.add(buttonMediumMuted(GUI_TEXT_WURZELSTIFTE, "button_wurzelstifte", e -> presenter.onNotAvailableInDemo()), gbc);
        gbc.gridy = 2;
        buttonGrid.add(buttonMediumMuted(GUI_TEXT_PREVENTION_MEASURES, "button_prevention_measures", e -> presenter.onNotAvailableInDemo()), gbc);
        gbc.gridy = 3;
        buttonGrid.add(buttonMediumMuted(GUI_TEXT_INLAYS, "button_inlays", e -> presenter.onNotAvailableInDemo()), gbc);
        gbc.gridy = 4;
        buttonGrid.add(buttonMediumMuted(GUI_TEXT_SURGERY, "button_surgery", e -> presenter.onNotAvailableInDemo()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonGrid.add(buttonLarge(GUI_TEXT_GENERAL_PATIENT_INFORMATION, "button_general_patient_information", e -> presenter.onShowGeneralPatientInformation()), gbc);

        contentPanel.add(buttonGrid);

        add(contentPanel, BorderLayout.CENTER);
    }

    void addMenu() {
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

