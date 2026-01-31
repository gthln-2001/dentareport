package de.dentareport.repositories;

import de.dentareport.models.Patient;
import de.dentareport.models.PatientFactory;

import java.sql.ResultSet;

import static de.dentareport.utils.db.DbConnection.db;

// TODO: TEST?
public class PatientsRepository {

    public static Patient patient(String patientIndex) {
        ResultSet rs = db().query(String.format("SELECT * FROM patients WHERE patient_index='%s'",
                patientIndex));
        return PatientFactory.create(rs);
    }
}
