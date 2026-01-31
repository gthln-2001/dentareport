package de.dentareport.utils.xls;

import de.dentareport.Translate;
import de.dentareport.evaluations.AvailableColumns;
import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.models.Column;
import de.dentareport.utils.string.DateStringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Set;

// TODO: TEST?
public class XlsColumn {

    private final AvailableColumns availableColumns;
    private final Translate translate;
    private String name;
    private String index;
    private boolean inEvaluation;
    private boolean toTranslate;
    private boolean isDate;

    public XlsColumn(Column column,
                     Set<String> evaluationColumns) {
        setInstanceVariables(column, evaluationColumns);
        this.availableColumns = new AvailableColumns();
        this.translate = new Translate();
    }

    public String index() {
        if (index == null) {
            index = availableColumns.index(name);
        }
        return index;
    }

    public String name() {
        return name;
    }

    public boolean isInEvaluation() {
        return inEvaluation;
    }

    public boolean isHiddenInDocumentation() {
        String group = availableColumns.group(name);
        return Objects.equals(group, "events") || Objects.equals(group, "replaced_with_shortcut");
    }

    public boolean isVisibleInDocumentation() {
        return !isHiddenInDocumentation();
    }

    public String backgroundColor() {
        return index().substring(0, index().indexOf("."));
    }

    public String value(ResultSet rs) {
        try {
            if (toTranslate) {
                return translate.translate(rs.getString(name()));
            }
            if (isDate) {
                return DateStringUtils.reformatSqlToGerman(rs.getString(name()));
            }
            return rs.getString(name());
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private void setInstanceVariables(Column column,
                                      Set<String> evaluationColumns) {
        this.name = column.name();
        this.toTranslate = column.isTranslatable();
        this.isDate = column.isDate();
        this.inEvaluation = evaluationColumns.contains(name);
    }
}
