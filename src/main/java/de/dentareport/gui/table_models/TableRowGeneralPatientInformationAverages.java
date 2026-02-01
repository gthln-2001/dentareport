package de.dentareport.gui.table_models;

public class TableRowGeneralPatientInformationAverages {

    private final String firstName;
    private final String lastName;
    private final String birthDate;
    private final String insurance;

    public TableRowGeneralPatientInformationAverages(String firstName, String lastName,
                                                     String birthDate, String insurance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.insurance = insurance;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getInsurance() {
        return insurance;
    }
}

