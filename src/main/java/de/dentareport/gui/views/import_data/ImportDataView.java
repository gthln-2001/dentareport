package de.dentareport.gui.views.import_data;

import javax.swing.*;
import java.awt.*;

import static de.dentareport.gui.util.SwingUtils.*;
import static de.dentareport.utils.Keys.*;

// TODO: Test?
// TODO: TEST?
public class ImportDataView extends JPanel {

    private final ImportDataPresenter presenter;
    private JProgressBar overallProgressBar;
    private JProgressBar fileProgressBar;
    private JButton buttonStartDataImport;
    private JButton buttonEvaluation;

    public ImportDataView(ImportDataPresenter presenter) {
        this.presenter = presenter;
        presenter.setView(this);

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
        contentPanel.add(Box.createVerticalStrut(80));
        addTopButtons(contentPanel);
        addProgressBars(contentPanel);
        addBottomButtons(contentPanel);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void addTopButtons(JPanel contentPanel) {
        JPanel topButtons = topButtons();
        contentPanel.add(topButtons);
        contentPanel.add(Box.createVerticalStrut(80));
    }

    private void addProgressBars(JPanel contentPanel) {
        overallProgressBar = progressBar();
        contentPanel.add(overallProgressBar);

        contentPanel.add(Box.createVerticalStrut(40));

        fileProgressBar = progressBar();
        contentPanel.add(fileProgressBar);
        contentPanel.add(Box.createVerticalStrut(80));
    }

    private void addBottomButtons(JPanel contentPanel) {
        JPanel buttonPanelForEvaluation = new JPanel();
        buttonPanelForEvaluation.setLayout(new BoxLayout(buttonPanelForEvaluation, BoxLayout.Y_AXIS));
        buttonEvaluation = buttonLarge(GUI_TEXT_EVALUATIONS, "evaluations", e -> presenter.onEvaluations());
        buttonEvaluation.setEnabled(false);
        buttonEvaluation.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanelForEvaluation.add(buttonEvaluation);
        buttonPanelForEvaluation.setMaximumSize(buttonPanelForEvaluation.getPreferredSize());
        contentPanel.add(buttonPanelForEvaluation);
    }

    private JPanel topButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonStartDataImport = buttonLarge(GUI_TEXT_START_DATA_IMPORT, "start_data_import",
                e -> presenter.onStartDataImport());
        buttonStartDataImport.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.add(buttonStartDataImport);
        buttonPanel.add(Box.createVerticalStrut(40));

        // TODO: implement cancel import
//        JButton buttonCancelDataImport = buttonLargeMuted(GUI_TEXT_CANCEL_DATA_IMPORT, "cancel_data_import",
//                e -> presenter.onBack());
//        buttonCancelDataImport.setAlignmentX(CENTER_ALIGNMENT);
//        buttonPanel.add(buttonCancelDataImport);
        buttonPanel.setMaximumSize(buttonPanel.getPreferredSize());
        return buttonPanel;
    }

    public void setOverallProgress(int value) {
        overallProgressBar.setValue(value);
    }

    public void setOverallProgressText(String text) {
        overallProgressBar.setString(text);
    }

    public void setFileProgress(int value) {
        fileProgressBar.setValue(value);
    }

    public void setFileProgressText(String text) {
        fileProgressBar.setString(text);
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

    public void startImport() {
        buttonStartDataImport.setEnabled(false);
    }

    public void importDone() {
        buttonEvaluation.setEnabled(true);
    }
}

