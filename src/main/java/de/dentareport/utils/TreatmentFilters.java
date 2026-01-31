package de.dentareport.utils;

import com.google.common.collect.ImmutableList;
import de.dentareport.models.CaseData;
import de.dentareport.models.TreatmentInterface;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static de.dentareport.utils.Keys.AT_LEAST_ONE_SURFACE;
import static de.dentareport.utils.Keys.IDENTICAL_SURFACES;

// TODO: Test!
// TODO: TEST?
public class TreatmentFilters {

    public static Predicate<TreatmentInterface> get(String filter, CaseData caseData) {
        switch (filter) {
            case IDENTICAL_SURFACES + "_as_event_start_observation":
                return filterIdenticalSurfacesAsEventStartObservation(caseData);
            case AT_LEAST_ONE_SURFACE + "_as_event_start_observation":
                return filterAtLeastOneSurfaceAsEventStartObservation(caseData);
        }
        Log.error("Unknow filter (get): " + filter);
        throw new IllegalArgumentException("Unknow filter: " + filter);
    }

    public static List<String> requiredColumns(String filter) {
        switch (filter) {
            case IDENTICAL_SURFACES + "_as_event_start_observation":
                return ImmutableList.of("event_start_observation");
            case AT_LEAST_ONE_SURFACE + "_as_event_start_observation":
                return ImmutableList.of("event_start_observation");
        }
        Log.error("Unknow filter (requiredColumns): " + filter);
        throw new IllegalArgumentException("Unknow filter: " + filter);
    }

    public static String shortDoc(String filter) {
        switch (filter) {
            case IDENTICAL_SURFACES + "_as_event_start_observation":
                return "Identische Flächen wie Start Beobachtung";
            case AT_LEAST_ONE_SURFACE + "_as_event_start_observation":
                return "min eine Fläche wie Start Beobachtung";
        }
        Log.error("Unknow filter (shortDoc): " + filter);
        throw new IllegalArgumentException("Unknow filter: " + filter);
    }

    private static Predicate<TreatmentInterface> filterIdenticalSurfacesAsEventStartObservation(CaseData caseData) {
        List<String> surfaces = caseData.event("event_start_observation").surfaces();
        return t -> t.surfaces().equals(surfaces);
    }

    private static Predicate<TreatmentInterface> filterAtLeastOneSurfaceAsEventStartObservation(CaseData caseData) {
        List<String> surfaces = caseData.event("event_start_observation").surfaces();
        return t -> !Collections.disjoint(t.surfaces(), surfaces);
    }
}
