package de.dentareport.evaluations.meta.averages;

import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbRow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.dentareport.utils.db.DbConnection.db;

public abstract class Calculator {

    private final Map<String, String> medians;
    private Averages averages;
    protected Items items;

    public Calculator(Averages averages) {
        this.items = new Items();
        this.averages = averages;
        this.medians = medians();
    }

    public void evaluate() {
        db().writeRows(averages.targetTable(),
                items().stream()
                        .map(item -> row(item, db().query(queryForItems())))
                        .collect(Collectors.toList()));
    }

    public abstract List<Map<String, String>> items();

    public abstract String queryForItems();

    public abstract String queryForMedians();

    protected String queryPartialForItems() {
        return items().stream()
                .map(item -> this.subQueryForItem(item.get("column")))
                .collect(Collectors.joining());
    }

    private String subQueryForItem(String column) {
        return String.format(", ROUND(AVG(%1$s + 0), 2) as avg_%1$s, " +
                        "MIN(%1$s + 0) as min_%1$s, " +
                        "MAX(%1$s + 0) as max_%1$s",
                column);
    }

    private DbRow row(Map<String, String> item, ResultSet rs) {
        try {
            rs.next();
            DbRow dbRow = new DbRow();
            dbRow.addCell(new DbCell("item", item.get("column")));
            dbRow.addCell(new DbCell("name", item.get("name")));
            dbRow.addCell(new DbCell("unit", item.get("unit")));
            dbRow.addCell(new DbCell("average", rs.getString("avg_" + item.get("column"))));
            dbRow.addCell(new DbCell("minimum", rs.getString("min_" + item.get("column"))));
            dbRow.addCell(new DbCell("maximum", rs.getString("max_" + item.get("column"))));
            dbRow.addCell(new DbCell("median", medians.get(item.get("column"))));
            return dbRow;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private Map<String, String> medians() {
        return items().stream().collect(Collectors.toMap(
                item -> item.get("column"),
                item -> median(item.get("column")
                )));
    }

    private String median(String item) {
        try {
            ResultSet rs = db().query(queryForMedian(item));
            rs.next();
            return rs.getString("medians");
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private String queryForMedian(String item) {
        return String.format(queryForMedians(), item, averages.sourceTable());
    }
}
