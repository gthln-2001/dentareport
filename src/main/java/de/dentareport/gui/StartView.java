package de.dentareport.gui;

import javax.swing.*;
import java.awt.*;

public class StartView extends JPanel {
    public StartView(StartPresenter presenter) {
        setLayout(new BorderLayout());

        JButton openReport = new JButton("Open report");
        openReport.addActionListener(e -> presenter.onOpenReport());

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> presenter.onExitRequested());

        JPanel buttons = new JPanel();
        buttons.add(openReport);
        buttons.add(exit);

        add(buttons, BorderLayout.CENTER);
    }
}

