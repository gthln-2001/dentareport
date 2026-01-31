package de.dentareport.imports.dampsoft.dampsoft_files;

//import de.dentareport.gui.ProgressUpdate;
import de.dentareport.gui.util.FileProgressListener;
import de.dentareport.utils.Keys;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static de.dentareport.utils.db.DbConnection.db;
import static de.dentareport.utils.string.DateStringUtils.isAfter;
import static de.dentareport.utils.string.DateStringUtils.isBefore;

// TODO: TEST?
public class FirstAndLastVisit implements DampsoftFile {

    private static Map<String, Map<String, String>> firstAndLastVisits = new HashMap<>();

    public static String conditionsTreatments() {
        return "type IN ('KK', 'AE', 'AF', 'AX', 'BG', 'GK', 'LF', 'LG', 'LK', 'LP', 'PI', 'RB') " +
                "OR source='hkp'";
    }

    public static String firstVisit(String patientIndex) {
        return visits(patientIndex).get("first_visit");
    }

    public static String lastVisit(String patientIndex) {
        return visits(patientIndex).get("last_visit");
    }

    public static String last01(String patientIndex) {
        return visits(patientIndex).get("last_01");
    }

    @Override
    public void importFile(FileProgressListener listener) {
        // TODO: FIX progress
//        ProgressUpdate.init(2, Keys.GUI_TEXT_IMPORTING_FIRST_AND_LAST_VISIT);
        updateVisitsAnd01(db().firstAndLastVisits("evidences_01"));
//        ProgressUpdate.tick();
        updateVisits(db().firstAndLastVisits("treatments", conditionsTreatments()));
//        ProgressUpdate.tick();
    }

    @Override
    public Boolean isMissing() {
        return false;
    }

    @Override
    public Boolean isRealFile() {
        return false;
    }

    private static Map<String, String> visits(String patientIndex) {
        return firstAndLastVisits.getOrDefault(patientIndex, defaultVisits());
    }

    private static Map<String, String> defaultVisits() {
        Map<String, String> ret = new HashMap<>();
        ret.put("first_visit", "");
        ret.put("last_visit", "");
        ret.put("last_01", "");
        return ret;
    }

    private void updateVisitsAnd01(Map<String, Map<String, String>> visits) {
        updateVisits(visits);
        for (String patientIndex : visits.keySet()) {
            if (isLast01Empty(patientIndex)
                    || isNewLast01AfterSavedLast01(visits, patientIndex)) {
                saveNewLast01(visits, patientIndex);
            }
        }
    }

    private void updateVisits(Map<String, Map<String, String>> visits) {
        for (String patientIndex : visits.keySet()) {
            if (isFirstOccurrenceOfPatient(patientIndex)) {
                firstAndLastVisits.put(patientIndex, defaultVisits());
            }
            if (isFirstVisitEmpty(patientIndex)
                    || isNewFirstVisitBeforeSavedFirstVisit(visits, patientIndex)) {
                saveNewFirstVisit(visits, patientIndex);
            }
            if (isLastVisitEmpty(patientIndex)
                    || isNewLastVisitAfterSavedLastVisit(visits, patientIndex)) {
                saveNewLastVisit(visits, patientIndex);
            }
        }
    }

    private boolean isFirstOccurrenceOfPatient(String patientIndex) {
        return !firstAndLastVisits.containsKey(patientIndex);
    }

    private boolean isFirstVisitEmpty(String patientIndex) {
        return Objects.equals(firstAndLastVisits.get(patientIndex).get("first_visit"), "");
    }

    private boolean isNewFirstVisitBeforeSavedFirstVisit(Map<String, Map<String, String>> visits,
                                                         String patientIndex) {
        return isBefore(
                visits.get(patientIndex).get("first_visit"),
                firstAndLastVisits.get(patientIndex).get("first_visit"));
    }

    private void saveNewFirstVisit(Map<String, Map<String, String>> visits,
                                   String patientIndex) {
        firstAndLastVisits.get(patientIndex)
                .put(
                        "first_visit",
                        visits.get(patientIndex).get("first_visit"));
    }

    private boolean isLastVisitEmpty(String patientIndex) {
        return Objects.equals(firstAndLastVisits.get(patientIndex).get("last_visit"), "");
    }

    private boolean isNewLastVisitAfterSavedLastVisit(Map<String, Map<String, String>> visits,
                                                      String patientIndex) {
        return isAfter(
                visits.get(patientIndex).get("last_visit"),
                firstAndLastVisits.get(patientIndex).get("last_visit"));
    }

    private void saveNewLastVisit(Map<String, Map<String, String>> visits,
                                  String patientIndex) {
        firstAndLastVisits.get(patientIndex)
                .put(
                        "last_visit",
                        visits.get(patientIndex).get("last_visit"));
    }

    private boolean isLast01Empty(String patientIndex) {
        return Objects.equals(firstAndLastVisits.get(patientIndex).get("last_01"), "");
    }

    private boolean isNewLast01AfterSavedLast01(Map<String, Map<String, String>> visits,
                                                String patientIndex) {
        return isAfter(
                visits.get(patientIndex).get("last_01"),
                firstAndLastVisits.get(patientIndex).get("last_01"));
    }

    private void saveNewLast01(Map<String, Map<String, String>> visits,
                               String patientIndex) {
        firstAndLastVisits.get(patientIndex)
                .put(
                        "last_01",
                        visits.get(patientIndex).get("last_visit"));
    }
}
