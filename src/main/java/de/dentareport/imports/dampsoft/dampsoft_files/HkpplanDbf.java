package de.dentareport.imports.dampsoft.dampsoft_files;

import com.google.common.collect.ImmutableList;
import de.dentareport.Config;
import de.dentareport.gui.util.FileProgressListener;
import de.dentareport.imports.dampsoft.convert.DateConvert;
import de.dentareport.utils.Keys;
import de.dentareport.utils.dbf.Dbf;
import de.dentareport.utils.dbf.DbfRow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

// TODO: TEST?
public class HkpplanDbf implements DampsoftFile {

    private static final Map<String, Hkp> hkps = new HashMap<>();
    private final Dbf dbf;

    public HkpplanDbf() {
        this.dbf = new Dbf();
    }

    public static Map<String, Hkp> hkps() {
        return hkps;
    }

    public String insurance(String hkpIndex) {
        try {
            return hkps.get(hkpIndex).insurance();
        } catch (NullPointerException e) {
            return "";
        }
    }

    public String date(String hkpIndex) {
        try {
            return hkps.get(hkpIndex).date();
        } catch (NullPointerException e) {
            return "";
        }
    }

    @Override
    public void importFile(FileProgressListener listener) {
        openDbf();
        Integer rowCount = dbf.rowCount();
        int count = 0;
        int percent;
        List<DbfRow> dbfRows;
        do {
            dbfRows = dbf.chunkOfRows();
            count += dbfRows.size();
            dbfRows.stream()
                    .filter(this::isValidRow)
                    .forEach(dbfRow -> hkps.put(
                            dbfRow.value("HKPNR").trim(),
                            hkp(dbfRow)
                    ));
            percent = count * 100 / rowCount;
            listener.onProgress(percent, Keys.GUI_TEXT_PREPARE_HKP);
        } while (dbfHasMoreRows(dbfRows));
        dbf.close();
    }

    @Override
    public String filename() {
        return "HKPPLAN.DBF";
    }

    private void openDbf() {
        dbf.open(Config.importPath() + filename(), ImmutableList.of("HKPNR", "EINGLIED", "MFRP"));
    }

    private boolean isValidRow(DbfRow dbfRow) {
        return !Objects.equals(dbfRow.value("EINGLIED").trim(), "")
                && !Objects.equals(dbfRow.value("MFRP").trim(), "P");
    }

    private Hkp hkp(DbfRow dbfRow) {
        return new Hkp(DateConvert.convert(dbfRow.value("EINGLIED").trim()),
                dbfRow.value("MFRP").trim());
    }

    private boolean dbfHasMoreRows(List<DbfRow> dbfRows) {
        return dbfRows.size() == dbf.chunkSizeToRead();
    }

    private static class Hkp {
        private final String date;
        private final String insurance;

        private Hkp(String date,
                    String insurance) {
            this.date = date;
            this.insurance = insurance;
        }

        public String date() {
            return date;
        }

        public String insurance() {
            return insurance;
        }
    }
}

