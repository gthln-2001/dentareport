package de.dentareport.gui.views.fillings;

import de.dentareport.Metadata;
import de.dentareport.gui.table_models.GeneralInformationFillingsAverages;
import de.dentareport.gui.table_models.GeneralInformationFillingsCountAndDistribution;
import de.dentareport.gui.table_models.GeneralInformationTelescopicCrownsAverages;
import de.dentareport.gui.table_models.GeneralInformationTelescopicCrownsCountAndDistribution;
import de.dentareport.utils.date.DateRange;

import javax.swing.*;
import java.awt.*;

import static de.dentareport.gui.util.SwingUtils.*;
import static de.dentareport.utils.Keys.*;

// TODO: Test?

// TODO: TEST?
public class GeneralInformationFillingsView extends JPanel {

    private final GeneralInformationFillingsPresenter presenter;

    public GeneralInformationFillingsView(GeneralInformationFillingsPresenter presenter) {
        this.presenter = presenter;
        presenter.setView(this);
        setLayout(createBorderLayout());
        addTitle();
        addContent();
        addMenu();
    }

    private void addTitle() {
        DateRange evaluationPeriod = Metadata.evaluationPeriod("7");
        String title = String.format(GUI_TEXT_GENERAL_INFORMATION_FILLINGS,
                evaluationPeriod.formattedStart(),
                evaluationPeriod.formattedEnd()
        );
        add(title(title), BorderLayout.NORTH);
    }

    private void addContent() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPanel.add(generalInformationFillingsAverages(), BorderLayout.CENTER);
//        contentPanel.add(generalInformationFillingsCountAndDistribution(), BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JScrollPane generalInformationFillingsAverages() {
        GeneralInformationFillingsAverages tableModel =
                presenter.getGeneralInformationFillingsAverages();

        return new JScrollPane(table(tableModel));
    }

    private JScrollPane generalInformationFillingsCountAndDistribution() {
        GeneralInformationFillingsCountAndDistribution tableModel =
                presenter.getGeneralInformationFillingsCountAndDistribution();

        return new JScrollPane(table(tableModel));
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
