package de.dentareport.evaluations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.dentareport.models.CaseData;
import de.dentareport.models.EventInterface;
import de.dentareport.models.RawData;
import de.dentareport.models.TreatmentInterface;
import de.dentareport.utils.Keys;
import de.dentareport.utils.Treatments;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static de.dentareport.utils.Keys.CROWN;
import static de.dentareport.utils.Keys.IMPLANT;

// TODO: TEST?
public class Evaluation9 extends Evaluation {

    public Evaluation9() {
        super();
    }

    @Override
    public String evaluationName() {
        return "teleskopkronen";
    }

    @Override
    public TreatmentInterface eventStartObservation(RawData rawData, CaseData caseData) {
        return new Treatments(rawData, caseData).withBillingpositions(requiredBillingPositionsForEventStartObservation()).first();
    }

    @Override
    public String longDocumentationForStartObservation() {
        return String.format("Erstes Auftreten von ~~%s~~", Keys.TELESCOPIC_CROWN);
    }

    @Override
    public Set<String> requiredBillingPositionsForEventStartObservation() {
        return ImmutableSet.of(Keys.TELESCOPIC_CROWN);
    }

    @Override
    public Set<String> requiredEvidenceTypesForEventStartObservation() {
        return ImmutableSet.of();
    }

    @Override
    public EventInterface eventEndObservation(RawData rawData, CaseData caseData) {
        return new Treatments(rawData, caseData).after(caseData.string("date__of__event_start_observation"))
                .withBillingpositions(requiredBillingPositionsForEventEndObservation())
                .first();
    }

    @Override
    public String longDocumentationForEndObservation() {
        return String.format(
                "Erstes Auftreten von ~~%s~~, ~~%s~~, ~~%s~~, ~~%s~~, ~~%s~~ oder ~~%s~~ " +
                        "nach Start Beobachtung",
                CROWN,
                Keys.TELESCOPIC_CROWN,
                Keys.BRUECKENANKER,
                Keys.VITE_TREP_WK,
                Keys.EXTRACTION,
                Keys.OSTEOTOMIE);
    }

    @Override
    public Set<String> requiredBillingPositionsForEventEndObservation() {
        return ImmutableSet.of(
                Keys.BRUECKENANKER,
                Keys.EXTRACTION,
                CROWN,
                Keys.OSTEOTOMIE,
                Keys.TELESCOPIC_CROWN,
                Keys.VITE_TREP_WK
        );
    }

    @Override
    public Set<String> requiredEvidenceTypesForEventEndObservation() {
        return ImmutableSet.of();
    }

    @Override
    public List<String> dependenciesForEventEndObservation() {
        return ImmutableList.of("date__of__event_start_observation");
    }

    @Override
    public Set<String> requiredBillingPositionsForEventsOfInterest(Map<String, String> options) {
        return ImmutableSet.of(
                Keys.ERNEUERUNG_WURZELSTIFT,
                Keys.EXTRACTION,
                Keys.FUELLUNG,
                CROWN,
                Keys.OSTEOTOMIE,
                Keys.REZEMENTIERUNG,
                Keys.VITE_TREP_WK,
                Keys.WSR,
                Keys.WURZELSTIFT
        );
    }

