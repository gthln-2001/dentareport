package de.dentareport.gui.views.fillings;

import de.dentareport.evaluations.meta.dependencies.AvailableDependencies;
import de.dentareport.evaluations.meta.events.AvailableEvents;
import de.dentareport.utils.Keys;
import de.dentareport.utils.MiniCache;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static de.dentareport.gui.util.SwingUtils.*;
import static de.dentareport.utils.Keys.*;

// TODO: Test?

// TODO: TEST?
public class ProbabilitiesFillingsView extends JPanel {

    private final ProbabilitiesFillingsPresenter presenter;
    private final AvailableEvents availableEvents;
    private final AvailableDependencies availableDependencies;
    private ButtonGroup eventGroup;
    private ButtonGroup dependencyGroup;

    public ProbabilitiesFillingsView(ProbabilitiesFillingsPresenter presenter) {
        this.availableEvents = new AvailableEvents("7");
        this.availableDependencies = new AvailableDependencies("7");
        MiniCache.clear();
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

        contentPanel.add(createRadioColumns());
        contentPanel.add(Box.createVerticalStrut(20));

        JButton displayButton = button(GUI_TEXT_DISPLAY, "display", e -> presenter.onDisplay(selectedEvent(),
                selectedDependency()));
        displayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(displayButton);

        add(contentPanel, BorderLayout.CENTER);
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

    private JPanel createRadioColumns() {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
        wrapper.setBorder(new EmptyBorder(20, 20, 20, 20));
        JPanel eventColumn = createEventColumn();
        JPanel dependencyColumn = createDependencyColumn();
        eventColumn.setAlignmentY(Component.TOP_ALIGNMENT);
        dependencyColumn.setAlignmentY(Component.TOP_ALIGNMENT);

        wrapper.add(eventColumn);
        wrapper.add(Box.createHorizontalStrut(80));
        wrapper.add(dependencyColumn);

        return wrapper;
    }

    private JPanel createEventColumn() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentY(Component.TOP_ALIGNMENT);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));

        JLabel label = headingLeft3(GUI_TEXT_SELECT_EVENT + ":");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(10));

        eventGroup = new ButtonGroup();
        String selected = selectedEvent();
        availableEvents.events().forEach(event -> {
            JRadioButton radioButton = new JRadioButton(event.name());
            radioButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            if (event.name().equals(selected)) {
                radioButton.setSelected(true);
            }
            radioButton.addActionListener(e -> MiniCache.put("selected_event", radioButton.getText()));
            eventGroup.add(radioButton);
            panel.add(radioButton);
        });

        return panel;
    }

    private JPanel createDependencyColumn() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentY(Component.TOP_ALIGNMENT);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));

        JLabel label = headingLeft3(GUI_TEXT_SELECT_DEPENDENCY + ":");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(10));

        dependencyGroup = new ButtonGroup();
        String selected = selectedDependency();
        availableDependencies.dependencies().forEach(dependency -> {
            JRadioButton radioButton = new JRadioButton(dependency.name());
            radioButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            if (dependency.name().equals(selected)) {
                radioButton.setSelected(true);
            }
            radioButton.addActionListener(e -> MiniCache.put("selected_dependency", radioButton.getText()));

            dependencyGroup.add(radioButton);
            panel.add(radioButton);
        });

        return panel;
    }

    private String selectedEvent() {
        return MiniCache.getOrDefault("selected_event", Keys.GUI_TEXT_TOOTHLOSS);
    }

    private String selectedDependency() {
        return MiniCache.getOrDefault("selected_dependency", Keys.GUI_TEXT_NO_DEPENDENCY);
    }
}