package de.dentareport.utils.dbf;

import de.dentareport.exceptions.DentareportUnexpectedEndOfDbfHeaderException;
import de.dentareport.utils.file.ByteSequence;
import de.dentareport.utils.file.File;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// TODO: TEST?
public class DbfFile {

    private static final String CHARSET = "ISO-8859-1";
    private static final int VALUE_END_OF_HEADER = 13; // 0x0d
    private static final int VALUE_MARKED_AS_DELETED = 42; // 0x2a
    private static DbfHeader header;
    private File file;
    private String filename;
    private List<String> requiredColumns;

    public DbfFile(String filename,
                   List<String> requiredColumns,
                   File file) {
        this.filename = filename;
        this.requiredColumns = requiredColumns;
        this.file = file;
        prepareHeader();
        movePointerToStartOfData();
    }

    public DbfFile(String filename,
                   List<String> requiredColumns) {
        this(filename, requiredColumns, new File(filename));
    }

    public void close() {
        file.close();
    }

    public String filename() {
        return filename;
    }

    public long pointerPosition() {
        return file.pointerPosition();
    }

    public void setPointerPosition(long position) {
        file.setPointerPosition(position);
    }

    public void movePointer(int distance) {
        file.movePointer(distance);
    }

    public int readByteAsInt() {
        return file.readByteAsInteger();
    }

    public int readBytesAsInt(int length) {
        return file.readLittleEndian(length);
    }

    public DbfColumn readColumn() {
        checkForEndOfHeader();
        return new DbfColumn(file.readByteSequence(32));
    }

    public int rowLength() {
        return header.rowLength();
    }

    public List<DbfColumn> columns() {
        return header.columns();
    }

    public int headerLength() {
        return header.headerLength();
    }

    public LocalDate lastModified() {
        return header.lastModified();
    }

    public int rowCount() {
        return header.rowCount();
    }

    public int version() {
        return header.version();
    }

    public List<DbfRow> readRows(int numberOfRows) {
        List<DbfRow> rows = new ArrayList<>();
        rows.addAll(this.file.readByteSequences(numberOfRows, rowLength()).stream()
                .map(this::byteSequenceToRow)
                .collect(Collectors.toList()));
        return rows;
    }

    private DbfRow byteSequenceToRow(ByteSequence byteSequence) {
        DbfRow dbfRow = new DbfRow();
        markRowIfDeleted(byteSequence, dbfRow);
        dbfRow = addColumnsToRow(byteSequence, dbfRow);
        return dbfRow;
    }

    private void markRowIfDeleted(ByteSequence byteSequence,
                                  DbfRow dbfRow) {
        if (byteSequence.value(0) == VALUE_MARKED_AS_DELETED) {
            dbfRow.markDeleted();
        }
    }

    private DbfRow addColumnsToRow(ByteSequence byteSequence,
                                   DbfRow dbfRow) {
        int offset = 1;
        for (DbfColumn dbfColumn : columns()) {
            if (isColumnRequired(dbfColumn)) {
                addColumnToRow(dbfRow, byteSequence, offset, dbfColumn);
            }
            offset += dbfColumn.length();
        }
        return dbfRow;
    }

    private void addColumnToRow(DbfRow dbfRow,
                                ByteSequence byteSequence,
                                int offset,
                                DbfColumn dbfColumn) {
        String value = byteSequence.stringSegment(offset, dbfColumn.length(), CHARSET);
        dbfRow.addCell(new DbfCell(dbfColumn.name(), value));
    }

    private boolean isColumnRequired(DbfColumn dbfColumn) {
        return requiredColumns.size() > 0 && requiredColumns.contains(dbfColumn.name());
    }

    private void prepareHeader() {
        header = new DbfHeader(this);
    }

    private void checkForEndOfHeader() {
        if (file.readByteAsInteger() == VALUE_END_OF_HEADER) {
            throw new DentareportUnexpectedEndOfDbfHeaderException();
        }
        movePointer(-1);
    }

    private void movePointerToStartOfData() {
        setPointerPosition(headerLength());
    }
}
