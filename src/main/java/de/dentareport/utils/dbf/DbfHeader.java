package de.dentareport.utils.dbf;

import de.dentareport.exceptions.DentareportUnexpectedEndOfDbfHeaderException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DbfHeader {

    private static final int OFFSET_TYPE = 0;
    private static final int OFFSET_LAST_MODIFIED = 1;
    private static final int OFFSET_ROW_COUNT = 4;
    private static final int OFFSET_HEADER_LENGTH = 8;
    private static final int OFFSET_ROW_LENGTH = 10;
    private static final int OFFSET_COLUMN_INFORMATION = 32;

    private List<DbfColumn> columns;
    private DbfFile file;
    private int headerLength;
    private LocalDate lastModified;
    private long pointerPosition;
    private int rowCount;
    private int rowLength;
    private int version;

    public DbfHeader(DbfFile file) {
        setInstanceVariables(file);
    }

    public List<DbfColumn> columns() {
        return columns;
    }

    public int headerLength() {
        return headerLength;
    }

    public LocalDate lastModified() {
        return lastModified;
    }

    public int rowCount() {
        return rowCount;
    }

    public int rowLength() {
        return rowLength;
    }

    public int version() {
        return version;
    }

    private void setInstanceVariables(DbfFile file) {
        this.file = file;
        this.columns = readColumns();
        this.headerLength = readHeaderLength();
        this.lastModified = readLastModified();
        this.rowCount = readRowCount();
        this.rowLength = readRowLength();
        this.version = readVersion();
    }

    private List<DbfColumn> readColumns() {
        movePointerToOffset(OFFSET_COLUMN_INFORMATION);
        List<DbfColumn> dbfColumns = new ArrayList<>();
        while (true) {
            try {
                dbfColumns.add(file.readColumn());
            } catch (DentareportUnexpectedEndOfDbfHeaderException e) {
                break;
            }
        }
        restorePointerPosition();
        return dbfColumns;
    }

    private void movePointerToOffset(int offset) {
        pointerPosition = file.pointerPosition();
        file.setPointerPosition(offset);
    }

    private void restorePointerPosition() {
        file.setPointerPosition(pointerPosition);
    }

    private Integer readHeaderLength() {
        movePointerToOffset(OFFSET_HEADER_LENGTH);
        int headerLength = file.readBytesAsInt(2);
        restorePointerPosition();
        return headerLength;
    }

    private LocalDate readLastModified() {
        movePointerToOffset(OFFSET_LAST_MODIFIED);
        int year = addFirstDigitsToYear(file.readByteAsInt());
        int month = file.readByteAsInt();
        int day = file.readByteAsInt();
        restorePointerPosition();
        return LocalDate.of(year, month, day);
    }

    /**
     * Add the first two digits to a two digit year value.
     * We assume that year values < 80 are meant to be in the 20th century
     * and values >= 80 are in the 19th century.
     * E.g. 81 => 1981, 15 => 2015
     * We chose 80 because dBase first appeared in the wild around 1980.
     */
    private int addFirstDigitsToYear(int year) {
        if (year < 80) {
            return year + 2000;
        }
        return year + 1900;
    }

    private Integer readRowCount() {
        movePointerToOffset(OFFSET_ROW_COUNT);
        int rowCount = file.readBytesAsInt(4);
        restorePointerPosition();
        return rowCount;
    }

    private Integer readRowLength() {
        movePointerToOffset(OFFSET_ROW_LENGTH);
        int rowLength = file.readBytesAsInt(2);
        restorePointerPosition();
        return rowLength;
    }

    /**
     * Get the dBase-version/filetype.
     * This is some arbitrary value and NOT 3 for dBase III or 4 for dBase IV.
     *
     * @see <a href="http://www.dbf2002.com/dbf-file-format.html">http://www.dbf2002.com/dbf-file-format.html</a>
     */
    private int readVersion() {
        movePointerToOffset(OFFSET_TYPE);
        int version = file.readByteAsInt();
        restorePointerPosition();
        return version;
    }
}
