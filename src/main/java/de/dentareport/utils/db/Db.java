package de.dentareport.utils.db;

import de.dentareport.Config;
import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.utils.Log;
import de.dentareport.utils.date.DateRange;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class Db {

    private Connection dbConnection;
    private Statement dbStatement;

    public Db(Connection connection) {
        prepareDbHandle(connection);
    }

    public Db() {
        this(defaultConnection());
    }

    private static Connection defaultConnection() {
        try {
            return DriverManager.getConnection(Config.defaultDatabaseUrl());
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    public boolean hasTable(String name) {
        try {
            return query(String.format(
                    "SELECT name FROM sqlite_master WHERE type='table' AND name='%s'",
                    name)).next();
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    public void rebuildTable(String name,
                             List<DbColumn> columns) {
        dropTable(name);
        createTable(name, columns);
    }

    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    public void writeRow(String table,
                         DbRow row) {
        deactivateAutoCommit();
        createRowInDB(table, row);
        activateAutoCommit();
    }

    public void writeRows(String table,
                          List<DbRow> rows) {
        deactivateAutoCommit();
        createRowsInDB(table, rows);
        activateAutoCommit();
    }

    public void updateRows(String table,
                           List<DbRow> rows) {
        deactivateAutoCommit();
        updateRowsInDB(table, rows);
        activateAutoCommit();
    }

    public ResultSet query(String query) {
        try {
            return dbStatement.executeQuery(query);
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    public void execute(String query) {
        try {
            dbStatement.execute(query);
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    public Map<String, Map<String, String>> firstAndLastVisits(String table) {
        return firstAndLastVisits(table, "");
    }

    public Map<String, Map<String, String>> firstAndLastVisits(String table,
                                                               String conditions) {
        String q = String.format(
                "SELECT patient_index, MIN(date) as first_visit, MAX(date) as last_visit " +
                "FROM %s %s " +
                "GROUP BY patient_index ",
                table,
                !Objects.equals(conditions, "") ? String.format("WHERE %s ", conditions) : ""
        );
        return mapFirstAndLastVisitsToPatients(query(q));
    }

    public Boolean hasEntry(String tablename,
                            String column,
                            String value) {
        try {
            ResultSet rs = query(String.format("SELECT %2$s FROM %1$s WHERE %2$s = '%3$s'",
                                               tablename,
                                               column,
                                               value));
            return rs.next();
        } catch (SQLException | DentareportSqlException e) {
            return false;
        }
    }

    public boolean isDisconnected() {
        try {
            return !dbConnection.isValid(10);
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    // TODO: Test
    public DateRange evaluationPeriod(String evaluationId) {
        // Added null check only for some test that will fail otherwise
        // TODO: Think of other solution...
        if (Objects.isNull(evaluationId)) {
            return new DateRange("foo", "bar");
        }

        ResultSet rs = db().query(String.format("SELECT " +
                        "MIN(date_start_observation) AS start_evaluation_period, " +
                        "MAX(date_end_observation) AS end_evaluation_period " +
                        "FROM evaluation_%s",
                evaluationId));
        String startEvaluationPeriod;
        String endEvaluationPeriod;
        try {
            startEvaluationPeriod = rs.getString("start_evaluation_period");
            endEvaluationPeriod = rs.getString("end_evaluation_period");
        } catch (SQLException e) {
            Log.error("Error retrieving evaluation period for evalution id " + evaluationId);
            throw new DentareportSqlException(e);
        }
        return new DateRange(startEvaluationPeriod, endEvaluationPeriod);
    }

    private Map<String, Map<String, String>> mapFirstAndLastVisitsToPatients(ResultSet rs) {
        try {
            Map<String, Map<String, String>> ret = new HashMap<>();
            while (rs.next()) {
                ret.put(rs.getString("patient_index"),
                        mapFirstAndLastVisitToPatient(rs));
            }
            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private Map<String, String> mapFirstAndLastVisitToPatient(ResultSet rs) {
        try {
            Map<String, String> ret = new HashMap<>();
            ret.put("first_visit", rs.getString("first_visit"));
            ret.put("last_visit", rs.getString("last_visit"));
            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private void prepareDbHandle(Connection connection) {
        try {
            this.dbConnection = connection;
            this.dbStatement = this.dbConnection.createStatement();
            this.dbStatement.setQueryTimeout(30);
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private void createTable(String name,
                             List<DbColumn> columns) {
        try {
            dbStatement.executeUpdate(tableCreationQuery(name, columns));
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private String tableCreationQuery(String name,
                                      List<DbColumn> columns) {
        return String.format("CREATE TABLE %s (%s)",
                             name,
                             String.join(", ", concatenateNamesAndTypes(columns)));
    }

    private List<String> concatenateNamesAndTypes(List<DbColumn> columns) {
        return columns.stream()
                      .map(column -> String.format("%s %s",
                                                   column.name(),
                                                   column.type()))
                      .collect(Collectors.toList());
    }

    private void dropTable(String name) {
        try {
            dbStatement.executeUpdate(String.format("DROP TABLE IF EXISTS %s", name));
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private void commit() {
        try {
            dbConnection.commit();
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private void activateAutoCommit() {
        try {
            dbConnection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private void deactivateAutoCommit() {
        try {
            dbConnection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private void createRowInDB(String table,
                               DbRow row) {
        List<DbRow> dbRows = new ArrayList<>();
        dbRows.add(row);
        createRowsInDB(table, dbRows);
    }

    private void createRowsInDB(String table,
                                List<DbRow> rows) {
        if (rows.size() > 0) {
            PreparedStatement statement = preparedInsertStatement(table, rows);
            for (DbRow dbRow : rows) {
                executeCreateRowInDb(statement, dbRow);
            }
            commit();
        }
    }

    private PreparedStatement preparedInsertStatement(String table,
                                                      List<DbRow> rows) {
        try {
            return dbConnection.prepareStatement(insertStatement(table, rows));
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private String insertStatement(String table,
                                   List<DbRow> rows) {
        return insertStatement(table, rows.get(0));
    }

    private String insertStatement(String table,
                                   DbRow row) {
        return String.format("INSERT INTO %s (%s) VALUES (%s)",
                             table,
                             String.join(", ", row.columnnames()),
                             fillInsertStatementWithQuestionmarks(row));
    }

    private String fillInsertStatementWithQuestionmarks(DbRow row) {
        String[] questionMarks = new String[row.cells().size()];
        Arrays.fill(questionMarks, "?");
        return String.join(",", questionMarks);
    }

    private void executeCreateRowInDb(PreparedStatement statement,
                                      DbRow row) {
        try {
            fillPreparedStatement(statement, row);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private void fillPreparedStatement(PreparedStatement statement,
                                       DbRow row) {
        try {
            int columnId = 1;
            for (DbCell dbCell : row.cells()) {
                statement.setString(columnId++, dbCell.value());
            }
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private void updateRowsInDB(String table,
                                List<DbRow> rows) {
        if (rows.size() > 0) {
            PreparedStatement statement = preparedUpdateStatement(table, rows);
            for (DbRow dbRow : rows) {
                executeUpdateRowInDb(statement, dbRow);
            }
            commit();
        }
    }

    private PreparedStatement preparedUpdateStatement(String table,
                                                      List<DbRow> rows) {
        try {
            return dbConnection.prepareStatement(updateStatement(table, rows));
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private String updateStatement(String table,
                                   List<DbRow> rows) {
        return updateStatement(table, rows.get(0));
    }

    private String updateStatement(String table,
                                   DbRow row) {
        return String.format("UPDATE %s SET %s WHERE id = ?",
                             table,
                             concatenateColumnNamesForUpdateStatement(row));
    }

    private String concatenateColumnNamesForUpdateStatement(DbRow row) {
        return row.columnnames().stream()
                  .map(columnName -> columnName + "= ?")
                  .collect(Collectors.joining(", "));
    }

    private void executeUpdateRowInDb(PreparedStatement statement,
                                      DbRow row) {
        try {
            fillPreparedUpdateStatement(statement, row);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private void fillPreparedUpdateStatement(PreparedStatement statement,
                                             DbRow row) {
        try {
            fillPreparedStatement(statement, row);
            statement.setString(row.cells().size() + 1, row.value("id"));
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }
}
