package de.dentareport.imports.dampsoft.dampsoft_files;

import com.google.common.collect.ImmutableList;
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
public class EndostmpDbf implements DampsoftFile {

    private final DampsoftImport dampsoftImport;

    public EndostmpDbf() {
        this.dampsoftImport = new DampsoftImport(this);
    }

    @Override
    public void importFile() {
        dampsoftImport.importFile();
    }

    @Override
    public String filename() {
        return "ENDOSTMP.DBF";
    }

    @Override
    public String progressMessage() {
        return Keys.GUI_TEXT_IMPORTING_ENDOSTAMPS;
    }

    @Override
    public String tablename() {
        return "endostamps";
    }

    @Override
    public List<String> columnsToImport() {
        return ImmutableList.of(
                "PATNR",
                "DATUM",
                "ZAHN",
                "KANAL",
                "ISO2");
    }

    @Override
    public List<DbRow> convert(DbfRow dbfRow) {
        List<DbRow> ret = new ArrayList<>();
        DbRow row = new DbRow();
        row.addCell(new DbCell("deleted", dbfRow.isDeleted() ? "1" : "0"));
        row.addCell(new DbCell("patient_index", dbfRow.value("PATNR").trim()));
        row.addCell(new DbCell("date", DateConvert.convert(dbfRow.value("DATUM"))));
        row.addCell(new DbCell("tooth", dbfRow.value("ZAHN")));
        row.addCell(new DbCell("canal", dbfRow.value("KANAL").trim()));
        row.addCell(new DbCell("iso2", dbfRow.value("ISO2")));
        ret.add(row);
        return ret;
    }

    @Override
    public List<DbColumn> columnsToWrite() {
        List<DbColumn> ret = new ArrayList<>();
        ret.add(new DbColumn("id", "integer primary key"));
        ret.add(new DbColumn("deleted", "text"));
        ret.add(new DbColumn("patient_index", "text"));
        ret.add(new DbColumn("date", "text"));
        ret.add(new DbColumn("tooth", "text"));
        ret.add(new DbColumn("canal", "text"));
        ret.add(new DbColumn("iso2", "text"));
        return ret;
    }

    @Override
    public boolean isValidRow(DbRow dbRow) {
        return !Objects.equals(dbRow.value("patient_index"), "");
    }
}