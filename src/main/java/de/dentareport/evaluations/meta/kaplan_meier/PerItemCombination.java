package de.dentareport.evaluations.meta.kaplan_meier;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.meta.dependencies.Dependency;
import de.dentareport.evaluations.meta.events.Event;
import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.models.Point;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbRow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class PerItemCombination {

    private String sourceTable;
    private Event event;
    private Dependency dependency;
    private int groupOrder;

    public void evaluate(String sourceTable,
                         Event event,
                         Dependency dependency) {
        this.sourceTable = sourceTable;
        this.event = event;
        this.dependency = dependency;
        this.groupOrder = 1;

        groups().forEach(this::evaluateGroup);
    }

    private void evaluateGroup(String group) {
        try {
            double total = total(group);
            double y = 1.0;
            double previousY = y;
            double finalX = 0.0;
            int i = 1;
            List<Point> kaplanMeier = new ArrayList<>();
            kaplanMeier.add(new Point(0.0, 1.0));
            ResultSet rs = query(group);
            while (rs.next()) {
                if (rs.getInt("censored") == 1) {
                    y = calculateY(total, i, previousY);
                    kaplanMeier.add(new Point(rs.getDouble("interval"), y));
                    previousY = y;
                }
                finalX = rs.getDouble("interval");
                i++;
            }
            if (previousY > 0) {
                kaplanMeier.add(new Point(finalX, previousY));
            }
            writeGroupDataToDb(group, kaplanMeier);
            groupOrder++;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private double total(String group) {
        try {
            if (isWithoutDependency()) {
                return totalWithoutDependency();
            }
            return totalWithDependency(group);
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private boolean isWithoutDependency() {
        return Objects.equals(dependency.name(), Keys.GUI_TEXT_NO_DEPENDENCY);
    }

    private double totalWithoutDependency() throws SQLException {
        return db().query(String.format("SELECT COUNT(*) as total FROM %s", sourceTable))
                .getDouble("total");
    }

    private double totalWithDependency(String group) throws SQLException {
        return db().query(String.format("SELECT COUNT(*) as total FROM %s WHERE %s = '%s'",
                sourceTable,
                dependency.groupColumn(),
                group))
                .getDouble("total");
    }

    private ResultSet query(String group) {
        if (isWithoutDependency()) {
            return queryWithoutDependency();
        }
        return queryWithDependency(group);
    }

    private ResultSet queryWithoutDependency() {
        return db().query(String.format(
                "SELECT %s as interval, %s as censored " +
                        "FROM %s " +
                        "ORDER BY interval + 0 ASC, censored + 0 ASC",
                event.intervalColumn(),
                event.censoredColumn(),
                sourceTable));
    }

    private ResultSet queryWithDependency(String group) {
        return db().query(String.format(
                "SELECT %s as interval, %s as censored " +
                        "FROM %s " +
                        "WHERE %s = '%s' " +
                        "ORDER BY interval + 0 ASC, censored + 0 ASC",
                event.intervalColumn(),
                event.censoredColumn(),
                sourceTable,
                dependency.groupColumn(),
                group));
    }

    private double calculateY(double total,
                              int i,
                              double previousY) {
        // We only calculate this for failures, we do not need values for non-failures yet.
        // So we can assume that the value for "censored" is always 1
        // The original formula is:
        // for i == 1:
        // (total - censored) / total
        // for i != 1:
        // ((total - i + 1 - censored) / (total - i + 1)) * previousY
        if (i == 1) {
            return (total - 1) / total;
        }
        return ((total - i) / (total - i + 1)) * previousY;
    }

    private void writeGroupDataToDb(String group,
                                    List<Point> kaplanMeier) {
        removeOldData(group);

        db().writeRows(targetTable(),
                kaplanMeier.stream()
                        .map(point -> row(group, point.x(), point.y()))
                        .collect(Collectors.toList())
        );
    }

    private void removeOldData(String group) {
        db().execute(String.format(
                "DELETE FROM %s WHERE event='%s' AND dependency = '%s' AND group_name='%s'",
                targetTable(),
                event.name(),
                dependency.name(),
                group));
    }

    private DbRow row(String group,
                      Double x,
                      Double y) {
        DbRow ret = new DbRow();
        ret.addCell(new DbCell("event", event.name()));
        ret.addCell(new DbCell("dependency", dependency.name()));
        ret.addCell(new DbCell("group_name", group));
        ret.addCell(new DbCell("group_order", String.valueOf(groupOrder)));
        ret.addCell(new DbCell("x", String.valueOf(x)));
        ret.addCell(new DbCell("y", String.valueOf(y)));
        return ret;
    }

    private String targetTable() {
        return String.format("%s%s", sourceTable, Keys.DB_TABLE_SUFFIX_KAPLAN_MEIER);
    }

    private List<String> groups() {
        if (isWithoutDependency()) {
            // We have no dependency, which means no item to group the evaluation by.
            // Create only one group for total data.
            return ImmutableList.of(Keys.GUI_TEXT_TOTAL);
        }
        return groupsFromDb();
    }

    private List<String> groupsFromDb() {
        try {
            List<String> ret = new ArrayList<>();
            ResultSet rs = db().query(queryGroups());
            while (rs.next()) {
                ret.add(rs.getString("group_name"));
            }
            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private String queryGroups() {
        return String.format(
                "SELECT DISTINCT %1$s AS group_name " +
                        "FROM %2$s " +
                        "WHERE %1$s NOT IN ('', '%3$s') " +
                        "ORDER BY %4$s",
                dependency.groupColumn(),
                sourceTable,
                Keys.NO_DATA,
                dependency.orderColumn());
    }
}