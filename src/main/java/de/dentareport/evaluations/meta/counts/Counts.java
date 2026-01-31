package de.dentareport.evaluations.meta.counts;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbColumn;
import de.dentareport.utils.db.DbRow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class Counts {

    private final Evaluation evaluation;
    private ResultSet rs;
    private List<Map<String, String>> items;

    public Counts(Evaluation evaluation) {
        this.evaluation = evaluation;
        this.items = items();
    }

    public void evaluate() {
        try {
            rebuildTable();
            rs = db().query(query());
            rs.next();

            db().writeRows(targetTable(), dbRows());
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    public List<DbColumn> columns() {
        List<String> columns = ImmutableList.of(
                "item",
                "name",
                "value"
        );
        return columns.stream()
                .map(column -> new DbColumn(column, "text"))
                .collect(Collectors.toList());
    }

    private List<Map<String, String>> items() {
        return ImmutableList.<Map<String, String>>builder()
                .add(ImmutableMap.of(
                        "key", "patient_count",
                        "name", Keys.TEXT_COUNT_PATIENTS,
                        "sql", "COUNT(DISTINCT patient_index)"
                ))
                .add(ImmutableMap.of(
                        "key", "case_count",
                        "name", Keys.TEXT_COUNT_CASES,
                        "sql", "COUNT(*)"
                ))
                .add(ImmutableMap.of(
                        "key", "tooth_loss_count",
                        "name", Keys.TEXT_COUNT_TOOTH_LOSS,
                        "sql", sqlPartial("censored__of__treatment_position_first_with_" + Keys.EXTRACTION + "_after_date_start_observation")
                ))
                .add(ImmutableMap.of(
                        "key", "rezementierung_count",
                        "name", Keys.TEXT_COUNT_REZEMENTIERUNG,
                        "sql", sqlPartial("censored__of__treatment_position_first_with_" + Keys.REZEMENTIERUNG + "_after_date_start_observation")
                ))
                .add(ImmutableMap.of(
                        "key", "endodontie_count",
                        "name", Keys.TEXT_COUNT_ENDODONTIE,
                        "sql", sqlPartial("censored__of__treatment_position_first_with_" + Keys.VITE_TREP_WK + "_after_date_start_observation")
                ))
                .add(ImmutableMap.of(
                        "key", "wurzelstift_count",
                        "name", Keys.TEXT_COUNT_WURZELSTIFT,
                        "sql", sqlPartial("censored__of__treatment_position_first_with_" + Keys.WURZELSTIFT + "_after_date_start_observation")
                )).build();
    }

    private String sqlPartial(String column) {
        return String.format("(SELECT COUNT(*) FROM %s WHERE %s ='1')",
                sourceTable(),
                column);
    }

    private List<DbRow> dbRows() {
        return items.stream()
                .map(item -> row(item.get("key"), item.get("name")))
                .collect(Collectors.toList());
    }

    private String query() {
        return String.format("SELECT %s FROM %s",
                combineSqlPartials(),
                sourceTable()
        );
    }

    private String combineSqlPartials() {
        return items.stream()
                .map(item -> String.format("%s AS %s", item.get("sql"), item.get("key")))
                .collect(Collectors.joining(","));
    }

    private String sourceTable() {
        return evaluation.dbTable();
    }

    private String targetTable() {
        return String.format("%s_counts", evaluation.dbTable());
    }

    private DbRow row(String item, String name) {
        try {
            DbRow dbRow;
            dbRow = new DbRow();
            dbRow.addCell(new DbCell("item", item));
            dbRow.addCell(new DbCell("name", name));
            dbRow.addCell(new DbCell("value", rs.getString(item)));
            return dbRow;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private void rebuildTable() {
        db().rebuildTable(targetTable(), columns());
    }
}