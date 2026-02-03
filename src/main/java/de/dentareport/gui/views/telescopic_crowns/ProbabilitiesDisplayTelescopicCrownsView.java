package de.dentareport.gui.views.telescopic_crowns;

import de.dentareport.gui.table_models.TableAfr;
import de.dentareport.gui.table_models.TableGroupSizes;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static de.dentareport.gui.util.SwingUtils.*;
import static de.dentareport.utils.Keys.*;

// TODO: Test?

// TODO: TEST?
public class ProbabilitiesDisplayTelescopicCrownsView extends JPanel {

    private final ProbabilitiesDisplayTelescopicCrownsPresenter presenter;
    private final String event;
    private final String dependency;

    public ProbabilitiesDisplayTelescopicCrownsView(ProbabilitiesDisplayTelescopicCrownsPresenter presenter,
                                                    String event, String dependency) {
        this.event = event;
        this.dependency = dependency;
        this.presenter = presenter;
        setLayout(createBorderLayout());
        addTitle();
        addContent();
        addMenu();
    }

    private void addTitle() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(heading2(chartTitle()));
        add(contentPanel, BorderLayout.NORTH);
    }

    private String chartTitle() {
        String title = String.format("%s %s %s",
                GUI_TEXT_TELESCOPIC_CROWNS,
                GUI_TEXT_UNDER_EVENT,
                this.event);
        if (isGrouped()) {
            title += String.format("\n%s %s",
                    GUI_TEXT_GROUPED_BY,
                    this.dependency);
        }
        return title;
    }

    private boolean isGrouped() {
        return !Objects.equals(this.dependency, GUI_TEXT_NO_DEPENDENCY);
    }

    private void addContent() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPanel.add(tableGroupSizes(), BorderLayout.CENTER);
        contentPanel.add(tableAfr(), BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JScrollPane tableGroupSizes() {
        TableGroupSizes tableModel = presenter.getTableGroupSizes(this.event, this.dependency);

        return new JScrollPane(table(tableModel));
    }

    private JScrollPane tableAfr() {
        TableAfr tableModel = presenter.getTableAfr(this.event, this.dependency);

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
