package de.dentareport.evaluations.office;

import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbRow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static de.dentareport.utils.db.DbConnection.db;

public interface ItemAverages extends Item {

    @Override
    default void evaluate() {
        Map<String, String> result = results();

        DbRow row = new DbRow();
        row.addCell(new DbCell("item", item()));
        row.addCell(new DbCell("name", name()));
        row.addCell(new DbCell("unit", unit()));
        row.addCell(new DbCell("minimum", result.get("minimum")));
        row.addCell(new DbCell("maximum", result.get("maximum")));
        row.addCell(new DbCell("average", result.get("average")));
        row.addCell(new DbCell("median", result.get("median")));

        db().writeRow(Keys.DB_TABLENAME_OFFICE_EVALUATION_AVERAGES, row);
    }

    String query();

    String queryMedian();

    default Map<String, String> results() {
        try {
            ResultSet rs = db().query(query());
            rs.next();

            Map<String, String> ret = new HashMap<>();
            ret.put("average", rs.getString("average"));
            ret.put("minimum", rs.getString("minimum"));
            ret.put("maximum", rs.getString("maximum"));
            ret.put("median", median());

            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    default String median() {
        try {
            ResultSet rs = db().query(queryMedian());
            rs.next();
            return rs.getString("median");
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }
}
