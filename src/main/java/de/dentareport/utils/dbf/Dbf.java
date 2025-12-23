package de.dentareport.utils.dbf;

import java.time.LocalDate;
import java.util.List;

public class Dbf {

    private static final int MAX_CHUNK_SIZE = 8388608; // 8MB  (8 * 1024 * 1024)
    private DbfFile dbfFile;

    public void open(String filename,
                     List<String> requiredColumns) {
        this.open(new DbfFile(filename, requiredColumns));
    }

    public void open(DbfFile dbfFile) {
        this.dbfFile = dbfFile;
    }

    public void close() {
        dbfFile.close();
    }

    public int chunkSizeToRead() {
        return Math.max(1, MAX_CHUNK_SIZE / rowLength());
    }

    public List<DbfColumn> columns() {
        return dbfFile.columns();
    }

    public String filename() {
        return dbfFile.filename();
    }

    public int headerLength() {
        return dbfFile.headerLength();
    }

    public LocalDate lastModified() {
        return dbfFile.lastModified();
    }

    public Integer rowCount() {
        return dbfFile.rowCount();
    }

    public Integer rowLength() {
        return dbfFile.rowLength();
    }

    public List<DbfRow> rows(int count) {
        return dbfFile.readRows(count);
    }

    public List<DbfRow> chunkOfRows() {
        return rows(chunkSizeToRead());
    }

    public Integer version() {
        return dbfFile.version();
    }
}