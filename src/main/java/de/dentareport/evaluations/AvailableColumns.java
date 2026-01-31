package de.dentareport.evaluations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.dentareport.utils.Keys;
import de.dentareport.utils.Log;

import java.util.*;
import java.util.stream.Collectors;

import static de.dentareport.utils.Keys.*;

// TODO: TEST?
public class AvailableColumns {

    private static final Map<String, List<String>> columns = ImmutableMap.of(
            "patient", ImmutableList.of(
                    "patient_index",
                    "tooth",
                    "toothtype",
                    "jaw",
                    "date_of_birth",
                    "gender",
                    "insurance",
                    "date_first_visit",
                    "date_last_visit",
                    "case_count_for_patient",
                    "case_count_for_patient_upper_jaw",
                    "case_count_for_patient_lower_jaw",
                    "date__of__treatment_on_dentition_position_first_with_" + SPLINT,
                    "billingposition__of__treatment_on_dentition_position_first_with_" + SPLINT,
                    "count_treatments__on__dentition__with__" + FLUORIDIERUNG + "__from__date_start_observation__until__date_end_observation",
                    "count_treatments__on__dentition__with__" + FLUORIDIERUNG + "__from__date_start_observation__until__date_end_observation__per__half_year",
                    "count_treatments__on__dentition__with__" + MUNDGESUNDHEITSAUFKLAERUNG + "__from__date_start_observation__until__date_end_observation",
                    "count_treatments__on__dentition__with__" + MUNDGESUNDHEITSAUFKLAERUNG + "__from__date_start_observation__until__date_end_observation__per__half_year",
                    "count_treatments__on__dentition__with__" + MUNDHYGIENESTATUS + "__from__date_start_observation__until__date_end_observation",
                    "count_treatments__on__dentition__with__" + MUNDHYGIENESTATUS + "__from__date_start_observation__until__date_end_observation__per__half_year",
                    "count_treatments__on__dentition__with__" + SPLINT + "__from__date_start_observation__until__date_end_observation",
                    "count_treatments__on__dentition__with__" + FUELLUNG + "__before__date_start_observation",
                    "count_filling_surfaces__on__dentition__before__date_start_observation",
                    "count_treatments__on__dentition__with__" + FUELLUNG + "__before__date_start_observation__per__year",
                    "count_filling_surfaces__on__dentition__before__date_start_observation__per__year",
                    "count_treatments__on__dentition__with__" + FUELLUNG + "__from__date_start_observation__until__date_end_observation",
                    "count_filling_surfaces__on__dentition__from__date_start_observation__until__date_end_observation",
                    "count_treatments__on__dentition__with__" + FUELLUNG + "__from__date_start_observation__until__date_end_observation__per__year",
                    "count_filling_surfaces__on__dentition__from__date_start_observation__until__date_end_observation__per__year",
                    "date__of__evidence_01_position_last",
                    "dt__of__evidence_01_position_last",
                    "mt__of__evidence_01_position_last",
                    "ft__of__evidence_01_position_last",
                    "dft__of__evidence_01_position_last",
                    "dmft__of__evidence_01_position_last",
                    "count_evidences_01__from__date_start_observation__until__date_end_observation",
                    "count_evidences_01__from__date_start_observation__until__date_end_observation__per__half_year"
            ),
            "tooth", ImmutableList.of(
                    "date_start_observation",
                    "year__of__event_start_observation",
                    "date_end_observation",
                    DATE_END_SEARCH_PERIOD,
                    "count_days__from__date_start_observation__until__date_end_observation",
                    "count_days__from__date_start_observation__until__date_end_observation__format__epi",
                    "count_days__from__date_first_visit__before__date_start_observation",
                    "censored__of__event_end_observation",
                    "billingposition__of__event_end_observation",
                    "age__at__event_start_observation",
                    "age_days__at__event_start_observation",
                    "groups__of__age_at_event_start_observation",
                    "billingcode__of__event_start_observation",
                    "handler__of__event_start_observation",
                    "material__of__event_start_observation",
                    "surfaces__of__event_start_observation",
                    "cofferdam__at__event_start_observation",
                    "date__of__evidence_01_position_last_before_date_start_observation",
                    "dmft__of__evidence_01_position_last_before_date_start_observation",
                    "groups__of__dmft_of_evidence_01_position_last_before_date_start_observation",
                    "dt__of__evidence_01_position_last_before_date_start_observation",
                    "groups__of__dt_of_evidence_01_position_last_before_date_start_observation",
                    "mt__of__evidence_01_position_last_before_date_start_observation",
                    "groups__of__mt_of_evidence_01_position_last_before_date_start_observation",
                    "ft__of__evidence_01_position_last_before_date_start_observation",
                    "groups__of__ft_of_evidence_01_position_last_before_date_start_observation",
                    "toothcount__of__evidence_01_position_last_before_date_start_observation",
                    "groups__of__toothcount_of_evidence_01_position_last_before_date_start_observation",
                    "toothcount_in_jaw__of__evidence_01_position_last_before_date_start_observation",
                    "groups__of__toothcount_in_jaw_of_evidence_01_position_last_before_date_start_observation",
                    "date__of__evidence_01_position_last_until_date_start_observation",
                    "status__is__" + IMPLANT + "__of__evidence_01_position_last_until_date_start_observation",
                    "date__of__evidence_01_position_first_after_date_start_observation",
                    "endstaendigkeit__of__evidence_01_position_first_after_date_start_observation",
                    "toothcontacts__of__evidence_01_position_first_after_date_start_observation",
                    "toothcount_in_jaw__of__evidence_01_position_first_after_date_start_observation",
                    "groups__of__toothcount_in_jaw_of_evidence_01_position_first_after_date_start_observation",
                    "count_filling_surfaces__of__evidence_01_position_last_before_date_start_observation",
                    "count_filling_surfaces__of__evidence_01_position_first_from_date_start_observation",
                    "count_filling_surfaces__of__evidence_01_position_last",
                    "caries_surfaces__specification__" + CARIES_NOT_TO_TREAT + "__of__evidence_01_position_first_from_date_start_observation",
                    "count_caries_surfaces__specification__" + CARIES_NOT_TO_TREAT + "__of__evidence_01_position_first_from_date_start_observation",
                    "caries_surfaces__specification__" + CARIES_NOT_TO_TREAT + "__of__evidence_01_position_last_before_date_start_observation",
                    "count_caries_surfaces__specification__" + CARIES_NOT_TO_TREAT + "__of__evidence_01_position_last_before_date_start_observation",
                    "caries_surfaces__specification__" + CARIES_NOT_TO_TREAT + "__of__evidence_01_position_last",
                    "count_caries_surfaces__specification__" + CARIES_NOT_TO_TREAT + "__of__evidence_01_position_last",
                    "caries_surfaces__specification__" + CARIES_TO_TREAT + "__of__evidence_01_position_first_from_date_start_observation",
                    "count_caries_surfaces__specification__" + CARIES_TO_TREAT + "__of__evidence_01_position_first_from_date_start_observation",
                    "caries_surfaces__specification__" + CARIES_TO_TREAT + "__of__evidence_01_position_last_before_date_start_observation",
                    "count_caries_surfaces__specification__" + CARIES_TO_TREAT + "__of__evidence_01_position_last_before_date_start_observation",
                    "caries_surfaces__specification__" + CARIES_TO_TREAT + "__of__evidence_01_position_last",
                    "count_caries_surfaces__specification__" + CARIES_TO_TREAT + "__of__evidence_01_position_last",
                    "caries_specification__surface__" + SURFACE_CERVIKAL + "__of__evidence_01_position_last_before_date_start_observation",
                    "caries_specification__surface__" + SURFACE_DISTAL + "__of__evidence_01_position_last_before_date_start_observation",
                    "caries_specification__surface__" + SURFACE_LINGUAL + "__of__evidence_01_position_last_before_date_start_observation",
                    "caries_specification__surface__" + SURFACE_MESIAL + "__of__evidence_01_position_last_before_date_start_observation",
                    "caries_specification__surface__" + SURFACE_OKKLUSAL + "__of__evidence_01_position_last_before_date_start_observation",
                    "caries_specification__surface__" + SURFACE_VESTIBULAER + "__of__evidence_01_position_last_before_date_start_observation",
                    "date__of__treatment_position_first_with_" + FUELLUNG + "_after_date_start_observation",
                    "surfaces__of__treatment_position_first_with_" + FUELLUNG + "_after_date_start_observation",
                    "censored__of__treatment_position_first_with_" + FUELLUNG + "_after_date_start_observation",
                    "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + FUELLUNG + "_after_date_start_observation__or_until__date_end_search_period",
                    "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + FUELLUNG + "_after_date_start_observation__or_until__date_end_search_period__format__epi",
                    "date__of__treatment_position_first_with_" + FUELLUNG + "_filter_only_" + IDENTICAL_SURFACES + "_as_event_start_observation_after_date_start_observation",
                    "censored__of__treatment_position_first_with_" + FUELLUNG + "_filter_only_" + IDENTICAL_SURFACES + "_as_event_start_observation_after_date_start_observation",
                    "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + FUELLUNG + "_filter_only_" + IDENTICAL_SURFACES + "_as_event_start_observation_after_date_start_observation__or_until__date_end_search_period",
                    "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + FUELLUNG + "_filter_only_" + IDENTICAL_SURFACES + "_as_event_start_observation_after_date_start_observation__or_until__date_end_search_period__format__epi",
                    "date__of__treatment_position_first_with_" + FUELLUNG + "_filter_only_" + AT_LEAST_ONE_SURFACE + "_as_event_start_observation_after_date_start_observation",
                    "censored__of__treatment_position_first_with_" + FUELLUNG + "_filter_only_" + AT_LEAST_ONE_SURFACE + "_as_event_start_observation_after_date_start_observation",
                    "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + FUELLUNG + "_filter_only_" + AT_LEAST_ONE_SURFACE + "_as_event_start_observation_after_date_start_observation__or_until__date_end_search_period",
                    "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + FUELLUNG + "_filter_only_" + AT_LEAST_ONE_SURFACE + "_as_event_start_observation_after_date_start_observation__or_until__date_end_search_period__format__epi",
                    "date__of__treatment_position_first_with_" + CROWN + "_after_date_start_observation",
                    "censored__of__treatment_position_first_with_" + CROWN + "_after_date_start_observation",
                    "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + CROWN + "_after_date_start_observation__or_until__date_end_search_period",
                    "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + CROWN + "_after_date_start_observation__or_until__date_end_search_period__format__epi",
                    "date__of__treatment_position_first_with_" + TELESCOPIC_CROWN + "_after_date_start_observation",
                    "censored__of__treatment_position_first_with_" + TELESCOPIC_CROWN + "_after_date_start_observation",
                    "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + TELESCOPIC_CROWN + "_after_date_start_observation__or_until__date_end_search_period",
                    "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + TELESCOPIC_CROWN + "_after_date_start_observation__or_until__date_end_search_period__format__epi",
                    "date__of__treatment_position_first_with_" + BRUECKENANKER + "_after_date_start_observation",
                    "censored__of__treatment_position_first_with_" + BRUECKENANKER + "_after_date_start_observation",
                    "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + BRUECKENANKER + "_after_date_start_observation__or_until__date_end_search_period",
                    "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + BRUECKENANKER + "_after_date_start_observation__or_until__date_end_search_period__format__epi",
                    "date__of__treatment_position_first_with_" + VITE_TREP_WK + "_after_date_start_observation",
                    "censored__of__treatment_position_first_with_" + VITE_TREP_WK + "_after_date_start_observation",
                    "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + VITE_TREP_WK + "_after_date_start_observation__or_until__date_end_search_period",
                    "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + VITE_TREP_WK + "_after_date_start_observation__or_until__date_end_search_period__format__epi",
                    "date__of__treatment_position_first_with_" + EXTRACTION + "_after_date_start_observation",
                    "censored__of__treatment_position_first_with_" + EXTRACTION + "_after_date_start_observation",
                    "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + EXTRACTION + "_after_date_start_observation__or_until__date_end_search_period",
                    "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + EXTRACTION + "_after_date_start_observation__or_until__date_end_search_period__format__epi",
                    "date__of__treatment_position_first_with_" + WURZELSTIFT + "_after_date_start_observation",
                    "censored__of__treatment_position_first_with_" + WURZELSTIFT + "_after_date_start_observation",
                    "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + WURZELSTIFT + "_after_date_start_observation__or_until__date_end_search_period",
                    "billingcode__of__treatment_position_first_with_" + WURZELSTIFT + "_after_date_start_observation",
                    "date__of__treatment_position_first_with_" + ERNEUERUNG_WURZELSTIFT + "_after_date_start_observation",
                    "censored__of__treatment_position_first_with_" + ERNEUERUNG_WURZELSTIFT + "_after_date_start_observation",
                    "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + ERNEUERUNG_WURZELSTIFT + "_after_date_start_observation__or_until__date_end_search_period",
                    "billingcode__of__treatment_position_first_with_" + ERNEUERUNG_WURZELSTIFT + "_after_date_start_observation",
                    "date__of__treatment_position_first_with_" + REZEMENTIERUNG + "_after_date_start_observation",
                    "censored__of__treatment_position_first_with_" + REZEMENTIERUNG + "_after_date_start_observation",
                    "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + REZEMENTIERUNG + "_after_date_start_observation__or_until__date_end_search_period",
                    "billingcode__of__treatment_position_first_with_" + REZEMENTIERUNG + "_after_date_start_observation",
                    "date__of__treatment_position_first_with_" + XRAY_EINZELBILD + "_from_date_start_observation",
                    "date__of__treatment_position_first_with_" + XRAY_OPG + "_from_date_start_observation",
                    "date__of__event_of_interest_after_date_start_observation_position_1",
                    "censored__of__event_of_interest_after_date_start_observation_position_1",
                    "count_days__from__date_start_observation__until__date_of_event_of_interest_after_date_start_observation_position_1__or_until__date_end_search_period",
                    "billingcode__of__event_of_interest_after_date_start_observation_position_1",
                    "billingposition__of__event_of_interest_after_date_start_observation_position_1",
                    "date__of__event_of_interest_after_date_start_observation_position_2",
                    "censored__of__event_of_interest_after_date_start_observation_position_2",
                    "count_days__from__date_start_observation__until__date_of_event_of_interest_after_date_start_observation_position_2__or_until__date_end_search_period",
                    "billingcode__of__event_of_interest_after_date_start_observation_position_2",
                    "billingposition__of__event_of_interest_after_date_start_observation_position_2",
                    "date__of__event_of_interest_after_date_start_observation_position_3",
                    "censored__of__event_of_interest_after_date_start_observation_position_3",
                    "count_days__from__date_start_observation__until__date_of_event_of_interest_after_date_start_observation_position_3__or_until__date_end_search_period",
                    "billingcode__of__event_of_interest_after_date_start_observation_position_3",
                    "billingposition__of__event_of_interest_after_date_start_observation_position_3"
            ),
            "errorcodes", ImmutableList.of(
                    "errorcodes"
            ),
            "replaced_with_shortcut", ImmutableList.of(
                    "date__of__event_end_observation",
                    "date__of__event_start_observation"
            ),
            "events", ImmutableList.of(
                    "event_end_observation",
                    "events_of_interest__after__date_start_observation",
                    "event_of_interest__after__date_start_observation__position__1",
                    "event_of_interest__after__date_start_observation__position__2",
                    "event_of_interest__after__date_start_observation__position__3",
                    "event_start_observation",
                    "evidence_01__position__first__after__date_start_observation",
                    "evidence_01__position__first__from__date_start_observation",
                    "evidence_01__position__last",
                    "evidence_01__position__last__before__date_start_observation",
                    "evidence_01__position__last__until__date_start_observation",
                    "treatment__on__dentition__position__first__with__" + SPLINT,
                    "treatment__position__first__with__" + BRUECKENANKER + "__after__date_start_observation",
                    "treatment__position__first__with__" + ERNEUERUNG_WURZELSTIFT + "__after__date_start_observation",
                    "treatment__position__first__with__" + EXTRACTION + "__after__date_start_observation",
                    "treatment__position__first__with__" + FUELLUNG + "__after__date_start_observation",
                    "treatment__position__first__with__" + FUELLUNG + "__filter_only__" + IDENTICAL_SURFACES + "_as_event_start_observation__after__date_start_observation",
                    "treatment__position__first__with__" + FUELLUNG + "__filter_only__" + AT_LEAST_ONE_SURFACE + "_as_event_start_observation__after__date_start_observation",
                    "treatment__position__first__with__" + CROWN + "__after__date_start_observation",
                    "treatment__position__first__with__" + REZEMENTIERUNG + "__after__date_start_observation",
                    "treatment__position__first__with__" + TELESCOPIC_CROWN + "__after__date_start_observation",
                    "treatment__position__first__with__" + VITE_TREP_WK + "__after__date_start_observation",
                    "treatment__position__first__with__" + WURZELSTIFT + "__after__date_start_observation",
                    "treatment__position__first__with__" + XRAY_EINZELBILD + "__from__date_start_observation",
                    "treatment__position__first__with__" + XRAY_OPG + "__from__date_start_observation"
            )
    );

