package de.dentareport.repositories;

import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.models.Evidence01Factory;
import de.dentareport.models.Evidence01Interface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class Evidences01Repository {

    public static List<Evidence01Interface> evidences01(String patientIndex) {
        try {
            List<Evidence01Interface> ret = new ArrayList<>();
            ResultSet rs = db().query(String.format("SELECT * FROM evidences_01 " +
                    "WHERE patient_index='%s' " +
                    "ORDER BY date", patientIndex));
            while (rs.next()) {
                ret.add(Evidence01Factory.create(rs));
            }
            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }
}
