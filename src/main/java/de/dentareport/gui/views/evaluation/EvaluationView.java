package de.dentareport.gui.views.evaluation;

import javax.swing.*;
import java.awt.*;

import static de.dentareport.gui.util.SwingUtils.*;
import static de.dentareport.utils.Keys.*;

// TODO: Test?
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
        contentPanel.add(Box.createVerticalStrut(40));


        // Hauptpanel mit GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Abstand zwischen Buttons
        gbc.fill = GridBagConstraints.NONE; // Buttons behalten ihre Größe
        gbc.anchor = GridBagConstraints.NORTH; // Oben ausrichten

        int leftButtons = 6;
        int rightButtons = 5;
        int maxRows = Math.max(leftButtons, rightButtons);

        for (int row = 0; row < maxRows; row++) {
            // Linke Spalte
            if (row < leftButtons) {
                JButton btn = new JButton("L" + (row + 1));
                btn.setPreferredSize(new Dimension(100, 30));
                gbc.gridx = 0;
                gbc.gridy = row;
                mainPanel.add(btn, gbc);
            }

            // Rechte Spalte
            if (row < rightButtons) {
                JButton btn = new JButton("R" + (row + 1));
                btn.setPreferredSize(new Dimension(100, 30));
                gbc.gridx = 1;
                gbc.gridy = row;
                mainPanel.add(btn, gbc);
            }
        }

        // Zentrierter Button unten
        JButton centerButton = new JButton("Zentrierter Button");
        centerButton.setPreferredSize(new Dimension(150, 35));
        gbc.gridx = 0;
        gbc.gridy = maxRows;
        gbc.gridwidth = 2; // über beide Spalten zentrieren
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(centerButton, gbc);


        contentPanel.add(mainPanel);







//        contentPanel.add(heading2(GUI_TEXT_HEADING_START_PANE_1));
//        contentPanel.add(Box.createVerticalStrut(40));
//        contentPanel.add(image("/teaser.png"));
//        contentPanel.add(Box.createVerticalStrut(40));
//        contentPanel.add(heading2(GUI_TEXT_HEADING_START_PANE_2));
//        contentPanel.add(Box.createVerticalStrut(40));
//        JButton buttonImportData = button(GUI_TEXT_IMPORT_DATA, "import_data", e -> presenter.onImportData());
//        JButton buttonEvaluation = button(GUI_TEXT_EVALUATIONS, "evaluations", e -> presenter.onEvaluations());
//        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
//        buttonRow.add(buttonImportData);
//        buttonRow.add(buttonEvaluation);
//        buttonRow.setAlignmentX(Component.CENTER_ALIGNMENT);
//        contentPanel.add(buttonRow);

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