    @Override
    protected Set<String> evaluationColumns() {
        return ImmutableSet.of(
                "age__at__event_start_observation",
                "age_days__at__event_start_observation",
                "billingcode__of__event_start_observation",
                "billingcode__of__event_of_interest_after_date_start_observation_position_1",
                "billingcode__of__event_of_interest_after_date_start_observation_position_2",
                "billingcode__of__event_of_interest_after_date_start_observation_position_3",
                "billingcode__of__treatment_position_first_with_" + Keys.ERNEUERUNG_WURZELSTIFT + "_after_date_start_observation",
                "billingcode__of__treatment_position_first_with_" + Keys.REZEMENTIERUNG + "_after_date_start_observation",
                "billingcode__of__treatment_position_first_with_" + Keys.WURZELSTIFT + "_after_date_start_observation",
                "billingposition__of__event_end_observation",
                "billingposition__of__event_of_interest_after_date_start_observation_position_1",
                "billingposition__of__event_of_interest_after_date_start_observation_position_2",
                "billingposition__of__event_of_interest_after_date_start_observation_position_3",
                "case_count_for_patient",
                "case_count_for_patient_upper_jaw",
                "case_count_for_patient_lower_jaw",
                "censored__of__event_end_observation",
                "censored__of__event_of_interest_after_date_start_observation_position_1",
                "censored__of__event_of_interest_after_date_start_observation_position_2",
                "censored__of__event_of_interest_after_date_start_observation_position_3",
                "censored__of__treatment_position_first_with_" + Keys.BRUECKENANKER + "_after_date_start_observation",
                "censored__of__treatment_position_first_with_" + Keys.ERNEUERUNG_WURZELSTIFT + "_after_date_start_observation",
                "censored__of__treatment_position_first_with_" + Keys.EXTRACTION + "_after_date_start_observation",
                "censored__of__treatment_position_first_with_" + CROWN + "_after_date_start_observation",
                "censored__of__treatment_position_first_with_" + Keys.REZEMENTIERUNG + "_after_date_start_observation",
                "censored__of__treatment_position_first_with_" + Keys.TELESCOPIC_CROWN + "_after_date_start_observation",
                "censored__of__treatment_position_first_with_" + Keys.VITE_TREP_WK + "_after_date_start_observation",
                "censored__of__treatment_position_first_with_" + Keys.WURZELSTIFT + "_after_date_start_observation",
                "count_days__from__date_start_observation__until__date_end_observation",
                "count_days__from__date_start_observation__until__date_of_event_of_interest_after_date_start_observation_position_1__or_until__date_end_search_period",
                "count_days__from__date_start_observation__until__date_of_event_of_interest_after_date_start_observation_position_2__or_until__date_end_search_period",
                "count_days__from__date_start_observation__until__date_of_event_of_interest_after_date_start_observation_position_3__or_until__date_end_search_period",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + Keys.BRUECKENANKER + "_after_date_start_observation__or_until__date_end_search_period",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + Keys.ERNEUERUNG_WURZELSTIFT + "_after_date_start_observation__or_until__date_end_search_period",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + Keys.EXTRACTION + "_after_date_start_observation__or_until__date_end_search_period",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + CROWN + "_after_date_start_observation__or_until__date_end_search_period",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + Keys.REZEMENTIERUNG + "_after_date_start_observation__or_until__date_end_search_period",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + Keys.TELESCOPIC_CROWN + "_after_date_start_observation__or_until__date_end_search_period",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + Keys.VITE_TREP_WK + "_after_date_start_observation__or_until__date_end_search_period",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + Keys.WURZELSTIFT + "_after_date_start_observation__or_until__date_end_search_period",
                "count_evidences_01__from__date_start_observation__until__date_end_observation",
                "count_treatments__on__dentition__with__" + Keys.SPLINT + "__from__date_start_observation__until__date_end_observation",
                "date__of__event_of_interest_after_date_start_observation_position_1",
                "date__of__event_of_interest_after_date_start_observation_position_2",
                "date__of__event_of_interest_after_date_start_observation_position_3",
                "date__of__evidence_01_position_first_after_date_start_observation",
                "date__of__evidence_01_position_last_before_date_start_observation",
                "date__of__evidence_01_position_last_until_date_start_observation",
                "date__of__treatment_position_first_with_" + Keys.BRUECKENANKER + "_after_date_start_observation",
                "date__of__treatment_position_first_with_" + Keys.ERNEUERUNG_WURZELSTIFT + "_after_date_start_observation",
                "date__of__treatment_position_first_with_" + Keys.EXTRACTION + "_after_date_start_observation",
                "date__of__treatment_position_first_with_" + CROWN + "_after_date_start_observation",
                "date__of__treatment_position_first_with_" + Keys.REZEMENTIERUNG + "_after_date_start_observation",
                "date__of__treatment_position_first_with_" + Keys.TELESCOPIC_CROWN + "_after_date_start_observation",
                "date__of__treatment_position_first_with_" + Keys.VITE_TREP_WK + "_after_date_start_observation",
                "date__of__treatment_position_first_with_" + Keys.WURZELSTIFT + "_after_date_start_observation",
                "date_end_observation",
                "date_start_observation",
                "date_of_birth",
                "dmft__of__evidence_01_position_last_before_date_start_observation",
                "dt__of__evidence_01_position_last_before_date_start_observation",
                "endstaendigkeit__of__evidence_01_position_first_after_date_start_observation",
                "ft__of__evidence_01_position_last_before_date_start_observation",
                "gender",
                "groups__of__age_at_event_start_observation",
                "groups__of__dmft_of_evidence_01_position_last_before_date_start_observation",
                "groups__of__dt_of_evidence_01_position_last_before_date_start_observation",
                "groups__of__ft_of_evidence_01_position_last_before_date_start_observation",
                "groups__of__mt_of_evidence_01_position_last_before_date_start_observation",
                "groups__of__toothcount_in_jaw_of_evidence_01_position_first_after_date_start_observation",
                "groups__of__toothcount_in_jaw_of_evidence_01_position_last_before_date_start_observation",
                "groups__of__toothcount_of_evidence_01_position_last_before_date_start_observation",
                "handler__of__event_start_observation",
                "insurance",
                "jaw",
                "mt__of__evidence_01_position_last_before_date_start_observation",
                "patient_index",
                "status__is__" + IMPLANT + "__of__evidence_01_position_last_until_date_start_observation",
                "tooth",
                "toothcontacts__of__evidence_01_position_first_after_date_start_observation",
                "toothcount__of__evidence_01_position_last_before_date_start_observation",
                "toothcount_in_jaw__of__evidence_01_position_first_after_date_start_observation",
                "toothcount_in_jaw__of__evidence_01_position_last_before_date_start_observation",
                "toothtype"
        );
    }
}
