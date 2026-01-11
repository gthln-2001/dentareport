package de.dentareport.gui.views.start;

import javax.swing.*;
import java.awt.*;

import static de.dentareport.gui.util.SwingUtils.*;
import static de.dentareport.utils.Keys.*;

// TODO: Test?
public class StartView extends JPanel {

    private final StartPresenter presenter;

    public StartView(StartPresenter presenter) {
        this.presenter = presenter;

        setLayout(createBorderLayout());
        addTitle();
        addContent();
        addMenu();
    }


    void addTitle() {
        add(createTitle(GUI_TEXT_DENTAREPORT), BorderLayout.NORTH);
    }

    void addContent() {
        JButton buttonImportData = createButton(GUI_TEXT_IMPORT_DATA, "import_data", e -> presenter.onImportData());
        JButton buttonEvaluation = createButton(GUI_TEXT_EVALUATIONS, "evaluations", e -> presenter.onEvaluations());
        JPanel buttons = new JPanel();
        buttons.add(buttonImportData);
        buttons.add(buttonEvaluation);

        add(buttons, BorderLayout.CENTER);
    }

    void addMenu() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());

        JButton buttonExit = createButton(GUI_TEXT_QUIT, "exit", e -> presenter.onExitRequested());
        JPanel buttons = new JPanel();
        buttons.add(buttonExit);
        menuPanel.add(buttons, BorderLayout.EAST);

        add(menuPanel, BorderLayout.SOUTH);
    }
}

