package de.dentareport.imports.dampsoft.dampsoft_files;

import com.google.common.collect.ImmutableList;
import de.dentareport.imports.dampsoft.DampsoftImport;
import de.dentareport.imports.dampsoft.convert.DateConvert;
import de.dentareport.imports.dampsoft.convert.EvidenceHkpConvert;
import de.dentareport.utils.Keys;
import de.dentareport.utils.Toothnumbers;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbColumn;
import de.dentareport.utils.db.DbRow;
import de.dentareport.utils.dbf.DbfRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static de.dentareport.utils.string.DateStringUtils.isValidDate;

public class BefundDbfHkp implements DampsoftFile {

    private final DampsoftImport dampsoftImport;

    public BefundDbfHkp() {
        this.dampsoftImport = new DampsoftImport(this);
    }

    @Override
    public void importFile() {
        dampsoftImport.importFile();
    }

    @Override
    public String filename() {
        return "BEFUND.DBF";
    }

    @Override
    public String progressMessage() {
        return Keys.GUI_TEXT_IMPORTING_BEFUND_HKP;
    }

    @Override
    public String tablename() {
        return "evidences_hkp";
    }

    @Override
    public List<String> columnsToImport() {
        return ImmutableList.of("PATNR", "DATUM", "BEFTYP", "GEBNR", "HKPNR",
                "ZA18", "ZA17", "ZA16", "ZA15", "ZA14", "ZA13", "ZA12", "ZA11",
                "ZA21", "ZA22", "ZA23", "ZA24", "ZA25", "ZA26", "ZA27", "ZA28",
                "ZA38", "ZA37", "ZA36", "ZA35", "ZA34", "ZA33", "ZA32", "ZA31",
                "ZA41", "ZA42", "ZA43", "ZA44", "ZA45", "ZA46", "ZA47", "ZA48");
    }

    @Override
    public List<DbRow> convert(DbfRow dbfRow) {
        List<DbRow> ret = new ArrayList<>();
        if (!isHkpEvidence(dbfRow)) {
            return ret;
        }
        DbRow row = new DbRow();
        row.addCell(new DbCell("deleted", dbfRow.isDeleted() ? "1" : "0"));
        row.addCell(new DbCell("patient_index", dbfRow.value("PATNR").trim()));
        row.addCell(new DbCell("date", DateConvert.convert(dbfRow.value("DATUM").trim())));
        row.addCell(new DbCell("billingcode", dbfRow.value("GEBNR").trim()));
        row.addCell(new DbCell("hkp_index", dbfRow.value("HKPNR").trim()));
        row.addCells(EvidenceHkpConvert.convert(dbfRow));
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
        ret.add(new DbColumn("billingcode", "text"));
        ret.add(new DbColumn("hkp_index", "text"));
        ret.addAll(Toothnumbers.ALL.stream()
                .map(toothnumber -> new DbColumn(
                        String.format("status_%s", toothnumber),
                        "text"))
                .collect(Collectors.toList()));
        return ret;
    }

    @Override
    public boolean isValidRow(DbRow dbRow) {
        return dbRow.cells().size() != 0 && isValidDate(dbRow.value("date"));
    }

    private boolean isHkpEvidence(DbfRow dbfRow) {
        return Objects.equals(dbfRow.value("BEFTYP").trim(), "H");
    }
}
