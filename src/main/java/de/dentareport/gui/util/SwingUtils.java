package de.dentareport.gui.util;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SwingUtils {

    public static JButton createButton(String buttonText, String buttonName, ActionListener actionListener) {
        JButton button = new JButton(buttonText);
        button.setName(buttonName);
        button.addActionListener(actionListener);
        return button;
    }
}