    public Map<String, List<String>> columnGroups() {
        return columns;
    }

    public Set<String> columns() {
        Set<String> ret = new HashSet<>();
        for (String group : columns.keySet()) {
            ret.addAll(columns.get(group));
        }
        return ret;
    }

    public boolean has(String column) {
        return columns().contains(column);
    }

    public String relatedColumn(String original) {
        List<String> ret = columns().stream()
                .filter(column -> compareColumnnames(original, column))
                .collect(Collectors.toList());
        allowOnlyResultWithExactlyOneMatch(original, ret);
        return ret.get(0);
    }

    public String index(String columnName) {
        int index = 1;
        for (List<String> group : columnGroups().values()) {
            if (!group.contains(columnName)) {
                index++;
                continue;
            }
            return String.format(
                    "%s.%s",
                    index,
                    (group.indexOf(columnName) + 1)
            );
        }
        throw new IllegalArgumentException(columnName);
    }

    public String group(String column) {
        for (Map.Entry<String, List<String>> entry : columnGroups().entrySet()) {
            if (entry.getValue().contains(column)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException(column);
    }

    private boolean compareColumnnames(String original,
                                       String column) {
        return Objects.equals(column.replace("_", ""), original.replace("_", ""));
    }

    private void allowOnlyResultWithExactlyOneMatch(String original,
                                                    List<String> ret) {
        if (ret.size() != 1) {
            Log.error("Invalid column name: " + original);
            throw new IllegalArgumentException(original);
        }
    }
}
