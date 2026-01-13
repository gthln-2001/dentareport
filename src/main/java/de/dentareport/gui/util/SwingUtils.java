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
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return button;
    }

    public static JButton buttonMedium(String buttonText, String buttonName, ActionListener actionListener) {
        JButton button = button(buttonText, buttonName, actionListener);
        button.setPreferredSize(new Dimension(320, 35));

        return button;
    }

    public static JButton buttonMediumMuted(String buttonText, String buttonName, ActionListener actionListener) {
        JButton button = buttonMedium(buttonText, buttonName, actionListener);
        button.putClientProperty(
                "FlatLaf.style",
                "background: #B1BED9FF;" +
                        "hoverBackground: #B1BED9FF;" +
                        "pressedBackground: #B1BED9FF;" +
                        "foreground: #FFFFFF;"
        );

        return button;
    }

    public static JButton buttonLarge(String buttonText, String buttonName, ActionListener actionListener) {
        JButton button = button(buttonText, buttonName, actionListener);
        button.setPreferredSize(new Dimension(600, 35));

        return button;
    }

    public static JLabel heading1(String text) {
        return heading(text, Font.BOLD, 48f);
    }

    public static JLabel heading2(String text) {
        return heading(text, Font.PLAIN, 32f);
    }

    public static JLabel image(String path) {
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(SwingUtils.class.getResource(path)));
        JLabel imageLabel = new JLabel(resizeImage(imageIcon, 1000, 500));
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

    private static ImageIcon resizeImage(ImageIcon icon, int maxWidth, int maxHeight) {
        Image image = icon.getImage();
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        double scale = Math.min((double) maxWidth / width, (double) maxHeight / height);
        int newWidth = (int) (width * scale);
        int newHeight = (int) (height * scale);
        Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        return new ImageIcon(resizedImage);
    }
}
