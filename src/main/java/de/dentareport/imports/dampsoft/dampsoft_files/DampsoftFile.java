package de.dentareport.imports.dampsoft.dampsoft_files;

import de.dentareport.Config;
import de.dentareport.gui.util.FileProgressListener;
import de.dentareport.utils.db.DbColumn;
import de.dentareport.utils.db.DbRow;
import de.dentareport.utils.dbf.DbfRow;
import org.apache.commons.lang3.NotImplementedException;

import java.io.File;
import java.util.List;

public interface DampsoftFile {

    void importFile(FileProgressListener listener);

    default Boolean isMissing() {
        File file = new File(Config.importPath() + filename());
        return !file.exists() || !file.isFile();
    }

    default Boolean isRealFile() {
        return true;
    }

    default String filename() {
        throw new NotImplementedException("Method not implemented: filename");
    }

    default String tablename() {
        throw new NotImplementedException("Method not implemented: targetTable");
    }

    default String progressMessage() {
        throw new NotImplementedException("Method not implemented: progressMessage");
    }

    default List<String> columnsToImport() {
        throw new NotImplementedException("Method not implemented: columnsToImport");
    }

    default List<DbRow> convert(DbfRow dbfRow) {
        throw new NotImplementedException("Method not implemented: convert");
    }

    default List<DbColumn> columnsToWrite() {
        throw new NotImplementedException("Method not implemented: columnsToWrite");
    }

    default boolean isValidRow(DbRow dbRow) {
        throw new NotImplementedException("Method not implemented: isValidRow");
    }

    default DbRow removeTemporaryColumns(DbRow dbRow) {
        return dbRow;
    }
}
