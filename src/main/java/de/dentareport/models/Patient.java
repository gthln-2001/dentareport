package de.dentareport.models;

// TODO: TEST?
public class Patient {

    private String patientIndex;
    private String dateOfBirth;
    private String gender;
    private String firstVisit;
    private String lastVisit;

    public Patient(String patientIndex,
                   String dateOfBirth,
                   String gender,
                   String firstVisit,
                   String lastVisit) {
        this.patientIndex = patientIndex;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.firstVisit = firstVisit;
        this.lastVisit = lastVisit;
    }

    public String patientIndex() {
        return patientIndex;
    }

    public String dateOfBirth() {
        return dateOfBirth;
    }

    public String gender() {
        return gender;
    }

    public String firstVisit() {
        return firstVisit;
    }

    public String lastVisit() {
        return lastVisit;
    }
}