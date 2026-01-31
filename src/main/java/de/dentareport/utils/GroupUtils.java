package de.dentareport.utils;

import de.dentareport.exceptions.DentareportSqlException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class GroupUtils {

    public static String valuesForColumn(String table,
                                         String column,
                                         String sortColumn) {
        ResultSet rs = distinctValues(table, column, sortColumn);
        return String.join("; ", extractColumnToList(column, rs));
    }

    private static ResultSet distinctValues(String table,
                                            String column,
                                            String sortColumn) {
        return db().query(String.format("SELECT DISTINCT %s FROM %s WHERE %s != '%s' ORDER BY %s",
                column,
                table,
                column,
                Keys.NO_DATA,
                sortColumn));
    }

    private static List<String> extractColumnToList(String column, ResultSet rs) {
        try {
            List<String> ret = new ArrayList<>();
            while (rs.next()) {
                ret.add(rs.getString(column));
            }
            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }
}
