package de.dentareport.gui.views.general_patient_information;

import javax.swing.*;
import java.awt.*;

import static de.dentareport.gui.util.SwingUtils.*;
import static de.dentareport.gui.util.SwingUtils.button;
import static de.dentareport.utils.Keys.*;
import static de.dentareport.utils.Keys.GUI_TEXT_QUIT;

// TODO: Test?

// TODO: TEST?
public class GeneralPatientInformationView extends JPanel {

    private final GeneralPatientInformationPresenter presenter;

    public GeneralPatientInformationView(GeneralPatientInformationPresenter presenter) {
        this.presenter = presenter;
        setLayout(createBorderLayout());
        addTitle();

        addMenu();
    }

    void addTitle() {
        add(title(GUI_TEXT_GENERAL_PATIENT_INFORMATION), BorderLayout.NORTH);
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
