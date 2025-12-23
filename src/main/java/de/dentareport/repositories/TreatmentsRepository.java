package de.dentareport.repositories;

import de.dentareport.exceptions.DentareportSqlException;
import de.dentareport.models.TreatmentFactory;
import de.dentareport.models.TreatmentInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static de.dentareport.utils.db.DbConnection.db;

public class TreatmentsRepository {

    public static List<TreatmentInterface> treatments(String patientIndex,
                                                      Set<String> billingcodes) {
        try {
            List<TreatmentInterface> ret = new ArrayList<>();
            ResultSet rs = db().query(String.format("SELECT * FROM treatments " +
                                                    "WHERE patient_index='%s' AND billingcode in (%s) " +
                                                    "ORDER BY date, sequence",
                                                    patientIndex,
                                                    prepareBillingcodes(billingcodes)));
            while (rs.next()) {
                ret.add(TreatmentFactory.create(rs));
            }
            return ret;
        } catch (SQLException e) {
            throw new DentareportSqlException(e);
        }
    }

    private static String prepareBillingcodes(Set<String> billingcodes) {
        return String.format("'%s'", String.join("', '", billingcodes));
    }
}
