package de.dentareport.gui.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// TODO: Test
public class SwingUtils {

    public static JButton createButton(String buttonText, String buttonName, ActionListener actionListener) {
        JButton button = new JButton(buttonText);
        button.setName(buttonName);
        button.addActionListener(actionListener);
        return button;
    }

    public static BorderLayout createBorderLayout() {
        return new BorderLayout(0, 40);
    }

    public static JLabel createTitle(String text) {
        JLabel title = new JLabel(text, SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Font baseFont = title.getFont();
        title.setFont(baseFont.deriveFont(Font.PLAIN, 36f));

        return title;
    }
}
