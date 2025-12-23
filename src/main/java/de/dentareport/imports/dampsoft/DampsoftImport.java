package de.dentareport.imports.dampsoft;

import de.dentareport.Config;
//import de.dentareport.gui.ProgressUpdate;
import de.dentareport.imports.dampsoft.dampsoft_files.DampsoftFile;
import de.dentareport.utils.db.DbRow;
import de.dentareport.utils.dbf.Dbf;
import de.dentareport.utils.dbf.DbfRow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.dentareport.utils.db.DbConnection.db;

public class DampsoftImport {

    private Dbf dbf;
    private DampsoftFile dampsoftFile;

    public DampsoftImport(DampsoftFile dampsoftFile,
                          Dbf dbf) {
        this.dbf = dbf;
        this.dampsoftFile = dampsoftFile;
    }

    public DampsoftImport(DampsoftFile dampsoftFile) {
        this(dampsoftFile, new Dbf());
    }

    public void importFile() {
        executeImport(true);
    }

    public void importFileWithoutRebuildingTable() {
        executeImport(false);
    }

    public void rebuildTable() {
        db().rebuildTable(dampsoftFile.tablename(), dampsoftFile.columnsToWrite());
    }

    public void importData() {
        int countImportedRows;
        do {
            countImportedRows = importChunkOfRows();
        } while (dbfHasMoreRows(countImportedRows));
    }

    public void closeConnections() {
        dbf.close();
    }

    private void executeImport(Boolean rebuildTable) {
        openDbf();
//        ProgressUpdate.init(dbf.rowCount(), dampsoftFile.progressMessage());
        if (rebuildTable) {
            rebuildTable();
        }
        importData();
        closeConnections();
    }

    private void openDbf() {
        dbf.open(Config.importPath() + dampsoftFile.filename(), dampsoftFile.columnsToImport());
    }

    private int importChunkOfRows() {
        List<DbfRow> dbfRows = dbf.chunkOfRows();
        writeValidRowsToDb(dbfRows);
        return dbfRows.size();
    }

    private void writeValidRowsToDb(List<DbfRow> dbfRows) {
        List<DbRow> validRows = new ArrayList<>();
        for (DbfRow dbfRow : dbfRows) {
//            ProgressUpdate.tick();
            validRows.addAll(dampsoftFile.convert(dbfRow)
                    .stream()
                    .filter(dbRow -> dampsoftFile.isValidRow(dbRow))
                    .map(dbRow -> dampsoftFile.removeTemporaryColumns(dbRow))
                    .collect(Collectors.toList())
            );
        }
        db().writeRows(dampsoftFile.tablename(), validRows);
    }

    private boolean dbfHasMoreRows(int countImportedRows) {
        return countImportedRows == dbf.chunkSizeToRead();
    }
}

