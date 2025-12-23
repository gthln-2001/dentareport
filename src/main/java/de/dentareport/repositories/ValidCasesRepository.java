package de.dentareport.repositories;

import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbColumn;
import de.dentareport.utils.db.DbRow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static de.dentareport.utils.db.DbConnection.db;

public class ValidCasesRepository {

    private static List<DbColumn> columns() {
        List<DbColumn> dbColumns = new ArrayList<>();
        dbColumns.add(new DbColumn("id", "integer primary key"));
        dbColumns.add(new DbColumn("evaluation_id", "text"));
        dbColumns.add(new DbColumn("patient_index", "text"));
        dbColumns.add(new DbColumn("tooth", "text"));
        return dbColumns;
    }

    private static Map<String, List<String>> addValidCase(Map<String, List<String>> validCases,
                                                          String patientIndex,
                                                          String tooth) {
        validCases.putIfAbsent(patientIndex, new ArrayList<>());
        validCases.get(patientIndex).add(tooth);
        return validCases;
    }

    // TODO: Refactor! Check tests for this class!
    private static Set<DbRow> validCasesFromDb() {
        try {
            Set<DbRow> validCases = new HashSet<>();
            ResultSet rs9 = db().query("SELECT patient_index, tooth FROM treatments " +
                                       "WHERE billingcode IN ('91D', '504', '5040') AND tooth != '' AND patient_index !='0' ");
            while (rs9.next()) {
                validCases.add(createValidCase(rs9, "9"));
            }
            ResultSet rs7 = db().query("SELECT patient_index, tooth FROM treatments " +
                                       "WHERE billingcode IN ('13A', '13B', '13C', '13D', '13E', '13F', '13G', '205', "
                                       + "'205MK', '207', '207MK', '209', '209MK', '211', '211MK', '215A', '215MK', "
                                       + "'216A', '216MK', '217A', '217MK', '217MK2', '2050', '2070', '2090', '2110', "
                                       + "'218', '2180', '217', '2170', '215', '2150', '216', '2160') "
                                       + "AND tooth != '' AND patient_index !='0' AND source != 'hkp'");
            while (rs7.next()) {
                validCases.add(createValidCase(rs7, "7"));
            }
            return validCases;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private static DbRow createValidCase(ResultSet rs, String evaluationId) throws SQLException {
        DbRow dbRow = new DbRow();
        dbRow.addCell(new DbCell("evaluation_id", evaluationId));
        dbRow.addCell(new DbCell("patient_index", rs.getString("patient_index")));
        dbRow.addCell(new DbCell("tooth", rs.getString("tooth")));
        return dbRow;
    }

    public void identifyValidCases() {
        db().rebuildTable("valid_cases", columns());
        db().writeRows("valid_cases", new ArrayList<>(validCasesFromDb()));
    }

    public Map<String, List<String>> validCases(String evaluationId) {
        try {
            Map<String, List<String>> ret = new HashMap<>();
            ResultSet rs = db().query(
                    String.format(
                            "SELECT patient_index, tooth FROM valid_cases WHERE evaluation_id='%s' ORDER BY patient_index, tooth",
                            evaluationId));
            while (rs.next()) {
                ret = addValidCase(ret, rs.getString("patient_index"), rs.getString("tooth"));
            }
            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }
}
