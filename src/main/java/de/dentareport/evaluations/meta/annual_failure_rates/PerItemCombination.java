package de.dentareport.evaluations.meta.annual_failure_rates;

import de.dentareport.evaluations.meta.dependencies.Dependency;
import de.dentareport.evaluations.meta.events.Event;
import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbRow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static de.dentareport.utils.db.DbConnection.db;

public class PerItemCombination {

    private String sourceTable;
    private Event event;
    private Dependency dependency;
    private HashMap<String, Map<String, String>> afr;
    private Map<String, String> groupOrder = new HashMap<>();

    public void evaluate(String sourceTable,
                         Event event,
                         Dependency dependency) {
        this.sourceTable = sourceTable;
        this.event = event;
        this.dependency = dependency;
        this.afr = new HashMap<>();

        groups().forEach(
                (group, kaplanMeierData) -> afr.put(group, evaluateAfr(kaplanMeierData)));

        writeAfrToDb();
    }

    private Map<String, Map<String, String>> groups() {
        try {
            Map<String, Map<String, String>> ret = new HashMap<>();
            ResultSet rs = db().query(queryGroups());
            while (rs.next()) {
                groupOrder.put(rs.getString("group_name"), rs.getString("group_order"));
                ret.putIfAbsent(rs.getString("group_name"), new LinkedHashMap<>());
                ret.get(rs.getString("group_name")).put(rs.getString("x"), rs.getString("y"));
            }
            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private String queryGroups() {
        return String.format("SELECT * FROM %s WHERE event = '%s' AND dependency = '%s' ORDER BY x + 0",
                String.format("%s%s", sourceTable, Keys.DB_TABLE_SUFFIX_KAPLAN_MEIER),
                event.name(),
                dependency.name()
        );
    }

    private String afrTable() {
        return String.format("%s%s", sourceTable, Keys.DB_TABLE_SUFFIX_AFR);
    }

    private void clearDb() {
        db().execute(String.format("DELETE FROM %s WHERE event='%s' AND dependency = '%s'",
                afrTable(),
                event.name(),
                dependency.name()));
    }

    private Map<String, String> evaluateAfr(Map<String, String> kaplanMeier) {
        Map<String, String> ret = new HashMap<>();
        double stepSize = 5.0 * 365.0;
        final double[] interval = {stepSize}; // interval size for first afr value
        final double[] previousY = {1.0};
        kaplanMeier.forEach((x, y) -> {
            if (Double.valueOf(x) > interval[0]) {
                while (interval[0] <= Double.valueOf(x)) {
                    ret.put(intervalInFullYears(interval[0]), afrValue(interval[0], previousY[0]));
                    interval[0] += stepSize;
                }
            }
            previousY[0] = Double.valueOf(y);
        });
        return ret;
    }

    private String intervalInFullYears(double v) {
        return String.valueOf((int) (v / 365));
    }

    private String afrValue(double interval,
                            double y) {
        return String.format(
                "%.2f",
                (1 - Math.pow(y, (1.0 / (interval / 365.0)))) * 100.0);
    }

    private void writeAfrToDb() {
        clearDb();

        List<DbRow> dbRows = new ArrayList<>();
        afr.forEach((group, afrData) ->
                afrData.forEach((key, value) ->
                        dbRows.add(dbRow(group, key, value))));

        db().writeRows(afrTable(), dbRows);
    }

    private DbRow dbRow(String group,
                        String interval,
                        String afr) {
        DbRow ret = new DbRow();
        ret.addCell(new DbCell("event", event.name()));
        ret.addCell(new DbCell("dependency", dependency.name()));
        ret.addCell(new DbCell("group_name", group));
        ret.addCell(new DbCell("group_order", groupOrder.get(group)));
        ret.addCell(new DbCell("interval", interval));
        ret.addCell(new DbCell("afr", afr));
        return ret;
    }
}