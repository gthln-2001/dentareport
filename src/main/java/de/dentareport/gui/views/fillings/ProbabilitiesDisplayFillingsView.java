package de.dentareport.gui.views.fillings;

import de.dentareport.evaluations.meta.dependencies.AvailableDependencies;
import de.dentareport.evaluations.meta.events.AvailableEvents;
import de.dentareport.gui.table_models.TableGroupSizes;
import de.dentareport.utils.Keys;
import de.dentareport.utils.MiniCache;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static de.dentareport.gui.util.SwingUtils.*;
import static de.dentareport.utils.Keys.*;

// TODO: Test?

// TODO: TEST?
public class ProbabilitiesDisplayFillingsView extends JPanel {

    private final ProbabilitiesDisplayFillingsPresenter presenter;
    private final String event;
    private final String dependency;

    public ProbabilitiesDisplayFillingsView(ProbabilitiesDisplayFillingsPresenter presenter, String event, String dependency) {
        this.event = event;
        this.dependency = dependency;
        this.presenter = presenter;
        setLayout(createBorderLayout());
        addTitle();
        addContent();
        addMenu();
    }

    private void addTitle() {
        add(title(GUI_TEXT_FILLINGS), BorderLayout.NORTH);
    }

    private void addContent() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPanel.add(Box.createVerticalStrut(80));
        contentPanel.add(heading2(GUI_TEXT_PROBABILITIES));
        contentPanel.add(Box.createVerticalStrut(40));

        contentPanel.add(tableGroupSizes(), BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JScrollPane tableGroupSizes() {
        TableGroupSizes tableModel = presenter.getTableGroupSizes(this.event, this.dependency);

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