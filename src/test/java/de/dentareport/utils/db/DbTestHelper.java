package de.dentareport.utils.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// TODO: TEST?
public class DbTestHelper {

    public static void createTable(Connection connection, String tableName) throws SQLException {
        String query = String.format("CREATE TABLE %s (column text)", tableName);
        connection.createStatement().executeUpdate(query);
    }

    public static DbRow createTestRow(String value) {
        DbRow dbRow = new DbRow();
        dbRow.addCell(new DbCell("column", value));
        return dbRow;
    }

    public static void createTestTable(Connection connection) throws SQLException {
        connection.createStatement().executeUpdate("CREATE TABLE test (column text)");
    }

    public static boolean tableExists(Connection connection, String tableName) throws SQLException {
        String query = String.format("SELECT name FROM sqlite_master WHERE type='table' AND name='%s'", tableName);
        ResultSet rs = connection.createStatement().executeQuery(query);
        return rs.next();
    }

    public static boolean tableIsEmpty(Connection connection, String tableName) throws SQLException {
        String query = String.format("SELECT COUNT(*) as count_rows FROM %s", tableName);
        ResultSet rs = connection.createStatement().executeQuery(query);
        rs.next();
        return rs.getInt("count_rows") == 0;
    }

    public static boolean tableHasColumn(Connection connection, String tableName, String columnName) throws SQLException {
        String query = String.format("PRAGMA table_info(%s)", tableName);
        ResultSet rs = connection.createStatement().executeQuery(query);
        while (rs.next()) {
            if (Objects.equals(rs.getString("name"), columnName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean columnIsType(Connection connection, String tableName, String columnName, String columnType) throws SQLException {
        String query = String.format("PRAGMA table_info(%s)", tableName);
        ResultSet rs = connection.createStatement().executeQuery(query);
        while (rs.next()) {
            if (Objects.equals(rs.getString("name"), columnName) && Objects.equals(rs.getString("type"), columnType)) {
                return true;
            }
        }
        return false;
    }

    public static boolean columnIsPrimaryKey(Connection connection, String tableName, String columnName) throws SQLException {
        ResultSet rs = connection.getMetaData().getPrimaryKeys(null, null, tableName);
        while (rs.next()) {
            if (Objects.equals(rs.getString("COLUMN_NAME"), columnName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean tableContainsRow(Connection connection, String tableName, DbRow dbRow) throws SQLException {
        List<String> queryData = dbRow.cells().stream()
                .map(cell -> String.format("%s='%s'", cell.column(), cell.value()))
                .collect(Collectors.toList());

        String query = String.format("SELECT COUNT(*) AS count_rows FROM %s WHERE %s", tableName, String.join(" AND ", queryData));
        ResultSet rs = connection.createStatement().executeQuery(query);
        rs.next();
        return rs.getInt("count_rows") == 1;
    }

    public static void createTestTableForFirstAndLastVisit(Db db, Connection connection) throws Exception {
        connection.createStatement().executeUpdate("CREATE TABLE test (patient_index text, date text, foo text)");

        List<DbRow> dbRows = new ArrayList<>();

        DbRow row1 = new DbRow();
        row1.addCell(new DbCell("patient_index", "42"));
        row1.addCell(new DbCell("date", "2010-10-01"));
        row1.addCell(new DbCell("foo", ""));
        dbRows.add(row1);

        DbRow row2 = new DbRow();
        row2.addCell(new DbCell("patient_index", "42"));
        row2.addCell(new DbCell("date", "2010-11-01"));
        row2.addCell(new DbCell("foo", "bar"));
        dbRows.add(row2);

        DbRow row3 = new DbRow();
        row3.addCell(new DbCell("patient_index", "42"));
        row3.addCell(new DbCell("date", "2010-12-01"));
        row3.addCell(new DbCell("foo", ""));
        dbRows.add(row3);

        DbRow row4 = new DbRow();
        row4.addCell(new DbCell("patient_index", "23"));
        row4.addCell(new DbCell("date", "2010-11-01"));
        row4.addCell(new DbCell("foo", ""));
        dbRows.add(row4);

        db.writeRows("test", dbRows);
    }
}
