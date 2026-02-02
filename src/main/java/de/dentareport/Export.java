package de.dentareport;

import de.dentareport.evaluations.Evaluation;
import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.gui.util.ProgressListener;
import de.dentareport.utils.Billingcodes;
import de.dentareport.utils.Keys;
import de.dentareport.utils.xls.Xls;
import de.dentareport.utils.xls.XlsColumn;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.dentareport.utils.db.DbConnection.db;
import static de.dentareport.utils.map.MapUtils.sortByKey;

// TODO: TEST?
public class Export {

    private final Translate translate;
    private final Glossary glossary;
    private final Evaluation evaluation;

    public Export(Evaluation evaluation) {
        this.evaluation = evaluation;
        this.glossary = new Glossary();
        this.translate = new Translate();
    }

    public void export(ProgressListener listener) {
        listener.onProgress(0, Keys.GUI_TEXT_WRITING_XLS_EVALUATION);
        Xls xls = new Xls();
        addData(xls, listener);
        addDocumentation(xls);
        addGlossary(xls);
        xls.write(filename());
    }

    public String filename() {
        return String.format("%1$s%2$sexport%2$s%3$s_%4$s.xlsx",
                System.getProperty("user.dir"),
                File.separator,
                evaluation.evaluationName(),
                new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()));
    }

    private void addData(Xls xls, ProgressListener listener) {
        xls.addSheet(Keys.XLS_EVALUATION);
        addDataHeader(xls);
        xls.autosizeHeaderColumns();
        addDataBody(xls, listener);
    }

    private void addDataHeader(Xls xls) {
        addDataHeaderRowIndex(xls);
        addDataHeaderRowName(xls);
        xls.freezeRow();
    }

    private void addDataHeaderRowIndex(Xls xls) {
        evaluation.xlsColumnsInEvaluation().forEach(column -> xls.addCell(column.index(), column.backgroundColor()));
        xls.addRow();
    }

    private void addDataHeaderRowName(Xls xls) {
        evaluation.xlsColumnsInEvaluation().forEach(
                column -> xls.addCell(
                        removeFormatting(evaluation.shortDocumentation(column.name())),
                        column.backgroundColor())
        );
        xls.addRow();
    }

    private String removeFormatting(String value) {
        return value.replace("°°", "");
    }

    private void addDataBody(Xls xls, ProgressListener listener) {
        try (ResultSet rs = db().query("SELECT *, COUNT(*) OVER() AS total_count FROM " + evaluation.dbTable())) {
            int total = -1;
            int count = 0;
            List<XlsColumn> xlsColumns = evaluation.xlsColumnsInEvaluation();
            while (rs.next()) {
                if (total == -1) {
                    total = rs.getInt("total_count");
                    if (total == 0) {
                        listener.onProgress(100, Keys.GUI_TEXT_WRITING_XLS_EVALUATION);
                        return;
                    }
                }
                xlsColumns.forEach(column -> xls.addCell(column.value(rs)));
                xls.addRow();
                count++;
                listener.onProgress(count * 100 / total, Keys.GUI_TEXT_WRITING_XLS_EVALUATION);
            }
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }


    private void addDocumentation(Xls xls) {
        xls.addSheet(Keys.XLS_DOCUMENTATION);
        addDocumentationHeader(xls);
        xls.autosizeHeaderColumns();
        addDocumentationBody(xls);
    }

    private void addDocumentationHeader(Xls xls) {
        xls.addCell("Index");
        xls.addCell("In Auswertung");
        xls.addCell("Name");
        xls.addCell("Beschreibung");
        xls.addRow();
        xls.freezeRow();
    }

    private void addDocumentationBody(Xls xls) {
        evaluation.xlsColumns().stream()
                .filter(XlsColumn::isVisibleInDocumentation)
                .forEach(column -> {
                    xls.addCell(column.index(), column.backgroundColor());
                    xls.addCell(column.isInEvaluation() ? "x" : "", column.backgroundColor());
                    xls.addCell(evaluation.shortDocumentation(column.name()), column.backgroundColor());
                    xls.addCell(evaluation.longDocumentation(column.name()));
                    xls.addRow();
                });
    }

    private void addGlossary(Xls xls) {
        xls.addSheet(Keys.XLS_GLOSSARY);
        addGlossaryStartAndEndObservation(xls);
        addGlossaryBillingcodes(xls);
        addGlossaryGlossary(xls);
        xls.autosizeHeaderColumns();
    }

    private void addGlossaryStartAndEndObservation(Xls xls) {
        xls.addCell(Keys.START_OBSERVATION);
        xls.addCell(evaluation.longDocumentation("event_start_observation"));
        xls.addRow();
        xls.addCell(Keys.END_OBSERVATION);
        xls.addCell(evaluation.longDocumentation("event_end_observation"));
        xls.addRow();
        xls.addRow();
    }

    private void addGlossaryBillingcodes(Xls xls) {
        Map<String, String> billingcodes = new HashMap<>();
        for (String billingposition : evaluation.occurringBillingpositions()) {
            billingcodes.put(
                    translate.translate(billingposition),
                    Billingcodes.forPositionAsString(billingposition));
        }
        billingcodes = sortByKey(billingcodes);
        billingcodes.forEach((key, value) -> {
            xls.addCell(key);
            xls.addCell(value);
            xls.addRow();
        });
        xls.addRow();
    }

    private void addGlossaryGlossary(Xls xls) {
        glossary.glossary().forEach((key, value) -> {
            xls.addCell(key);
            xls.addCell(value);
            xls.addRow();
        });
        xls.addRow();
    }
}
