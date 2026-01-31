package de.dentareport.evaluations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.dentareport.models.CaseData;
import de.dentareport.models.EventInterface;
import de.dentareport.models.RawData;
import de.dentareport.models.TreatmentInterface;
import de.dentareport.utils.Treatments;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static de.dentareport.utils.Keys.*;

// TODO: TEST?
public class Evaluation7 extends Evaluation {

    public Evaluation7() {
        super();
    }

    @Override
    public String evaluationName() {
        return "fuellungen";
    }

    @Override
    public TreatmentInterface eventStartObservation(RawData rawData, CaseData caseData) {
        return new Treatments(rawData, caseData).withBillingpositions(requiredBillingPositionsForEventStartObservation()).first();
    }

    @Override
    public String longDocumentationForStartObservation() {
        return String.format("Erstes Auftreten von ~~%s~~", FUELLUNG);
    }

    @Override
    public Set<String> requiredBillingPositionsForEventStartObservation() {
        return ImmutableSet.of(FUELLUNG);
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
                "Erstes Auftreten von ~~%s~~, ~~%s~~, ~~%s~~, ~~%s~~, ~~%s~~, ~~%s~~ oder ~~%s~~ " +
                "nach Start Beobachtung",
                FUELLUNG,
                CROWN,
                TELESCOPIC_CROWN,
                BRUECKENANKER,
                VITE_TREP_WK,
                EXTRACTION,
                OSTEOTOMIE);
    }

    @Override
    public Set<String> requiredBillingPositionsForEventEndObservation() {
        return ImmutableSet.of(
                BRUECKENANKER,
                EXTRACTION,
                FUELLUNG,
                CROWN,
                OSTEOTOMIE,
                TELESCOPIC_CROWN,
                VITE_TREP_WK
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
                ERNEUERUNG_WURZELSTIFT,
                EXTRACTION,
                FUELLUNG,
                CROWN,
                OSTEOTOMIE,
                REZEMENTIERUNG,
                VITE_TREP_WK,
                WSR,
                WURZELSTIFT
        );
    }

    @Override
    protected Set<String> evaluationColumns() {
        return ImmutableSet.of(
                "age__at__event_start_observation",
                "age_days__at__event_start_observation",
                "billingcode__of__event_start_observation",
                "billingposition__of__event_end_observation",
                "billingposition__of__treatment_on_dentition_position_first_with_" + SPLINT,
                "caries_surfaces__specification__" + CARIES_NOT_TO_TREAT + "__of__evidence_01_position_first_from_date_start_observation",
                "caries_surfaces__specification__" + CARIES_NOT_TO_TREAT + "__of__evidence_01_position_last_before_date_start_observation",
                "caries_surfaces__specification__" + CARIES_NOT_TO_TREAT + "__of__evidence_01_position_last",
                "caries_surfaces__specification__" + CARIES_TO_TREAT + "__of__evidence_01_position_first_from_date_start_observation",
                "caries_surfaces__specification__" + CARIES_TO_TREAT + "__of__evidence_01_position_last_before_date_start_observation",
                "caries_surfaces__specification__" + CARIES_TO_TREAT + "__of__evidence_01_position_last",
                "caries_specification__surface__" + SURFACE_CERVIKAL + "__of__evidence_01_position_last_before_date_start_observation",
                "caries_specification__surface__" + SURFACE_DISTAL + "__of__evidence_01_position_last_before_date_start_observation",
                "caries_specification__surface__" + SURFACE_LINGUAL + "__of__evidence_01_position_last_before_date_start_observation",
                "caries_specification__surface__" + SURFACE_MESIAL + "__of__evidence_01_position_last_before_date_start_observation",
                "caries_specification__surface__" + SURFACE_OKKLUSAL + "__of__evidence_01_position_last_before_date_start_observation",
                "caries_specification__surface__" + SURFACE_VESTIBULAER + "__of__evidence_01_position_last_before_date_start_observation",
                "case_count_for_patient",
                "censored__of__event_end_observation",
                "censored__of__treatment_position_first_with_" + BRUECKENANKER + "_after_date_start_observation",
                "censored__of__treatment_position_first_with_" + EXTRACTION + "_after_date_start_observation",
                "censored__of__treatment_position_first_with_" + FUELLUNG + "_after_date_start_observation",
                "censored__of__treatment_position_first_with_" + FUELLUNG + "_filter_only_" + IDENTICAL_SURFACES + "_as_event_start_observation_after_date_start_observation",
                "censored__of__treatment_position_first_with_" + FUELLUNG + "_filter_only_" + AT_LEAST_ONE_SURFACE + "_as_event_start_observation_after_date_start_observation",
                "censored__of__treatment_position_first_with_" + CROWN + "_after_date_start_observation",
                "censored__of__treatment_position_first_with_" + TELESCOPIC_CROWN + "_after_date_start_observation",
                "censored__of__treatment_position_first_with_" + VITE_TREP_WK + "_after_date_start_observation",
                "cofferdam__at__event_start_observation",
                "count_caries_surfaces__specification__" + CARIES_NOT_TO_TREAT + "__of__evidence_01_position_first_from_date_start_observation",
                "count_caries_surfaces__specification__" + CARIES_NOT_TO_TREAT + "__of__evidence_01_position_last_before_date_start_observation",
                "count_caries_surfaces__specification__" + CARIES_NOT_TO_TREAT + "__of__evidence_01_position_last",
                "count_caries_surfaces__specification__" + CARIES_TO_TREAT + "__of__evidence_01_position_first_from_date_start_observation",
                "count_caries_surfaces__specification__" + CARIES_TO_TREAT + "__of__evidence_01_position_last_before_date_start_observation",
                "count_caries_surfaces__specification__" + CARIES_TO_TREAT + "__of__evidence_01_position_last",
                "count_days__from__date_start_observation__until__date_end_observation",
                "count_days__from__date_start_observation__until__date_end_observation__format__epi",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + BRUECKENANKER + "_after_date_start_observation__or_until__date_end_search_period",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + BRUECKENANKER + "_after_date_start_observation__or_until__date_end_search_period__format__epi",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + CROWN + "_after_date_start_observation__or_until__date_end_search_period",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + CROWN + "_after_date_start_observation__or_until__date_end_search_period__format__epi",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + FUELLUNG + "_after_date_start_observation__or_until__date_end_search_period",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + FUELLUNG + "_after_date_start_observation__or_until__date_end_search_period__format__epi",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + FUELLUNG + "_filter_only_" + IDENTICAL_SURFACES + "_as_event_start_observation_after_date_start_observation__or_until__date_end_search_period",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + FUELLUNG + "_filter_only_" + IDENTICAL_SURFACES + "_as_event_start_observation_after_date_start_observation__or_until__date_end_search_period__format__epi",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + FUELLUNG + "_filter_only_" + AT_LEAST_ONE_SURFACE + "_as_event_start_observation_after_date_start_observation__or_until__date_end_search_period",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + FUELLUNG + "_filter_only_" + AT_LEAST_ONE_SURFACE + "_as_event_start_observation_after_date_start_observation__or_until__date_end_search_period__format__epi",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + EXTRACTION + "_after_date_start_observation__or_until__date_end_search_period",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + EXTRACTION + "_after_date_start_observation__or_until__date_end_search_period__format__epi",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + TELESCOPIC_CROWN + "_after_date_start_observation__or_until__date_end_search_period",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + TELESCOPIC_CROWN + "_after_date_start_observation__or_until__date_end_search_period__format__epi",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + VITE_TREP_WK + "_after_date_start_observation__or_until__date_end_search_period",
                "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + VITE_TREP_WK + "_after_date_start_observation__or_until__date_end_search_period__format__epi",
                "count_evidences_01__from__date_start_observation__until__date_end_observation",
                "count_evidences_01__from__date_start_observation__until__date_end_observation__per__half_year",
                "count_filling_surfaces__of__evidence_01_position_first_from_date_start_observation",
                "count_filling_surfaces__of__evidence_01_position_last_before_date_start_observation",
                "count_filling_surfaces__of__evidence_01_position_last",
                "count_filling_surfaces__on__dentition__from__date_start_observation__until__date_end_observation",
                "count_filling_surfaces__on__dentition__from__date_start_observation__until__date_end_observation__per__year",
                "count_filling_surfaces__on__dentition__before__date_start_observation",
                "count_filling_surfaces__on__dentition__before__date_start_observation__per__year",
                "count_treatments__on__dentition__with__" + FUELLUNG + "__before__date_start_observation",
                "count_treatments__on__dentition__with__" + FUELLUNG + "__before__date_start_observation__per__year",
                "count_treatments__on__dentition__with__" + FUELLUNG + "__from__date_start_observation__until__date_end_observation",
                "count_treatments__on__dentition__with__" + FUELLUNG + "__from__date_start_observation__until__date_end_observation__per__year",
                "count_treatments__on__dentition__with__" + FLUORIDIERUNG + "__from__date_start_observation__until__date_end_observation",
                "count_treatments__on__dentition__with__" + FLUORIDIERUNG + "__from__date_start_observation__until__date_end_observation__per__half_year",
                "count_treatments__on__dentition__with__" + MUNDGESUNDHEITSAUFKLAERUNG + "__from__date_start_observation__until__date_end_observation",
                "count_treatments__on__dentition__with__" + MUNDGESUNDHEITSAUFKLAERUNG + "__from__date_start_observation__until__date_end_observation__per__half_year",
                "count_treatments__on__dentition__with__" + MUNDHYGIENESTATUS + "__from__date_start_observation__until__date_end_observation",
                "count_treatments__on__dentition__with__" + MUNDHYGIENESTATUS + "__from__date_start_observation__until__date_end_observation__per__half_year",
                "count_treatments__on__dentition__with__" + SPLINT + "__from__date_start_observation__until__date_end_observation",
                "date__of__evidence_01_position_first_after_date_start_observation",
                "date__of__evidence_01_position_last",
                "date__of__evidence_01_position_last_before_date_start_observation",
                "date__of__treatment_on_dentition_position_first_with_" + SPLINT,
                "date__of__treatment_position_first_with_" + BRUECKENANKER + "_after_date_start_observation",
                "date__of__treatment_position_first_with_" + EXTRACTION + "_after_date_start_observation",
                "date__of__treatment_position_first_with_" + FUELLUNG + "_after_date_start_observation",
                "date__of__treatment_position_first_with_" + FUELLUNG + "_filter_only_" + IDENTICAL_SURFACES + "_as_event_start_observation_after_date_start_observation",
                "date__of__treatment_position_first_with_" + FUELLUNG + "_filter_only_" + AT_LEAST_ONE_SURFACE + "_as_event_start_observation_after_date_start_observation",
                "date__of__treatment_position_first_with_" + CROWN + "_after_date_start_observation",
                "date__of__treatment_position_first_with_" + TELESCOPIC_CROWN + "_after_date_start_observation",
                "date__of__treatment_position_first_with_" + VITE_TREP_WK + "_after_date_start_observation",
                "date__of__treatment_position_first_with_" + XRAY_EINZELBILD + "_from_date_start_observation",
                "date__of__treatment_position_first_with_" + XRAY_OPG + "_from_date_start_observation",
                "date_end_observation",
                "date_first_visit",
                "date_last_visit",
                "date_of_birth",
                "date_start_observation",
                "dft__of__evidence_01_position_last",
                "dmft__of__evidence_01_position_last",
                "dt__of__evidence_01_position_last",
                "errorcodes",
                "dmft__of__evidence_01_position_last_before_date_start_observation",
                "dt__of__evidence_01_position_last_before_date_start_observation",
                "ft__of__evidence_01_position_last_before_date_start_observation",
                "ft__of__evidence_01_position_last",
                "gender",
                "handler__of__event_start_observation",
                "insurance",
                "material__of__event_start_observation",
                "mt__of__evidence_01_position_last_before_date_start_observation",
                "mt__of__evidence_01_position_last",
                "patient_index",
                "surfaces__of__event_start_observation",
                "surfaces__of__treatment_position_first_with_" + FUELLUNG + "_after_date_start_observation",
                "tooth",
                "toothcount__of__evidence_01_position_last_before_date_start_observation",
                "toothtype",
                "year__of__event_start_observation"

                //---------------------------------------------------------------------------------------------------------
                // alte Spalten
                //---------------------------------------------------------------------------------------------------------
                //$gewuenschteSpalten = [

                //                    'alle_behandlungen_ab_start_beobachtung',
                //                    'anzahl_zaehne_gegenkiefer_erster_01_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'anzahl_zaehne_gegenkiefer_letzter_01_patient',
                //                    'anzahl_zaehne_gegenkiefer_naechster_01_an_ende_beobachtung_ab_start_patient_bis_ende_beobachtung_plus_180t',
                //                    'anzahl_zaehne_gegenkiefer_naechster_01_an_start_beobachtung_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'anzahl_zaehne_kiefer_erster_01_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'anzahl_zaehne_kiefer_letzter_01_patient',
                //                    'anzahl_zaehne_kiefer_naechster_01_an_ende_beobachtung_ab_start_patient_bis_ende_beobachtung_plus_180t',
                //                    'anzahl_zaehne_kiefer_naechster_01_an_start_beobachtung_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'anzahl_zahnkontakte_befund_erster_01_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'anzahl_zahnkontakte_befund_letzter_01_patient',
                //                    'anzahl_zahnkontakte_befund_naechster_01_an_ende_beobachtung_ab_start_patient_bis_ende_beobachtung_plus_180t',
                //                    'anzahl_zahnkontakte_befund_naechster_01_an_start_beobachtung_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'code_restauration_erster_01_ab_start_beobachtung',
                //                    'code_restauration_letzter_01_bis_start_beobachtung',
                //                    'code_restauration_letzter_01_patient',
                //                    'datum_erster_01_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'datum_erster_01_nach_start_beobachtung_mit_karies',
                //                    'datum_letzter_01_bis_erste_fuellung_nach_start_beobachtung',
                //                    'datum_letzter_01_bis_erste_versiegelung_nach_start_beobachtung',
                //                    'datum_naechster_01_an_ende_beobachtung_ab_start_patient_bis_ende_beobachtung_plus_180t',
                //                    'datum_naechster_01_an_start_beobachtung_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'dft_erster_01_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'dft_letzter_01_bis_erste_fuellung_nach_start_beobachtung',
                //                    'dft_letzter_01_bis_erste_versiegelung_nach_start_beobachtung',
                //                    'dft_naechster_01_an_ende_beobachtung_ab_start_patient_bis_ende_beobachtung_plus_180t',
                //                    'dft_naechster_01_an_start_beobachtung_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'dmft_erster_01_ab_start_beobachtung',
                //                    'dmft_erster_01_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'dmft_letzter_01_bis_erste_fuellung_nach_start_beobachtung',
                //                    'dmft_letzter_01_bis_erste_versiegelung_nach_start_beobachtung',
                //                    'dmft_naechster_01_an_ende_beobachtung_ab_start_patient_bis_ende_beobachtung_plus_180t',
                //                    'dmft_naechster_01_an_start_beobachtung_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'dt_erster_01_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'dt_naechster_01_an_ende_beobachtung_ab_start_patient_bis_ende_beobachtung_plus_180t',
                //                    'dt_naechster_01_an_start_beobachtung_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'endstaendig_befund_erster_01_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'endstaendig_befund_letzter_01_patient',
                //                    'endstaendig_befund_naechster_01_an_ende_beobachtung_ab_start_patient_bis_ende_beobachtung_plus_180t',
                //                    'endstaendig_befund_naechster_01_an_start_beobachtung_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'ft_erster_01_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'ft_naechster_01_an_ende_beobachtung_ab_start_patient_bis_ende_beobachtung_plus_180t',
                //                    'ft_naechster_01_an_start_beobachtung_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'fuellungsflaechen_zahn_erster_01_ab_start_beobachtung',
                //                    'fuellungsflaechen_zahn_letzter_01_bis_start_beobachtung',
                //                    'fuellungsflaechen_zahn_letzter_01_patient',
                //                    'krone_oder_teleskopkrone_erster_01_ab_start_beobachtung',
                //                    'krone_oder_teleskopkrone_letzter_01_bis_start_beobachtung',
                //                    'krone_oder_teleskopkrone_letzter_01_patient',
                //                    'material_fuellung_erster_01_ab_start_beobachtung',
                //                    'material_fuellung_letzter_01_bis_start_beobachtung',
                //                    'material_fuellung_letzter_01_patient',
                //                    'mt_erster_01_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'mt_naechster_01_an_ende_beobachtung_ab_start_patient_bis_ende_beobachtung_plus_180t',
                //                    'mt_naechster_01_an_start_beobachtung_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'status_flaechen_start_beobachtung_letzter_01_vor_start_beobachtung',
                //                    'tage_ab_start_beobachtung_bis_erster_01_nach_start_beobachtung_mit_karies',
                //                    'tage_ab_start_beobachtung_bis_erster_01_nach_start_beobachtung_mit_karies_epi',
                //                    'wert_befund_gegenzahn_naechster_01_an_start_beobachtung_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'wert_befund_naechster_01_an_start_beobachtung_ab_start_patient_bis_start_beobachtung_plus_180t',
                //                    'wert_karies_erster_01_nach_start_beobachtung_mit_karies',
                //                    'werte_flaechen_karies_letzter_01_bis_start_beobachtung',




        );
    }
}
