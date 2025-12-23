package de.dentareport.gui.views.start;

import javax.swing.*;
import java.awt.*;

import static de.dentareport.gui.util.SwingUtils.createButton;
import static de.dentareport.utils.Keys.GUI_TEXT_QUIT;

public class StartView extends JPanel {

    public StartView(StartPresenter presenter) {
        setLayout(new BorderLayout());

        JButton openReport = createButton("Open report", "open_report", e -> presenter.onOpenReport());
        JButton exit = createButton(GUI_TEXT_QUIT, "exit", e -> presenter.onExitRequested());

        JPanel buttons = new JPanel();
        buttons.add(openReport);
        buttons.add(exit);

        add(buttons, BorderLayout.CENTER);
    }
}

