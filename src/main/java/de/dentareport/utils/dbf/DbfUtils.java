package de.dentareport.utils.dbf;

import de.dentareport.utils.string.StringUtils;

public class DbfUtils {

    private static final int COLUMN_WIDTH_DECIMAL_COUNT = 13;
    private static final int COLUMN_WIDTH_LENGTH = 6;
    private static final int COLUMN_WIDTH_TYPE = 4;
    private static final int COLUMN_WIDTH_NAME = 10;
    private static final int WIDTH_INFO_TABLE = COLUMN_WIDTH_NAME + COLUMN_WIDTH_TYPE + COLUMN_WIDTH_LENGTH + COLUMN_WIDTH_DECIMAL_COUNT + 9;

    /**
     * Print some information about a dbf-file to standard output.
     *
     * @param dbf DbfFile handle for dbf file
     */
    public static void info(Dbf dbf) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int i = 0; i < WIDTH_INFO_TABLE + 10; i++) sb.append('*');
        sb.append("\n\n")
                .append("Filename:      ").append(dbf.filename()).append("\n")
                .append("Version:       ").append(dbf.version()).append("\n")
                .append("Last Modified: ").append(dbf.lastModified()).append("\n")
                .append("DbRow Count:     ").append(dbf.rowCount()).append("\n")
                .append("DbfHeader Length: ").append(dbf.headerLength()).append("\n")
                .append("DbRow Length:    ").append(dbf.rowLength()).append("\n\n")
                .append("Columns: ").append("\n\n")
                .append(StringUtils.rightPad("Name", COLUMN_WIDTH_NAME))
                .append(" | ")
                .append(StringUtils.rightPad("Type", COLUMN_WIDTH_TYPE))
                .append(" | ")
                .append(StringUtils.rightPad("Length", COLUMN_WIDTH_LENGTH))
                .append(" | ")
                .append(StringUtils.rightPad("Decimal Count", COLUMN_WIDTH_DECIMAL_COUNT))
                .append('\n');
        for (int i = 0; i < WIDTH_INFO_TABLE; i++) sb.append('-');
        sb.append("\n");
        for (DbfColumn dbfColumn : dbf.columns()) {
            sb.append(StringUtils.rightPad(dbfColumn.name(), COLUMN_WIDTH_NAME))
                    .append(" | ")
                    .append(StringUtils.leftPad(String.valueOf(dbfColumn.type()), COLUMN_WIDTH_TYPE))
                    .append(" | ")
                    .append(StringUtils.leftPad(String.valueOf(dbfColumn.length()), COLUMN_WIDTH_LENGTH))
                    .append(" | ")
                    .append(StringUtils.leftPad(String.valueOf(dbfColumn.decimalCount()), COLUMN_WIDTH_DECIMAL_COUNT))
                    .append("\n");
        }
        sb.append("\n");
        for (int i = 0; i < WIDTH_INFO_TABLE + 10; i++) sb.append('*');
        sb.append("\n\n");
        System.out.println(sb.toString());
    }
}
