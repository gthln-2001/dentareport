package de.dentareport.gui.views.start;

import javax.swing.*;
import java.awt.*;

import static de.dentareport.utils.Keys.GUI_TEXT_QUIT;

// TODO: Test
public class StartView extends JPanel {
    public StartView(StartPresenter presenter) {
        setLayout(new BorderLayout());

        JButton openReport = new JButton("Open report");
        openReport.addActionListener(e -> presenter.onOpenReport());

        JButton exit = new JButton(GUI_TEXT_QUIT);
        exit.addActionListener(e -> presenter.onExitRequested());

        JPanel buttons = new JPanel();
        buttons.add(openReport);
        buttons.add(exit);

        add(buttons, BorderLayout.CENTER);
    }
}

