package de.dentareport.gui.views.import_data;

import javax.swing.*;
import java.awt.*;

import static de.dentareport.gui.util.SwingUtils.*;
import static de.dentareport.utils.Keys.*;

// TODO: Test?
// TODO: TEST?
public class ImportDataView extends JPanel {

    private final ImportDataPresenter presenter;
    private JProgressBar progressBar;

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


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JButton buttonStartDataImport= buttonLarge(GUI_TEXT_START_DATA_IMPORT, "start_data_import", e -> presenter.onStartDataImport());
        buttonStartDataImport.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.add(buttonStartDataImport);
        buttonPanel.add(Box.createVerticalStrut(40));

        JButton buttonCancelDataImport = buttonLargeMuted(GUI_TEXT_CANCEL_DATA_IMPORT, "cancel_data_import", e -> presenter.onBack());
        buttonCancelDataImport.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.add(buttonCancelDataImport);

        buttonPanel.setMaximumSize(buttonPanel.getPreferredSize());

        contentPanel.add(buttonPanel);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        contentPanel.add(progressBar);

        add(contentPanel, BorderLayout.CENTER);
    }

    public void setProgress(int value) {
        progressBar.setValue(value);
    }

    public void setProgressText(String text) {
        progressBar.setString(text);
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

