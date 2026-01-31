package de.dentareport.imports.dampsoft.dampsoft_files;

import com.google.common.collect.ImmutableList;
import de.dentareport.Config;
import de.dentareport.gui.util.FileProgressListener;
import de.dentareport.utils.Keys;
import de.dentareport.utils.dbf.Dbf;
import de.dentareport.utils.dbf.DbfRow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

// TODO: TEST?
public class PatkuerzDbf implements DampsoftFile {

    private static final Map<Integer, String> tokens = new HashMap<>();
    private final Dbf dbf;

    public PatkuerzDbf() {
        this.dbf = new Dbf();
    }

    public static Map<Integer, String> tokens() {
        return tokens;
    }

    public static String token(int i) {
        return tokens.get(i);
    }

    @Override
    public String filename() {
        return "PATKUERZ.DBF";
    }

    @Override
    public void importFile(FileProgressListener listener) {
        openDbf();
        List<DbfRow> dbfRows;
        Integer rowCount = dbf.rowCount();
        int count = 0;
        int i = 0;
        int percent;
        do {
            dbfRows = dbf.chunkOfRows();
            for (DbfRow dbfRow : dbfRows) {
                count++;
                if (isValidRow(dbfRow)) {
                    tokens.put(i++, dbfRow.value("TYPE").trim().toUpperCase());
                }
            }
            percent = count * 100 / rowCount;
            listener.onProgress(percent, progressMessage());
        } while (dbfHasMoreRows(dbfRows));
        dbf.close();
    }

    @Override
    public String progressMessage() {
        return Keys.GUI_TEXT_IMPORTING_PATKUERZ;
    }

    private void openDbf() {
        dbf.open(Config.importPath() + filename(), ImmutableList.of("TYPE"));
    }

    private boolean dbfHasMoreRows(List<DbfRow> dbfRows) {
        return dbfRows.size() == dbf.chunkSizeToRead();
    }

    private boolean isValidRow(DbfRow dbfRow) {
        return !Objects.equals(dbfRow.value("TYPE").trim(), "");
    }
}
