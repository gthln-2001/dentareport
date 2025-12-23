package de.dentareport.models;

import de.dentareport.exceptions.DentareportSqlException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientFactory {

    public static Patient create(ResultSet rs) {
        try {
            return new Patient(
                    rs.getString("patient_index"),
                    rs.getString("date_of_birth"),
                    rs.getString("gender"),
                    rs.getString("first_visit"),
                    rs.getString("last_visit")
            );
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }
}
