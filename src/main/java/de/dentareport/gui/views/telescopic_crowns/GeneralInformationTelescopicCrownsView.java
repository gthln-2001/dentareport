package de.dentareport.gui.views.telescopic_crowns;

import de.dentareport.Metadata;
import de.dentareport.gui.table_models.GeneralInformationTelescopicCrownsAverages;
import de.dentareport.gui.table_models.GeneralInformationTelescopicCrownsCountAndDistribution;
import de.dentareport.utils.Keys;
import de.dentareport.utils.date.DateRange;

import javax.swing.*;
import java.awt.*;

import static de.dentareport.gui.util.SwingUtils.*;
import static de.dentareport.utils.Keys.GUI_TEXT_BACK;
import static de.dentareport.utils.Keys.GUI_TEXT_QUIT;

// TODO: Test?

// TODO: TEST?
public class GeneralInformationTelescopicCrownsView extends JPanel {

    private final GeneralInformationTelescopicCrownsPresenter presenter;

    public GeneralInformationTelescopicCrownsView(GeneralInformationTelescopicCrownsPresenter presenter) {
        this.presenter = presenter;
        setLayout(createBorderLayout());
        addTitle();
        addContent();
        addMenu();
    }

    private void addTitle() {
        DateRange evaluationPeriod = Metadata.evaluationPeriod("9");
        String title = String.format(Keys.GUI_TEXT_GENERAL_INFORMATION_TELESCOPIC_CROWNS,
                evaluationPeriod.formattedStart(),
                evaluationPeriod.formattedEnd()
        );
        add(title(title), BorderLayout.NORTH);
    }

    private void addContent() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPanel.add(generalInformationTelescopicCrownsAverages(), BorderLayout.CENTER);
        contentPanel.add(generalInformationTelescopicCrownsCountAndDistribution(), BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JScrollPane generalInformationTelescopicCrownsAverages() {
        GeneralInformationTelescopicCrownsAverages tableModel =
                presenter.getGeneralInformationTelescopicCrownsAverages();

        return new JScrollPane(table(tableModel));
    }

    private JScrollPane generalInformationTelescopicCrownsCountAndDistribution() {
        GeneralInformationTelescopicCrownsCountAndDistribution tableModel =
                presenter.getGeneralInformationTelescopicCrownsCountAndDistribution();

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
