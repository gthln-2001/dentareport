package de.dentareport.gui.util;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

// TODO: Test
// TODO: TEST?
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

    public static JButton buttonLargeMuted(String buttonText, String buttonName, ActionListener actionListener) {
        JButton button = buttonLarge(buttonText, buttonName, actionListener);
        button.putClientProperty(
                "FlatLaf.style",
                "background: #B1BED9FF;" +
                        "hoverBackground: #B1BED9FF;" +
                        "pressedBackground: #B1BED9FF;" +
                        "foreground: #FFFFFF;"
        );

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

    public static JProgressBar progressBar() {
        JProgressBar progressBar = new JProgressBar(0, 100);
        Dimension size = new Dimension(400, 28);
        progressBar.setPreferredSize(size);
        progressBar.setMaximumSize(size);
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        progressBar.setStringPainted(true);

        return progressBar;
    }

    public static JTable table(AbstractTableModel tableModel) {
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(24);
        sortTable(table);
        return table;
    }

    private static void sortTable(JTable table) {
        TableRowSorter<TableModel> sorter =  new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
        sorter.sort();
    }

    public static JPanel text(String text) {
        JPanel textWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        textWrapper.add(new JLabel("<html>" + text.replace("\n", "<br>") + "</html>"));
        textWrapper.setMaximumSize(textWrapper.getPreferredSize());

        return textWrapper;
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
