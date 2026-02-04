package de.dentareport.gui.util;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

// TODO: Test
// TODO: TEST?
public class SwingUtils {

    public static final int DEFAULT_TABLE_COLUMN_MARGIN = 10;

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
        return headingCenter(text, Font.BOLD, 48f);
    }

    public static JLabel heading2(String text) {
        return headingCenter(text, Font.PLAIN, 32f);
    }

    public static JLabel heading3(String text) {
        return headingCenter(text, Font.PLAIN, 28f);
    }

    public static JLabel headingLeft3(String text) {
        return headingLeft(text, Font.PLAIN, 28f);
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
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        sortTable(table);
        zebraTable(table);
        resizeColumnWidths(table);

        return table;
    }

    private static void sortTable(JTable table) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
        sorter.sort();
    }

    private static void zebraTable(JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private final Color evenRow = UIManager.getColor("Table.alternateRowColor");
            private final Color oddRow = UIManager.getColor("Table.background");

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? evenRow : oddRow);
                }
                c.setHorizontalAlignment(column <= 1 ? LEFT : CENTER);

                return c;
            }
        });
    }

    private static void resizeColumnWidths(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = getHeaderWidth(table, column);
            width = getMaxCellWidth(table, column, width);
            width += DEFAULT_TABLE_COLUMN_MARGIN;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    private static int getMaxCellWidth(JTable table, int column, int width) {
        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
            Component comp = cellRenderer.getTableCellRendererComponent(table, table.getValueAt(row, column), false,
                    false, row, column);
            width = Math.max(width, comp.getPreferredSize().width);
        }
        return width;
    }

    private static int getHeaderWidth(JTable table, int column) {
        int width;
        TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
        Component headerComp = headerRenderer.getTableCellRendererComponent(table, table.getColumnName(column), false
                , false, 0, column);
        width = headerComp.getPreferredSize().width;
        return width;
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

    private static JLabel headingLeft(String text, int bold, float size) {
        JLabel heading = heading(text, bold, size);
        heading.setHorizontalAlignment(SwingConstants.LEFT);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        return heading;
    }

    private static JLabel headingCenter(String text, int bold, float size) {
        JLabel heading = heading(text, bold, size);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        return heading;
    }

    private static JLabel heading(String text, int bold, float size) {
        JLabel heading = new JLabel("<html><center>" + text.replace("\n", "<br>") + "</center></html>");
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
