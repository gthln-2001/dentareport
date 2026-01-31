package de.dentareport.imports.dampsoft.dampsoft_files;

import com.google.common.collect.ImmutableList;
import de.dentareport.imports.dampsoft.DampsoftImport;
import de.dentareport.imports.dampsoft.convert.DateConvert;
import de.dentareport.imports.dampsoft.convert.GenderConvert;
import de.dentareport.imports.dampsoft.convert.TokensConvert;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbColumn;
import de.dentareport.utils.db.DbRow;
import de.dentareport.utils.dbf.DbfRow;
import de.dentareport.utils.string.DateStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


// TODO: TEST?
public class PatientDbf implements DampsoftFile {

    private final DampsoftImport dampsoftImport;

    public PatientDbf() {
        this.dampsoftImport = new DampsoftImport(this);
    }

    @Override
    public void importFile() {
        dampsoftImport.importFile();
    }

    @Override
    public String filename() {
        return "PATIENT.DBF";
    }

    @Override
    public String tablename() {
        return "patients";
    }

    @Override
    public String progressMessage() {
        return Keys.GUI_TEXT_IMPORTING_PATIENT;
    }

    @Override
    public List<String> columnsToImport() {
        return ImmutableList.of(
                "PATNR",
                "GEBDATP",
                "GESCHLECHT",
                "PAT_ANREDE",
                "KUERZEL");
    }

    @Override
    public List<DbRow> convert(DbfRow dbfRow) {
        List<DbRow> ret = new ArrayList<>();
        DbRow row = new DbRow();
        row.addCell(new DbCell("deleted", dbfRow.isDeleted() ? "1" : "0"));
        row.addCell(new DbCell("patient_index", dbfRow.value("PATNR").trim()));
        row.addCell(new DbCell("date_of_birth", DateConvert.convert(dbfRow.value("GEBDATP"))));
        row.addCell(new DbCell("gender", GenderConvert.convert(
                dbfRow.value("GESCHLECHT"),
                dbfRow.value("PAT_ANREDE"))));
        row.addCell(new DbCell("token", TokensConvert.convert(dbfRow.value("KUERZEL"))));
        row.addCell(new DbCell("first_visit", FirstAndLastVisit.firstVisit(row.value("patient_index"))));
        row.addCell(new DbCell("last_visit", FirstAndLastVisit.lastVisit(row.value("patient_index"))));
        row.addCell(new DbCell("age_last_01", DateStringUtils.age(
                row.value("date_of_birth"),
                FirstAndLastVisit.last01(row.value("patient_index")))));
        row.addCell(new DbCell("group_age_last_01",
                DateStringUtils.groupAgeByDecade(row.value("age_last_01"))));
        ret.add(row);
        return ret;
    }

    @Override
    public List<DbColumn> columnsToWrite() {
        List<DbColumn> ret = new ArrayList<>();
        ret.add(new DbColumn("id", "integer primary key"));
        ret.add(new DbColumn("deleted", "text"));
        ret.add(new DbColumn("patient_index", "text"));
        ret.add(new DbColumn("date_of_birth", "text"));
        ret.add(new DbColumn("last_name", "text"));
        ret.add(new DbColumn("first_name", "text"));
        ret.add(new DbColumn("gender", "text"));
        ret.add(new DbColumn("token", "text"));
        ret.add(new DbColumn("first_visit", "text"));
        ret.add(new DbColumn("last_visit", "text"));
        ret.add(new DbColumn("age_last_01", "text"));
        ret.add(new DbColumn("group_age_last_01", "text"));
        return ret;
    }

    @Override
    public boolean isValidRow(DbRow dbRow) {
        return !Objects.equals(dbRow.value("patient_index"), "");
    }
}