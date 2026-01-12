package de.dentareport.gui.views.import_data;

import javax.swing.*;
import java.awt.*;

import static de.dentareport.gui.util.SwingUtils.*;
import static de.dentareport.utils.Keys.*;

// TODO: Test?
public class ImportDataView extends JPanel {

    private final ImportDataPresenter presenter;

    public ImportDataView(ImportDataPresenter presenter) {
        this.presenter = presenter;

        setLayout(createBorderLayout());
        addTitle();
        addContent();
        addMenu();
    }

    void addTitle() {
        add(title(GUI_TEXT_IMPORT_DATA), BorderLayout.NORTH);
    }

    void addContent() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(Box.createVerticalStrut(120));
        contentPanel.add(heading1(GUI_TEXT_IMPORT_DATA));
//        contentPanel.add(Box.createVerticalStrut(40));
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

