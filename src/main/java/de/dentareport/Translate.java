package de.dentareport;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static de.dentareport.utils.Keys.*;

// TODO: TEST?
public class Translate {

    private final Map<String, Map<String, String>> translationsPerEvaluationType = ImmutableMap.<String, Map<String, String>>builder()
            .put(TEXT_COUNT_CASES, ImmutableMap.of(
                    "7", "Anzahl Füllungen gesamt",
                    "9", "Anzahl TK gesamt"
            ))
            .put(TEXT_CASE_COUNT_FOR_PATIENT, ImmutableMap.of(
                    "7", "Füllungen pro Patient",
                    "9", "TK pro Patient"
            ))
            .put(TEXT_COUNT_ENDODONTIE, ImmutableMap.of(
                    "7", "Anzahl Endodontie nach Füllung",
                    "9", "Anzahl Endodontie nach Eingliederung von TK"
            ))
            .put(TEXT_COUNT_REZEMENTIERUNG, ImmutableMap.of(
                    "7", "Anzahl Rezementierung nach Füllung",
                    "9", "Anzahl Rezementierung bei TK"
            ))
            .put(TEXT_COUNT_WURZELSTIFT, ImmutableMap.of(
                    "7", "Anzahl Wurzelstift nach Füllung",
                    "9", "Anzahl Wurzelstift nach Eingliederung von TK"
            ))
            .build();

    private final Map<String, String> translations = ImmutableMap.<String, String>builder()
            .put("", "")
            .put(BRUECKENANKER, "Brückenanker")
            .put(BRUECKENGLIED, "Brückenglied")
            .put(CARIES_NOT_TO_TREAT, "nicht zu behandeln")
            .put(CARIES_NOT_TO_TREAT_WITH_XRAY, "nicht zu behandeln mit Röntgen")
            .put(CARIES_NOT_TO_TREAT_WITHOUT_XRAY, "nicht zu behandeln ohne Röntgen")
            .put(CARIES_TO_TREAT_WITH_XRAY, "zu behandeln mit Röntgen")
            .put(CARIES_TO_TREAT, "zu behandeln")
            .put(CARIES_TO_TREAT_WITHOUT_XRAY, "zu behandeln ohne Röntgen")
            .put(CARIES_UNKNOWN_SEVERITY, "unbekannte Ausprägung")
            .put(CROWN, "Krone")
            .put(D_ENDSTAENDIG, "D-Endständig")
            .put(ECKZAHN, "Eckzahn")
            .put(ENDSTAENDIG, "Endständig")
            .put(ERNEUERUNG_WURZELSTIFT, "Erneuerung Wurzelstift")
            .put(ERSETZT, "Ersetzt")
            .put(EXTRACTION, "Extraktion")
            .put(FEHLEND, "Fehlend")
            .put(FEMALE, "w")
            .put(FUELLUNG, "Füllung")
            .put(FLUORIDIERUNG, "Fluoridierung")
            .put(FRONTZAHN, "Frontzahn")
            .put(GESCHLOSSEN, "Geschlossen")
            .put(IM_DURCHBRUCH, "Im Durchbruch")
            .put(KOFFERDAM, "Kofferdam")
            .put(HALF_YEAR, "Halbjahr")
            .put(IMPLANT, "Implantat")
            .put(LOWER_JAW, "UK")
            .put(LUECKE_VERENGT_NACH_DISTAL, "Lücke verengt nach distal")
            .put(LUECKE_VERENGT_NACH_LINKS, "Lücke verengt nach links")
            .put(LUECKE_VERENGT_NACH_MESIAL, "Lücke verengt nach mesial")
            .put(LUECKE_VERENGT_NACH_RECHTS, "Lücke verengt nach rechts")
            .put(MALE, "m")
            .put(MUNDGESUNDHEITSAUFKLAERUNG, "Mundgesundheitsaufklärung")
            .put(MUNDHYGIENESTATUS, "Mundhygienestatus")
            .put(OSTEOTOMIE, "Osteotomie")
            .put(MOLAR, "Molar")
            .put(NO_DATA, "no_data")
            .put(PRAEMOLAR, "Prämolar")
            .put(REZEMENTIERUNG, "Rezementierung")
            .put(SPLINT, "Schiene")
            .put(SOUND, "Ohne Befund")
            .put(TELESCOPIC_CROWN, "Teleskopkrone")
            .put(TEXT_AGE_LAST_01, "Alter Patient letzter 01")
            .put(TEXT_AGE_START_OBSERVATION, "Alter zum Versorgungszeitpunkt")
            .put(TEXT_AVERAGE_DMFT_GROUPED_BY_AGE_LAST_01, "DMFT letzter 01 nach Alter")
            .put(TEXT_AVERAGE_TOOTHCOUNT_GROUPED_BY_AGE_LAST_01, "Anzahl Zähne letzter 01 nach Alter")
            .put(TEXT_COUNT_PATIENTS, "Anzahl Patienten")
            .put(TEXT_COUNT_TOOTH_LOSS, "Anzahl Zahnverlust gesamt")
            .put(TEXT_DISTRIBUTION_ENDSTAENDIG, "Anzahl endständige Pfeiler")
            .put(TEXT_DISTRIBUTION_TOOTHCONTACTS, "Anzahl Zahnkontakte approximal")
            .put(TEXT_DMFT_BEFORE_TREATMENT, "DMF-T vor Behandlung")
            .put(TEXT_DMFT_LAST_01, "DMFT letzter 01")
            .put(TEXT_GENDER, "Geschlecht")
            .put(TEXT_INSURANCE, "Versicherungsstatus")
            .put(TEXT_OBSERVATION_PERIOD, "Beobachtungszeit")
            .put(TEXT_PATIENT_COUNT_GROUPED_BY_AGE_LAST_01, "Anzahl Patienten nach Alter")
            .put(TEXT_TOOTHCOUNT_IN_JAW, "Anzahl Zähne im behandelten Kiefer")
            .put(TEXT_TOOTHCOUNT_LAST_01, "Anzahl Zähne letzter 01")
            .put(TEXT_TOOTHTYPES, "Zahntypen")
            .put(UNIT_DAYS, "Tage")
            .put(UNIT_DMFT, "DMF-T")
            .put(UNIT_NUMBER, "Anzahl")
            .put(UNIT_YEARS, "Jahre")
            .put(UPPER_JAW, "OK")
            .put(VITE_TREP_WK, "VitE/Trep/WK")
            .put(WURZELSTIFT, "Wurzelstift")
            .put(XRAY_EINZELBILD, "Röntgen Einzelbild")
            .put(XRAY_OPG, "Röntgen OPG")
            .put(YEAR, "Jahr")
            .put(ZAHNREIHE, "Zahnreihe")
            .build();

    public String translate(String value) {
        return translations.getOrDefault(value, value);
    }

    public String translate(String value, String evaluationId) {
        if (translationsPerEvaluationType.containsKey(value)) {
            return translationsPerEvaluationType.get(value)
                    .getOrDefault(evaluationId, translations.getOrDefault(value, value));
        }
        return translations.getOrDefault(value, value);
    }
}
