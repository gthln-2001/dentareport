package de.dentareport.gui.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

// TODO: Test
public class SwingUtils {

    public static BorderLayout createBorderLayout() {
        return new BorderLayout(0, 40);
    }

    public static JButton button(String buttonText, String buttonName, ActionListener actionListener) {
        JButton button = new JButton(buttonText);
        button.setName(buttonName);
        button.addActionListener(actionListener);
        button.setFocusable(false);

        return button;
    }

    public static JLabel heading1(String text) {
        return heading(text, Font.BOLD, 48f);
    }

    public static JLabel heading2(String text) {
        return heading(text, Font.PLAIN, 32f);
    }

    public static JLabel image(String path) {
        ImageIcon image = new ImageIcon(Objects.requireNonNull(SwingUtils.class.getResource(path)));
        JLabel imageLabel = new JLabel(image);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return imageLabel;
    }

    public static JLabel title(String text) {
        JLabel title = new JLabel(text, SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Font baseFont = title.getFont();
        title.setFont(baseFont.deriveFont(Font.PLAIN, 36f));

        return title;
    }

    private static JLabel heading(String text, int bold, float size) {
        JLabel heading = new JLabel("<html><center>" + text.replace("\n", "<br>") + "</center></html>");
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font currentFont = heading.getFont();
        Font newFont = currentFont.deriveFont(bold, size);
        heading.setFont(newFont);

        return heading;
    }
}
