package de.dentareport.imports.dampsoft.dampsoft_files;

import com.google.common.collect.ImmutableList;
import de.dentareport.gui.util.FileProgressListener;
import de.dentareport.imports.dampsoft.DampsoftImport;
import de.dentareport.imports.dampsoft.convert.DateConvert;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbColumn;
import de.dentareport.utils.db.DbRow;
import de.dentareport.utils.dbf.DbfRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


// TODO: TEST?
public class ZnotizDbf implements DampsoftFile {

    private final DampsoftImport dampsoftImport;

    public ZnotizDbf() {
        this.dampsoftImport = new DampsoftImport(this);
    }

    @Override
    public void importFile(FileProgressListener listener) {
        dampsoftImport.importFile(listener);
    }

    @Override
    public String progressMessage() {
        return Keys.GUI_TEXT_IMPORTING_NOTES;
    }

    @Override
    public String filename() {
        return "ZNOTIZ.DBF";
    }

    @Override
    public String tablename() {
        return "notes";
    }

    @Override
    public List<String> columnsToImport() {
        return ImmutableList.of(
                "PATNR",
                "DATUM",
                "NOTIZ");
    }

    @Override
    public List<DbRow> convert(DbfRow dbfRow) {
        List<DbRow> ret = new ArrayList<>();
        DbRow row = new DbRow();
        row.addCell(new DbCell("deleted", dbfRow.isDeleted() ? "1" : "0"));
        row.addCell(new DbCell("patient_index", dbfRow.value("PATNR").substring(0, 5).trim()));
        row.addCell(new DbCell("tooth", dbfRow.value("PATNR").substring(5).trim()));
        row.addCell(new DbCell("date", DateConvert.convert(dbfRow.value("DATUM"))));
        row.addCell(new DbCell("note", dbfRow.value("NOTIZ").trim()));
        ret.add(row);
        return ret;
    }

    @Override
    public List<DbColumn> columnsToWrite() {
        List<DbColumn> ret = new ArrayList<>();
        ret.add(new DbColumn("id", "integer primary key"));
        ret.add(new DbColumn("deleted", "text"));
        ret.add(new DbColumn("patient_index", "text"));
        ret.add(new DbColumn("tooth", "text"));
        ret.add(new DbColumn("date", "text"));
        ret.add(new DbColumn("note", "text"));
        return ret;
    }

    @Override
    public boolean isValidRow(DbRow dbRow) {
        return !Objects.equals(dbRow.value("patient_index"), "");
    }
}
