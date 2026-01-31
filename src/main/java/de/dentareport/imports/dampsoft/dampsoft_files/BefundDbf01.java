package de.dentareport.imports.dampsoft.dampsoft_files;

import com.google.common.collect.ImmutableList;
import de.dentareport.imports.dampsoft.DampsoftImport;
import de.dentareport.imports.dampsoft.convert.DateConvert;
import de.dentareport.imports.dampsoft.convert.Evidence01Convert;
import de.dentareport.utils.Dmft;
import de.dentareport.utils.Keys;
import de.dentareport.utils.Toothnumbers;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbColumn;
import de.dentareport.utils.db.DbRow;
import de.dentareport.utils.dbf.DbfRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static de.dentareport.utils.string.DateStringUtils.isValidDate;

// TODO: TEST?
public class BefundDbf01 implements DampsoftFile {

    private final DampsoftImport dampsoftImport;

    public BefundDbf01() {
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
    public String tablename() {
        return "evidences_01";
    }

    @Override
    public String progressMessage() {
        return Keys.GUI_TEXT_IMPORTING_BEFUND_01;
    }

    @Override
    public List<String> columnsToImport() {
        return ImmutableList.of("PATNR", "DATUM", "BEFTYP", "GEBNR",
                "ZA18", "ZA17", "ZA16", "ZA15", "ZA14", "ZA13", "ZA12", "ZA11",
                "ZA21", "ZA22", "ZA23", "ZA24", "ZA25", "ZA26", "ZA27", "ZA28",
                "ZA38", "ZA37", "ZA36", "ZA35", "ZA34", "ZA33", "ZA32", "ZA31",
                "ZA41", "ZA42", "ZA43", "ZA44", "ZA45", "ZA46", "ZA47", "ZA48");
    }

    @Override
    public List<DbRow> convert(DbfRow dbfRow) {
        List<DbRow> ret = new ArrayList<>();
        if (!is01Evidence(dbfRow)) {
            return ret;
        }
        DbRow row = new DbRow();
        row.addCell(new DbCell("deleted", dbfRow.isDeleted() ? "1" : "0"));
        row.addCell(new DbCell("patient_index", dbfRow.value("PATNR").trim()));
        row.addCell(new DbCell("date", DateConvert.convert(dbfRow.value("DATUM").trim())));
        row.addCell(new DbCell("billingcode", dbfRow.value("GEBNR").trim()));
        row.addCells(Evidence01Convert.convert(dbfRow));
        row.addCells(Dmft.calculate(row));
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
        ret.add(new DbColumn("tooth_count_q1", "text"));
        ret.add(new DbColumn("tooth_count_q2", "text"));
        ret.add(new DbColumn("tooth_count_q3", "text"));
        ret.add(new DbColumn("tooth_count_q4", "text"));
        ret.add(new DbColumn("dt", "text"));
        ret.add(new DbColumn("dt_milkteeth", "text"));
        ret.add(new DbColumn("ft", "text"));
        ret.add(new DbColumn("ft_milkteeth", "text"));
        ret.add(new DbColumn("mt", "text"));
        ret.add(new DbColumn("mt_milkteeth", "text"));
        ret.add(new DbColumn("dft", "text"));
        ret.add(new DbColumn("dmft", "text"));
        ret.add(new DbColumn("dmft_milkteeth", "text"));
        for (String toothnumber : Toothnumbers.ALL) {
            ret.add(new DbColumn(String.format("milktooth_%s", toothnumber), "text"));
            ret.add(new DbColumn(String.format("status_%s", toothnumber), "text"));
            ret.add(new DbColumn(String.format("sealing_%s", toothnumber), "text"));
            ret.add(new DbColumn(String.format("vitality_%s", toothnumber), "text"));
            ret.add(new DbColumn(String.format("insufficient_crown_%s", toothnumber), "text"));
            ret.add(new DbColumn(String.format("wf_%s", toothnumber), "text"));
            ret.add(new DbColumn(String.format("wurzelstift_%s", toothnumber), "text"));
            for (String surface : Keys.SURFACES) {
                ret.add(new DbColumn(String.format("filling_%s_%s", toothnumber, surface), "text"));
                ret.add(new DbColumn(String.format("caries_%s_%s", toothnumber, surface), "text"));
            }
        }
        return ret;
    }

    @Override
    public boolean isValidRow(DbRow dbRow) {
        return dbRow.cells().size() != 0 && isValidDate(dbRow.value("date"));
    }

    private boolean is01Evidence(DbfRow dbfRow) {
        return Objects.equals(dbfRow.value("BEFTYP").trim(), "0");
    }
}
