package de.dentareport.evaluations.meta.distributions;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbColumn;
import de.dentareport.utils.db.DbRow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class Distributions {

    private final Evaluation evaluation;
    private List<String> defaultUnclearValues = ImmutableList.of("no_data", "");

    public Distributions(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public void evaluate() {
        rebuildTable();

        List<DbRow> dbRows = new ArrayList<>();

        dbRows.addAll(perPatient(
                "gender",
                Keys.TEXT_GENDER
        ));
        dbRows.addAll(perPatient(
                "insurance",
                Keys.TEXT_INSURANCE,
                ImmutableList.of("no_data", "BEFUND")
        ));
        dbRows.addAll(perCase(
                "toothtype",
                Keys.TEXT_TOOTHTYPES
        ));
        dbRows.addAll(perCase(
                "endstaendigkeit__of__evidence_01_position_first_after_date_start_observation",
                Keys.TEXT_DISTRIBUTION_ENDSTAENDIG
        ));
        dbRows.addAll(perCase(
                "toothcontacts__of__evidence_01_position_first_after_date_start_observation",
                Keys.TEXT_DISTRIBUTION_TOOTHCONTACTS
        ));

        db().writeRows(targetTable(), dbRows);
    }

    public List<DbColumn> columns() {
        List<String> columns = ImmutableList.of(
                "item",
                "name",
                "value",
                "value_count");

        return columns.stream()
                .map(column -> new DbColumn(column, "text"))
                .collect(Collectors.toList());
    }

    private List<DbRow> perPatient(String item,
                                   String name) {
        return perPatient(item, name, defaultUnclearValues);
    }

    private List<DbRow> perPatient(String item,
                                   String name,
                                   List<String> unclearValues) {
        String query = String.format("SELECT %1$s AS value, " +
                        "COUNT(%1$s) AS value_count " +
                        "FROM (SELECT %1$s FROM %2$s GROUP BY patient_index) " +
                        "GROUP BY %1$s",
                item,
                sourceTable());
        return distribution(item, name, unclearValues, query);
    }

    private List<DbRow> perCase(String item,
                                String name) {
        return perCase(item, name, defaultUnclearValues);
    }

    private List<DbRow> perCase(String item,
                                String name,
                                List<String> unclearValues) {
        String query = String.format("SELECT %1$s AS value, " +
                        "COUNT(%1$s) AS value_count " +
                        "FROM %2$s " +
                        "GROUP BY %1$s",
                item,
                sourceTable());
        return distribution(item, name, unclearValues, query);
    }

    private List<DbRow> distribution(String item,
                                     String name,
                                     List<String> unclearValues,
                                     String query) {
        try {
            List<DbRow> ret = new ArrayList<>();
            ResultSet rs = db().query(query);
            int unclear = 0;

            while (rs.next()) {
                if (unclearValues.contains(rs.getString("value"))) {
                    unclear += Integer.valueOf(rs.getString("value_count"));
                    continue;
                }
                ret.add(row(item, name, rs.getString("value"), rs.getString("value_count")));
            }
            if (unclear != 0) {
                ret.add(row(item, name, "?", String.valueOf(unclear)));
            }
            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private DbRow row(String item,
                      String name,
                      String value,
                      String value_count) {
        DbRow dbRow;
        dbRow = new DbRow();
        dbRow.addCell(new DbCell("item", item));
        dbRow.addCell(new DbCell("name", name));
        dbRow.addCell(new DbCell("value", value));
        dbRow.addCell(new DbCell("value_count", value_count));

        return dbRow;
    }

    private void rebuildTable() {
        db().rebuildTable(targetTable(), columns());
    }

    private String sourceTable() {
        return evaluation.dbTable();
    }

    private String targetTable() {
        return String.format("%s_distributions", evaluation.dbTable());
    }
}