package de.dentareport.imports.dampsoft.dampsoft_files;

import com.google.common.collect.ImmutableList;
import de.dentareport.imports.dampsoft.DampsoftImport;
import de.dentareport.imports.dampsoft.convert.DateConvert;
import de.dentareport.imports.dampsoft.convert.HkpIndexConvert;
import de.dentareport.imports.dampsoft.convert.InfoConvert;
import de.dentareport.imports.dampsoft.convert.SerialConvert;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbColumn;
import de.dentareport.utils.db.DbRow;
import de.dentareport.utils.dbf.DbfRow;

import java.util.*;

import static de.dentareport.utils.string.DateStringUtils.isValidDate;

// TODO: TEST?
public class PatinfoDbf implements DampsoftFile {

    private static Map<String, List<Treatment>> hkpTreatments = new HashMap<>();
    private final DampsoftImport dampsoftImport;
    private HkpplanDbf hkpplanDbf;

    public PatinfoDbf(HkpplanDbf hkpplanDbf) {
        this.hkpplanDbf = hkpplanDbf;
        this.dampsoftImport = new DampsoftImport(this);
    }

    public PatinfoDbf() {
        this(new HkpplanDbf());
    }

    public static List<Treatment> hkpTreatments(String patientIndex) {
        return hkpTreatments.getOrDefault(patientIndex, new ArrayList<>());
    }

    @Override
    public void importFile() {
        dampsoftImport.importFile();
    }

    @Override
    public String filename() {
        return "PATINFO.DBF";
    }

    @Override
    public String tablename() {
        return "treatments";
    }

    @Override
    public String progressMessage() {
        return Keys.GUI_TEXT_IMPORTING_TREATMENTS;
    }

    @Override
    public List<String> columnsToImport() {
        return ImmutableList.of(
                "PATNR",
                "DATUM",
                "SERIELL",
                "ITYPE",
                "INFO",
                "BEHAND",
                "KOMMENTAR",
                "HKPNR");
    }

    @Override
    public List<DbRow> convert(DbfRow dbfRow) {
        List<DbRow> ret = new ArrayList<>();
        DbRow row = new DbRow();
        row.addCell(new DbCell("deleted", dbfRow.isDeleted() ? "1" : "0"));
        row.addCell(new DbCell("patient_index", dbfRow.value("PATNR").trim()));
        row.addCell(new DbCell("date", DateConvert.convert(dbfRow.value("DATUM"))));
        row.addCell(new DbCell("sequence", SerialConvert.convert(dbfRow.value("SERIELL"))));
        row.addCell(new DbCell("type", dbfRow.value("ITYPE").trim()));
        row.addCell(new DbCell("billingcode", InfoConvert.convertToBillingCode(
                dbfRow.value("INFO"),
                row.value("type"))));
        row.addCell(new DbCell("quantity", InfoConvert.convertToQuantity(
                dbfRow.value("INFO"),
                row.value("type"))));
        row.addCell(new DbCell("surfaces", InfoConvert.convertToSurfaces(
                dbfRow.value("INFO"),
                row.value("type"))));
        row.addCell(new DbCell("tooth", InfoConvert.convertToTooth(
                dbfRow.value("INFO"),
                row.value("type"))));
        row.addCell(new DbCell("toothrange", ""));
        row.addCell(new DbCell("hkp_index", HkpIndexConvert.convert(dbfRow.value("HKPNR"))));
        row.addCell(new DbCell("insurance", hkpplanDbf.insurance(row.value("hkp_index"))));
        row.addCell(new DbCell("handler", dbfRow.value("BEHAND").trim()));
        row.addCell(new DbCell("comment", dbfRow.value("KOMMENTAR")));
        row.addCell(new DbCell("source", "patinfo"));
        storeValidHkpTreatments(row);
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
        ret.add(new DbColumn("sequence", "text"));
        ret.add(new DbColumn("type", "text"));
        ret.add(new DbColumn("billingcode", "text"));
        ret.add(new DbColumn("quantity", "text"));
        ret.add(new DbColumn("surfaces", "text"));
        ret.add(new DbColumn("tooth", "text"));
        ret.add(new DbColumn("toothrange", "text"));
        ret.add(new DbColumn("hkp_index", "text"));
        ret.add(new DbColumn("insurance", "text"));
        ret.add(new DbColumn("handler", "text"));
        ret.add(new DbColumn("comment", "text"));
        ret.add(new DbColumn("source", "text"));
        return ret;
    }

    @Override
    public boolean isValidRow(DbRow dbRow) {
        return !Objects.equals(dbRow.value("patient_index"), "")
                && isValidDate(dbRow.value("date"));
    }

    public boolean hkpTreatmentExists(DbRow dbRow) {
        for (Treatment treatment : hkpTreatments(dbRow.value("patient_index"))) {
            if (treatment.isEquivalent(dbRow)) {
                return true;
            }
        }
        return false;
    }

    private void storeValidHkpTreatments(DbRow dbRow) {
        if (!isValidRow(dbRow) || !isHkpRow(dbRow)) {
            return;
        }
        String patientIndex = dbRow.value("patient_index");
        if (!hkpTreatments.containsKey(patientIndex)) {
            hkpTreatments.put(patientIndex, new ArrayList<>());
        }
        if (!hkpTreatmentExists(dbRow)) {
            hkpTreatments.get(patientIndex).add(
                    new Treatment(
                            dbRow.value("hkp_index"),
                            dbRow.value("tooth"),
                            dbRow.value("billingcode")));
        }
    }

    private boolean isHkpRow(DbRow dbRow) {
        return !Objects.equals(dbRow.value("hkp_index"), "");
    }

    // TODO: TEST?
public class Treatment {

        private String billingcode;
        private String hkpIndex;
        private String tooth;

        Treatment(String hkpIndex, String tooth, String billingcode) {
            this.billingcode = billingcode;
            this.hkpIndex = hkpIndex;
            this.tooth = tooth;
        }

        public String billingcode() {
            return billingcode;
        }

        public String hkpIndex() {
            return hkpIndex;
        }

        public String tooth() {
            return tooth;
        }

        private boolean isEquivalent(DbRow dbRow) {
            return Objects.equals(billingcode, dbRow.value("billingcode"))
                    && Objects.equals(hkpIndex, dbRow.value("hkp_index"))
                    && Objects.equals(tooth, dbRow.value("tooth"));
        }
    }
}
