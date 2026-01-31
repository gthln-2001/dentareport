package de.dentareport.imports.dampsoft.dampsoft_files;

import com.google.common.collect.ImmutableList;
import de.dentareport.gui.util.FileProgressListener;
import de.dentareport.imports.dampsoft.DampsoftImport;
import de.dentareport.imports.dampsoft.convert.QuantityConvert;
import de.dentareport.imports.dampsoft.convert.SurfacesConvert;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbColumn;
import de.dentareport.utils.db.DbRow;
import de.dentareport.utils.dbf.DbfRow;
import de.dentareport.utils.string.ToothStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static de.dentareport.utils.string.DateStringUtils.isValidDate;

// TODO: TEST?
public class HkplstDbf implements DampsoftFile {

    private final DampsoftImport dampsoftImport;
    private final HkpplanDbf hkpplanDbf;
    private final PatinfoDbf patinfoDbf;

    public HkplstDbf(HkpplanDbf hkpplanDbf, PatinfoDbf patinfoDbf) {
        this.hkpplanDbf = hkpplanDbf;
        this.patinfoDbf = patinfoDbf;
        this.dampsoftImport = new DampsoftImport(this);
    }

    public HkplstDbf() {
        this(new HkpplanDbf(), new PatinfoDbf());
    }

    @Override
    public void importFile(FileProgressListener listener) {
        dampsoftImport.importFileWithoutRebuildingTable(listener);
    }

    @Override
    public String filename() {
        return "HKPLST.DBF";
    }

    @Override
    public String tablename() {
        return "treatments";
    }

    @Override
    public String progressMessage() {
        return Keys.GUI_TEXT_IMPORTING_TREATMENTS_HKP;
    }

    @Override
    public List<String> columnsToImport() {
        return ImmutableList.of("PATNR", "HKPNR", "GEBNR", "ANZAHL", "ZAHN", "FLAECHE", "BEHANDLER", "SIGN");
    }

    @Override
    public List<DbRow> convert(DbfRow dbfRow) {
        List<DbRow> ret = new ArrayList<>();
        for (String tooth : ToothStringUtils.splitToothrange(dbfRow.value("ZAHN").trim())) {
            DbRow row = new DbRow();
            row.addCell(new DbCell("deleted", dbfRow.isDeleted() ? "1" : "0"));
            row.addCell(new DbCell("sequence", "hkp"));
            row.addCell(new DbCell("type", ""));
            row.addCell(new DbCell("comment", ""));
            row.addCell(new DbCell("source", "hkp"));
            row.addCell(new DbCell("patient_index", dbfRow.value("PATNR").trim()));
            row.addCell(new DbCell("handler", dbfRow.value("BEHANDLER").trim()));
            row.addCell(new DbCell("billingcode", dbfRow.value("GEBNR").toUpperCase().trim()));
            row.addCell(new DbCell("tooth", tooth));
            row.addCell(new DbCell("toothrange", dbfRow.value("ZAHN").trim()));
            row.addCell(new DbCell("hkp_index", dbfRow.value("HKPNR").trim()));
            row.addCell(new DbCell("insurance", hkpplanDbf.insurance(row.value("hkp_index"))));
            row.addCell(new DbCell("quantity", QuantityConvert.convert(dbfRow.value("ANZAHL").trim())));
            row.addCell(new DbCell("surfaces", SurfacesConvert.convert(dbfRow.value("FLAECHE"))));
            row.addCell(new DbCell("date", hkpplanDbf.date(row.value("hkp_index"))));
            row.addCell(new DbCell("sign", dbfRow.value("SIGN").trim()));
            ret.add(row);
        }
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
        return isValidDate(dbRow.value("date")) && hasValidValueForQuantity(dbRow)
                && hasValidValueForSign(dbRow) && doesNotExistInPatinfoYet(dbRow);
    }

    @Override
    public DbRow removeTemporaryColumns(DbRow dbRow) {
        dbRow.removeCell("sign");
        return dbRow;
    }

    private boolean doesNotExistInPatinfoYet(DbRow dbRow) {
        return !patinfoDbf.hkpTreatmentExists(dbRow);
    }

    private boolean hasValidValueForSign(DbRow dbRow) {
        return Objects.equals(dbRow.value("sign"), "+");
    }

    private boolean hasValidValueForQuantity(DbRow dbRow) {
        return !Objects.equals(dbRow.value("quantity"), "") && !Objects.equals(dbRow.value("quantity"), "0.00");
    }
}
