package de.dentareport.models;

import de.dentareport.exceptions.DentareportSqlException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TreatmentFactory {

    public static Treatment create(ResultSet rs) {
        try {
            return new Treatment(
                    rs.getString("date"),
                    rs.getString("tooth"),
                    rs.getString("billingcode"),
                    rs.getString("surfaces"),
                    rs.getString("handler"),
                    rs.getString("insurance"),
                    rs.getString("comment"),
                    rs.getString("source")
            );
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }
}
