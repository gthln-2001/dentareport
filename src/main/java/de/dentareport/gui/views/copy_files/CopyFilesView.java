package de.dentareport.gui.views.copy_files;

import javax.swing.*;
import java.awt.*;

import static de.dentareport.gui.util.SwingUtils.*;
import static de.dentareport.utils.Keys.*;

// TODO: Test?
public class CopyFilesView extends JPanel {

    private final CopyFilesPresenter presenter;

    public CopyFilesView(CopyFilesPresenter presenter) {
        this.presenter = presenter;

        setLayout(createBorderLayout());
        addTitle();
        addContent();
        addMenu();
    }

    void addTitle() {
        add(title(GUI_TEXT_COPY_FILES), BorderLayout.NORTH);
    }

    void addContent() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPanel.add(text(GUI_TEXT_COPY_FILES_EXPLANATION));
        contentPanel.add(Box.createVerticalStrut(80));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JButton buttonCopyFilesAutomatic = buttonLargeMuted(GUI_TEXT_COPY_FILES_AUTOMATIC, "copy_files_automatic", e -> presenter.onNotAvailableInDemo());
        buttonCopyFilesAutomatic.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.add(buttonCopyFilesAutomatic);
        buttonPanel.add(Box.createVerticalStrut(20));

        JButton buttonCopyFilesSelectFolder = buttonLargeMuted(GUI_TEXT_COPY_FILES_SELECT_FOLDER, "copy_files_select_folder", e -> presenter.onNotAvailableInDemo());
        buttonCopyFilesSelectFolder.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.add(buttonCopyFilesSelectFolder);
        buttonPanel.add(Box.createVerticalStrut(20));

        JButton buttonCopyFilesManually = buttonLarge(GUI_TEXT_COPY_FILES_MANUALLY, "copy_files_manually", e -> presenter.onCopyFilesManually());
        buttonCopyFilesManually.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.add(buttonCopyFilesManually);
        buttonPanel.setMaximumSize(buttonPanel.getPreferredSize());

        contentPanel.add(buttonPanel);

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

