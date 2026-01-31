package de.dentareport.groups;

import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbRow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static de.dentareport.utils.db.DbConnection.db;
import static de.dentareport.utils.string.StringUtils.isNumeric;

// TODO: TEST?
public class ColumnGroups {

    private String table;
    private String value;
    private String target;
    private List<Integer> limits;

    public void group(String table,
                      String value,
                      String target) {
        this.table = table;
        this.value = value;
        this.target = target;
        if (value.equals("age__at__event_start_observation")) {
            groupAge();
        } else {
            groupInThirds();
        }
    }

    private void groupAge() {
        db().execute(String.format("UPDATE %s SET %s='>80' WHERE %s > 80", table, target, value));
        db().execute(String.format("UPDATE %1$s SET %2$s='61-80' WHERE %3$s >= 61 AND %3$s <= 80", table, target, value));
        db().execute(String.format("UPDATE %1$s SET %2$s='41-60' WHERE %3$s >= 41 AND %3$s <= 60", table, target, value));
        db().execute(String.format("UPDATE %1$s SET %2$s='21-40' WHERE %3$s >= 21 AND %3$s <= 40", table, target, value));
        db().execute(String.format("UPDATE %s SET %s='0-20' WHERE %s <= 20", table, target, value));
        db().execute(String.format("UPDATE %1$s SET %2$s='%3$s' WHERE %4$s = '%3$s'", table, target, Keys.NO_DATA, value));
    }

    private void groupInThirds() {
        this.limits = limits();
        updateTable();
    }

    private List<Integer> limits() {
        List<Integer> values = sortedListOfValues();
        int chunkSize = (int) Math.round((double) values.size() / 3);
        return new ArrayList<>(Arrays.asList(values.get(chunkSize - 1), values.get((2 * chunkSize))));
    }

    private List<Integer> sortedListOfValues() {
        List<Integer> values = listOfValues();
        Collections.sort(values);
        return values;
    }

    private List<Integer> listOfValues() {
        try {
            List<Integer> values = new ArrayList<>();
            ResultSet rs = db().query(String.format("SELECT %s FROM %s", value, table));
            while (rs.next()) {
                if (isNumeric(rs.getString(value))) {
                    values.add(Integer.valueOf(rs.getString(value)));
                }
            }
            return values;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private void updateTable() {
        ResultSet rs = db().query(String.format("SELECT id, %s FROM %s", value, table));
        db().updateRows(table, updatedRows(rs));
    }

    private List<DbRow> updatedRows(ResultSet rs) {
        try {
            List<DbRow> dbRows = new ArrayList<>();
            while (rs.next()) {
                dbRows.add(updatedRow(rs));
            }
            return dbRows;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private DbRow updatedRow(ResultSet rs) {
        try {
            DbRow dbRow = new DbRow();
            dbRow.addCell(new DbCell("id", rs.getString("id")));
            dbRow.addCell(new DbCell(target, groupValue(rs.getString(value))));
            return dbRow;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private String groupValue(String value) {
        if (!isNumeric(value)) {
            return Keys.NO_DATA;
        }
        Integer intValue = Integer.valueOf(value);
        if (intValue <= limits.get(0)) {
            return String.format("<=%s", String.valueOf(limits.get(0)));
        }
        if (intValue >= limits.get(1)) {
            return String.format(">=%s", String.valueOf(limits.get(1)));
        }
        return String.format(">%s, <%s", String.valueOf(limits.get(0)), String.valueOf(limits.get(1)));
    }
}