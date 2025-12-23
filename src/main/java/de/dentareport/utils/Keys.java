package de.dentareport.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Keys {

    public static final String LANG_DE = "de";

    public static final String ORIGINAL_NAME = "original_name";
    public static final String WITH = "with";
    public static final String AS = "as";
    public static final String IS = "is";
    public static final String POSITION = "position";
    public static final String FIRST = "first";
    public static final String LAST = "last";
    public static final String ON = "on";
    public static final String FILTER_ONLY = "filter_only";
    public static final String FORMAT = "format";
    public static final String DENTITION = "dentition";
    public static final String AT = "at";
    public static final String OF = "of";
    public static final String FROM = "from";
    public static final String AFTER = "after";
    public static final String BEFORE = "before";
    public static final String UNTIL = "until";
    public static final String OR_UNTIL = "or_until";
    public static final String PER = "per";
    public static final String HALF_YEAR = "half_year";
    public static final String YEAR = "year";
    public static final String SPECIFICATION = "specification";
    public static final String SURFACE = "surface";
    public static final String DATE_END_SEARCH_PERIOD = "date_end_search_period";
    public static final String IDENTICAL_SURFACES = "identical_surfaces";
    public static final String AT_LEAST_ONE_SURFACE = "at_least_one_surface";


    public static final String SUFFIX_LONG = "_long";

    public static final String EVALUATION_TYPE_FILLING = "7";
    public static final String EVALUATION_TYPE_TELESCOPIC_CROWN = "9";

    public static final String FILLING__MATERIAL_1 = "1";
    public static final String FILLING__MATERIAL_1_EXTERNAL = "1e";
    public static final String FILLING__MATERIAL_2 = "2";
    public static final String FILLING__MATERIAL_2_EXTERNAL = "2e";
    public static final String FILLING__MATERIAL_3 = "3";
    public static final String FILLING__MATERIAL_3_EXTERNAL = "3e";
    public static final String FILLING__MATERIAL_4 = "4";
    public static final String FILLING__MATERIAL_4_EXTERNAL = "4e";
    public static final String FILLING__MATERIAL_5 = "5";
    public static final String FILLING__MATERIAL_5_EXTERNAL = "5e";
    public static final String FILLING__MATERIAL_6 = "6";
    public static final String FILLING__MATERIAL_6_EXTERNAL = "6e";
    public static final String FILLING__MATERIAL_7 = "7";
    public static final String FILLING__MATERIAL_7_EXTERNAL = "7e";
    public static final String FILLING__MATERIAL_8 = "8";
    public static final String FILLING__MATERIAL_8_EXTERNAL = "8e";
    public static final String FILLING__NO_FILLING = "0";

    public static final String SURFACE_CERVIKAL = "c";
    public static final String SURFACE_DISTAL = "d";
    public static final String SURFACE_LINGUAL = "l";
    public static final String SURFACE_MESIAL = "m";
    public static final String SURFACE_OKKLUSAL = "o";
    public static final String SURFACE_VESTIBULAER = "v";
    public static final List<String> SURFACES = surfaces();
    public static final String EVIDENCE_TYPE_01 = "befund_01";
    public static final String EVIDENCE_MILKTOOTH_YES = "1";
    public static final String EVIDENCE_MILKTOOTH_NO = "0";
    public static final String CENSORED_YES = "1";
    public static final String CENSORED_NO = "0";
    public static final String UPPER_JAW = "uj";
    public static final String LOWER_JAW = "lj";
    public static final String MALE = "m";
    public static final String FEMALE = "f";
    public static final String ZAHNREIHE = "zahnreihe";
    public static final String ENDSTAENDIG = "endstaendig";
    public static final String D_ENDSTAENDIG = "d-endstaendig";
    public static final String FRONTZAHN = "frontzahn";
    public static final String ECKZAHN = "eckzahn";
    public static final String PRAEMOLAR = "praemolar";
    public static final String MOLAR = "molar";
    public static final String BRUECKENANKER = "brueckenanker";
    public static final String CROWN = "krone";
    public static final String ERNEUERUNG_WURZELSTIFT = "erneuerung_wurzelstift";
    public static final String EXTRACTION = "extraktion";
    public static final String FLUORIDIERUNG = "fluoridierung";
    public static final String FUELLUNG = "fuellung";
    public static final String IMPLANT = "implantat";
    public static final String KOFFERDAM = "kofferdam";
    public static final String SOUND = "sound";
    public static final String BRUECKENGLIED = "brueckenglied";
    public static final String IM_DURCHBRUCH = "im_durchbruch";
    public static final String ERSETZT = "ersetzt";
    public static final String FEHLEND = "fehlend";
    public static final String GESCHLOSSEN = "geschlossen";
    public static final String LUECKE_VERENGT_NACH_LINKS = "luecke_verengt_nach_links";
    public static final String LUECKE_VERENGT_NACH_RECHTS = "luecke_verengt_nach_rechts";
    public static final String LUECKE_VERENGT_NACH_DISTAL = "luecke_verengt_nach_distal";
    public static final String LUECKE_VERENGT_NACH_MESIAL = "luecke_verengt_nach_mesial";
    public static final String MUNDGESUNDHEITSAUFKLAERUNG = "mundgesundheitsaufklaerung";
    public static final String MUNDHYGIENESTATUS = "mundhygienestatus";
    public static final String OSTEOTOMIE = "osteotomie";
    public static final String PROVISORIUM = "provisorium";
    public static final String REZEMENTIERUNG = "rezementierung";
    public static final String SPLINT = "schiene";
    public static final String TELESCOPIC_CROWN = "telescopic_crown";
    public static final String TOOTH_LOSS = "tooth_loss";
    public static final String VIPR = "vipr";
    public static final String VITE_TREP_WK = "vite_trep_wk";
    public static final String WSR = "wsr";
    public static final String WURZELSTIFT = "wurzelstift";
    public static final String XRAY_EINZELBILD = "xray_einzelbild";
    public static final String XRAY_OPG = "xray_opg";
    public static final String NO_CARIES = "no_caries";
    public static final String CARIES_UNKNOWN_SEVERITY = "caries_unknown_severity";
    public static final String CARIES_NOT_TO_TREAT = "caries_not_to_treat";
    public static final String CARIES_TO_TREAT = "caries_to_treat";
    public static final String CARIES_NOT_TO_TREAT_WITH_XRAY = "caries_not_to_treat_with_xray";
    public static final String CARIES_NOT_TO_TREAT_WITHOUT_XRAY = "caries_not_to_treat_without_xray";
    public static final String CARIES_TO_TREAT_WITH_XRAY = "caries_to_treat_with_xray";
    public static final String CARIES_TO_TREAT_WITHOUT_XRAY = "caries_to_treat_without_xray";
    public static final String INSURANCE_GESETZLICH = "g";
    public static final String INSURANCE_PRIVAT = "p";
    public static final Set<String> DAMPSOFT_INSURANCE_GESETZLICH = ImmutableSet.of("M", "R", "F");
    public static final Set<String> DAMPSOFT_INSURANCE_PRIVAT = ImmutableSet.of("P");
    public static final String CLASSNAME_OF_COLUMNS_TO_GROUP = "Groups";
    public static final String XLS_EVALUATION = "Auswertung";
    public static final String XLS_DOCUMENTATION = "Spalten";
    public static final String XLS_GLOSSARY = "Glossar";
    public static final String START_OBSERVATION = "Start Beobachtung";
    public static final String END_OBSERVATION = "Ende Beobachtung";
    public static final String NO_DATA = "no_data";
    public static final String TEXT_AGE_LAST_01 = "age last 01";
    public static final String TEXT_AGE_START_OBSERVATION = "age start observation";
    public static final String TEXT_AVERAGE_DMFT_GROUPED_BY_AGE_LAST_01 = "average dmft grouped by age last 01";
    public static final String TEXT_AVERAGE_TOOTHCOUNT_GROUPED_BY_AGE_LAST_01 = "average toothcount grouped by age last 01";
    public static final String TEXT_CASE_COUNT_FOR_PATIENT = "case count for patient";
    public static final String TEXT_COUNT_CASES = "case count";
    public static final String TEXT_COUNT_ENDODONTIE = "endodontie count";
    public static final String TEXT_COUNT_PATIENTS = "patient count";
    public static final String TEXT_COUNT_REZEMENTIERUNG = "rezementierung count";
    public static final String TEXT_COUNT_TOOTH_LOSS = "tooth loss count";
    public static final String TEXT_COUNT_WURZELSTIFT = "wurzelstift count";
    public static final String TEXT_DISTRIBUTION_ENDSTAENDIG = "distribution endstaendig";
    public static final String TEXT_DISTRIBUTION_TOOTHCONTACTS = "distribution toothcontacts";
    public static final String TEXT_DMFT_BEFORE_TREATMENT = "dmft before treatment";
    public static final String TEXT_DMFT_LAST_01 = "dmft last 01";
    public static final String TEXT_GENDER = "gender";
    public static final String TEXT_INSURANCE = "insurance";
    public static final String TEXT_OBSERVATION_PERIOD = "observation period";
    public static final String TEXT_PATIENT_COUNT_GROUPED_BY_AGE_LAST_01 = "patient count grouped by age last 01";
    public static final String TEXT_TOOTHCOUNT_IN_JAW = "toothcount in jaw";
    public static final String TEXT_TOOTHCOUNT_LAST_01 = "toothcount last 01";
    public static final String TEXT_TOOTHTYPES = "toothtypes";
    public static final String UNIT_NUMBER = "number";
    public static final String UNIT_DAYS = "days";
    public static final String UNIT_DMFT = "dmft";
    public static final String UNIT_YEARS = "years";
    public static final String GUI_ID_MENU = "menu";
    public static final String GUI_TEXT_AFR_10_YEARS = "AFR 10 Jahre";
    public static final String GUI_TEXT_AFR_5_YEARS = "AFR 5 Jahre";
    public static final String GUI_TEXT_AGE_START_OBSERVATION = "Alter zum Versorgungszeitpunkt";
    public static final String GUI_TEXT_AVERAGE = "Durchschnitt";
    public static final String GUI_TEXT_BACK = "zurück";
    public static final String GUI_TEXT_BLANK = "";
    public static final String GUI_TEXT_BRIDGES = "Brücken";
    public static final String GUI_TEXT_CANCEL_DATA_IMPORT = "Datenimport abbrechen";
    public static final String GUI_TEXT_CANCEL_EVALUATION = "Berechnung abbrechen";
    public static final String GUI_TEXT_CHOOSE_EVALUATION = "Bitte wählen Sie ein Auswertungsthema:";
    public static final String GUI_TEXT_CONFIRM_CANCEL_IMPORT = "Wirklich abbrechen?";
    public static final String GUI_TEXT_CONFIRM_CANCEL_EVALUATION = "Wirklich abbrechen?";
    public static final String GUI_TEXT_CONFIRM_EXIT = "Wirklich beenden?";
    public static final String GUI_TEXT_COPY_FILES = "Dateien kopieren";
    public static final String GUI_TEXT_COPY_FILES_AUTOMATIC = "Dateien automatisch suchen und kopieren";
    // TODO
    public static final String GUI_TEXT_COPY_FILES_EXPLANATION =
            "Um die Sicherheit Ihrer Daten zu gewährleisten, arbeitet Dentareport zu keinem Zeitpunkt\n" +
                    "mit den Originaldateien Ihrer Dampsoft-Installation, sondern ausschließlich mit Kopien.\n" +
                    "Dazu müssen die von Dentareport benötigten Dateien in das Dentareport-Verzeichnis kopiert werden.\n\n" +
                    "Sie können dies von Dentareport automatisch durchführen lassen.\n" +
                    "Dies ist der empfohlene Weg wenn Sie Dampsoft mit den Standardeinstellungen installiert haben.\n\n" +
                    "Alternativ dazu können Sie auch den Ordner manuell auswählen, aus dem Dentareport\n" +
                    "die Dampsoft-Dateien kopieren soll.\n\n" +
                    "Wenn Sie die benötigten Dateien selbst kopieren möchten folgen Sie bitte den Anweisungen.";
    public static final String GUI_TEXT_COPY_FILES_FROM_SOURCE_DIRECTORY = "Dateien kopieren";
    public static final String GUI_TEXT_COPY_FILES_MANUALLY = "Überspringen und Dateien manuell kopieren";
    public static final String GUI_TEXT_COPY_FILES_MANUALLY_FOR_IMPORT = "Bitte die folgenden Dateien aus Ihrer Dampsoft-Installation\nin den Ordner \"import\" im Dentareport-Verzeichnis kopieren:\n";
    public static final String GUI_TEXT_COPY_FILES_SELECT_FOLDER = "Ordner auswählen aus dem kopiert werden soll";
    public static final String GUI_TEXT_COUNT = "Anzahl";
    public static final String GUI_TEXT_CROWNS = "Kronen";
    public static final String GUI_TEXT_DEMO_VERSION = "DEMO Version";
    public static final String GUI_TEXT_DENTAREPORT = "Dentareport";
    public static final String GUI_TEXT_DISPLAY = "anzeigen";
    public static final String GUI_TEXT_DMFT = "DMF-T";
    public static final String GUI_TEXT_DONE = "Fertig.";
    public static final String GUI_TEXT_ERROR = "Error";
    public static final String GUI_TEXT_ERROR_NO_VALID_EVALUATION_FILLING = "Bitte führen Sie zuerst eine\nvollständige Berechnung für plastische Füllungen aus.";
    public static final String GUI_TEXT_ERROR_NO_VALID_EVALUATION_TELESCOPIC_CROWN = "Bitte führen Sie zuerst eine\nvollständige Berechnung für Teleskopkronen aus.";
    public static final String GUI_TEXT_ERROR_NO_VALID_IMPORT_FOUND = "Bitte führen Sie zuerst einen\nvollständigen Datenimport aus.";
    public static final String GUI_TEXT_ENDODONTICS = "Endodontie";
    public static final String GUI_TEXT_ENDSTAENDIGER_PFEILER = "endständiger Pfeiler";
    public static final String GUI_TEXT_EVALUATIONS = "Auswertungen";
    public static final String GUI_TEXT_FILLINGS = "plastische Füllungen";
    public static final String GUI_TEXT_FINISHING_EVALUATION = "Auswertung wird abgeschlossen.";
    public static final String GUI_TEXT_FUNCTION_NOT_AVAILABLE_IN_DEMO_VERSION = "Diese Funktion steht in der Demoversion\n von Dentareport nicht zur Verfügung.";
    public static final String GUI_TEXT_GENDER = "Geschlecht";
    public static final String GUI_TEXT_GENERAL_INFORMATION = "Allgemeine Informationen";
    public static final String GUI_TEXT_GENERAL_INFORMATION_FILLINGS = "Allgemeine Informationen über die Fälle mit plastischen Füllungen\n(%s - %s)";
    public static final String GUI_TEXT_GENERAL_INFORMATION_TELESCOPIC_CROWNS = "Allgemeine Informationen über die Fälle mit Teleskopkronen\n(%s - %s)";
    public static final String GUI_TEXT_GENERAL_PATIENT_INFORMATION = "allgemeine Informationen über die Patienten Ihrer Praxis";
    public static final String GUI_TEXT_GROUPED_BY = "gruppiert nach";
    public static final String GUI_TEXT_HEADING_START_PANE_1 = "Die praxisindividuelle Ereignisanalyse\nfür Ihr Qualitätsmanagement";
    public static final String GUI_TEXT_HEADING_START_PANE_2 = "Ihre Routinedaten aus den Programmen\nDampsoft und CGM Z1";
    public static final String GUI_TEXT_IMPLANTS = "Implantate";
    public static final String GUI_TEXT_IMPORT_DATA = "Daten importieren";
    public static final String GUI_TEXT_IMPORTING_BEFUND_01 = "Importiere 01-Befunde";
    public static final String GUI_TEXT_IMPORTING_BEFUND_HKP = "Importiere HKP-Befunde";
    public static final String GUI_TEXT_IMPORTING_BEFUND_PA = "Importiere PA-Befunde";
    public static final String GUI_TEXT_IMPORTING_ENDOSTAMPS = "Importiere Endostempel";
    public static final String GUI_TEXT_IMPORTING_FIRST_AND_LAST_VISIT = "Importiere erste und letzte Besuche";
    public static final String GUI_TEXT_IMPORTING_NOTES = "Importiere Notizen";
    public static final String GUI_TEXT_IMPORTING_PATIENT = "Importiere Patienten";
    public static final String GUI_TEXT_IMPORTING_PATKUERZ = "Importiere Patientenkürzel";
    public static final String GUI_TEXT_IMPORTING_TREATMENTS = "Importiere Behandlungen";
    public static final String GUI_TEXT_IMPORTING_TREATMENTS_HKP = "Importiere HKP-Behandlungen";
    public static final String GUI_TEXT_INITIALIZE_PROGRESSBAR_LABEL = "";
    public static final String GUI_TEXT_INLAYS = "Inlays";
    public static final String GUI_TEXT_INVALID_SOURCE_DIRECTORY = "Die benötigten Dateien wurden im\nausgewählten Quellverzeichnis nicht gefunden.";
    public static final String GUI_TEXT_ITEM = "Item";
    public static final String GUI_TEXT_MAXIMUM = "Maximum";
    public static final String GUI_TEXT_MEDIAN = "Median";
    public static final String GUI_TEXT_MINIMUM = "Minimum";
    public static final String GUI_TEXT_MISSING_FILES_FOR_IMPORT = "Bitte die fehlenden Dateien in den Ordner \"import\" kopieren:\n";
    public static final String GUI_TEXT_NO = "Nein";
    public static final String GUI_TEXT_NO_DEPENDENCY = "Keine";
    public static final String GUI_TEXT_NO_DIRECTORY_SELECTED = "Kein Ordner ausgewählt";
    public static final String GUI_TEXT_OK = "OK";
    public static final String GUI_TEXT_PLEASE_WAIT_FILES_ARE_COPIED = "Bitte warten, Dateien werden kopiert";
    public static final String GUI_TEXT_PREPARE_HKP = "Vorbereiten HKP: ";
    public static final String GUI_TEXT_PREVENTION_MEASURES = "Präventionsmaßnahmen";
    public static final String GUI_TEXT_PROBABILITIES = "Ereigniswahrscheinlichkeit";
    public static final String GUI_TEXT_QUIT = "Programm beenden";
    public static final String GUI_TEXT_REZEMENTIERUNG = "Rezementierung";
    public static final String GUI_TEXT_SELECT_SOURCE_DIRECTORY = "Quellordner auswählen";
    public static final String GUI_TEXT_STEP_X_OF_Y = "Schritt %s von %s";
    public static final String GUI_TEXT_SEALINGS = "Fissurenversiegelungen";
    public static final String GUI_TEXT_SELECT_DEPENDENCY = "abhängig von";
    public static final String GUI_TEXT_SELECT_EVENT = "Ereignisauswahl";
    public static final String GUI_TEXT_START_DATA_IMPORT = "Datenimport starten";
    public static final String GUI_TEXT_START_NEW_EVALUATION = "neue Berechnung starten";
    public static final String GUI_TEXT_SURGERY = "Chirurgie";
    public static final String GUI_TEXT_TELESCOPIC_CROWNS = "Teleskopkronen";
    public static final String GUI_TEXT_TOOTHCONTACTS = "Zahnkontakte";
    public static final String GUI_TEXT_TOOTHCOUNT_IN_JAW = "Zahnanzahl im Kiefer";
    public static final String GUI_TEXT_TOOTHLOSS = "Zahnverlust";
    public static final String GUI_TEXT_TOOTHTYPE = "Zahntyp";
    public static final String GUI_TEXT_TOTAL = "Gesamt";
    public static final String GUI_TEXT_UNDER_EVENT = "unter Ereignis";
    public static final String GUI_TEXT_WURZELSTIFT = "Wurzelstift";
    public static final String GUI_TEXT_WURZELSTIFTE = "Wurzelstifte";
    public static final String GUI_TEXT_YEARS = "Jahre";
    public static final String GUI_TEXT_YES = "Ja";
    public static final String GUI_VIEW_COPY_FILES = "gui_view_copy_files";
    public static final String GUI_VIEW_COPY_FILES_SELECT_FOLDER = "gui_view_copy_files_select_folder";
    public static final String GUI_VIEW_EVALUATION = "gui_view_evaluation";
    public static final String GUI_VIEW_EVALUATION_FILLING = "gui_view_evaluation_filling";
    public static final String GUI_VIEW_EVALUATION_FILLING_GENERAL = "gui_view_evaluation_filling_general";
    public static final String GUI_VIEW_EVALUATION_FILLING_PROBABILITIES = "gui_view_evaluation_filling_probabilities";
    public static final String GUI_VIEW_EVALUATION_FILLING_PROBABILITIES_DISPLAY = "gui_view_evaluation_filling_probabilities_display";
    public static final String GUI_VIEW_EVALUATION_TELESCOPIC_CROWN = "gui_view_evaluation_telescopic_crown";
    public static final String GUI_VIEW_EVALUATION_TELESCOPIC_CROWN_GENERAL = "gui_view_evaluation_telescopic_crown_general";
    public static final String GUI_VIEW_EVALUATION_TELESCOPIC_CROWN_PROBABILITIES = "gui_view_evaluation_telescopic_crown_probabilities";
    public static final String GUI_VIEW_EVALUATION_TELESCOPIC_CROWN_PROBABILITIES_DISPLAY = "gui_view_evaluation_telescopic_crown_probabilities_display";
    public static final String GUI_VIEW_GENERAL_PATIENT_INFORMATION = "gui_view_general_patient_information";
    public static final String GUI_VIEW_IMPORT = "gui_view_import";
    public static final String GUI_VIEW_START = "gui_view_start";
    public static final String GUI_WINDOW_TITLE_MAIN = "Dentareport";
    public static final String GUI_WINDOW_TITLE_CONFIRM_EXIT = "Dentareport beenden?";
    public static final String GUI_WINDOW_TITLE_CONFIRM_CANCEL_EVALUATION = "Berechnung abbrechen?";
    public static final String GUI_WINDOW_TITLE_CONFIRM_CANCEL_IMPORT = "Datenimport abbrechen?";
    public static final String METADATA_DB_TABLENAME = "metadata";
    public static final String METADATA_KEY_VALID_EVALUATION_FILLING = "valid_evaluation_filling";
    public static final String METADATA_KEY_VALID_EVALUATION_TELESCOPIC_CROWN = "valid_evaluation_telescopic_crown";
    public static final String METADATA_KEY_VALID_IMPORT = "valid_import";
    public static final String DB_TABLENAME_OFFICE_EVALUATION_AVERAGES = "office_evaluation_averages";
    public static final String DB_TABLENAME_OFFICE_EVALUATION_COUNTS_PER_AGE_GROUP = "office_evaluation_counts_per_age_group";
    public static final String DB_TABLE_SUFFIX_AFR = "_afr";
    public static final String DB_TABLE_SUFFIX_KAPLAN_MEIER = "_kaplan_meier";
    public static final Map<String, String> EVIDENCE_CARIES = evidenceCaries();
    public static final String EVIDENCE_CARIES__NOT_TO_TREAT_WITHOUT_XRAY = EVIDENCE_CARIES.get(CARIES_NOT_TO_TREAT_WITHOUT_XRAY);
    public static final String EVIDENCE_CARIES__NOT_TO_TREAT_WITH_XRAY = EVIDENCE_CARIES.get(CARIES_NOT_TO_TREAT_WITH_XRAY);
    public static final String EVIDENCE_CARIES__NO_CARIES = EVIDENCE_CARIES.get(NO_CARIES);
    public static final String EVIDENCE_CARIES__TO_TREAT_WITHOUT_XRAY = EVIDENCE_CARIES.get(CARIES_TO_TREAT_WITHOUT_XRAY);
    public static final String EVIDENCE_CARIES__TO_TREAT_WITH_XRAY = EVIDENCE_CARIES.get(CARIES_TO_TREAT_WITH_XRAY);
    public static final String EVIDENCE_CARIES__UNKNOWN_SEVERITY = EVIDENCE_CARIES.get(CARIES_UNKNOWN_SEVERITY);
    public static final List<String> EVIDENCE_CARIES_WITHOUT_XRAY = evidenceCariesWithoutXray();
    public static final Map<String, String> EVIDENCE_STATUS = evidenceStatus();
    // TODO: Translate, check other constants too
    public static final String EVIDENCE_STATUS__BRUECKENGLIED = EVIDENCE_STATUS.get(BRUECKENGLIED);
    public static final String EVIDENCE_STATUS__ERSETZT = EVIDENCE_STATUS.get(ERSETZT);
    public static final String EVIDENCE_STATUS__FEHLEND = EVIDENCE_STATUS.get(FEHLEND);
    public static final List<String> EVIDENCE_STATUS__NO_TOOTH_CONTACT = evidenceStatusNoToothContact();
    public static final String EVIDENCE_STATUS__GESCHLOSSEN = EVIDENCE_STATUS.get(GESCHLOSSEN);
    public static final String EVIDENCE_STATUS__IMPLANTAT = EVIDENCE_STATUS.get(IMPLANT);
    public static final String EVIDENCE_STATUS__IM_DURCHBRUCH = EVIDENCE_STATUS.get(IM_DURCHBRUCH);
    public static final String EVIDENCE_STATUS__KRONE = EVIDENCE_STATUS.get(CROWN);
    public static final String EVIDENCE_STATUS__LUECKE_VERENGT_NACH_LINKS = EVIDENCE_STATUS.get(LUECKE_VERENGT_NACH_LINKS);
    public static final String EVIDENCE_STATUS__LUECKE_VERENGT_NACH_RECHTS = EVIDENCE_STATUS.get(LUECKE_VERENGT_NACH_RECHTS);
    public static final String EVIDENCE_STATUS__LUECKE_VERENGT_NACH_DISTAL = EVIDENCE_STATUS.get(LUECKE_VERENGT_NACH_DISTAL);
    public static final String EVIDENCE_STATUS__LUECKE_VERENGT_NACH_MESIAL = EVIDENCE_STATUS.get(LUECKE_VERENGT_NACH_MESIAL);
    public static final List<String> EVIDENCE_STATUS__MISSING_TEETH = evidenceStatusMissingTeeth();
    public static final String EVIDENCE_STATUS__SOUND = EVIDENCE_STATUS.get(SOUND);
    public static final String EVIDENCE_STATUS__TELESKOPKRONE = EVIDENCE_STATUS.get(TELESCOPIC_CROWN);
    private static final Map<String, String> EVIDENCE_SEALING = evidenceSealing();
    public static final String EVIDENCE_SEALING__NOT_SEALED = EVIDENCE_SEALING.get("not_sealed");
    public static final String EVIDENCE_SEALING__SEALED = EVIDENCE_SEALING.get("sealed");
    public static final String EVIDENCE_SEALING__SEALING_NECESSARY = EVIDENCE_SEALING.get("sealing_necessary");

    private static Map<String, String> evidenceSealing() {
        Map<String, String> ret = new HashMap<>();
        ret.put("not_sealed", "0");
        ret.put("sealing_necessary", "1");
        ret.put("sealed", "2");
        return ret;
    }

    private static Map<String, String> evidenceStatus() {
        Map<String, String> ret = new HashMap<>();
        ret.put(SOUND, ".");
        ret.put(BRUECKENGLIED, "b");
        ret.put(IM_DURCHBRUCH, "d");
        ret.put(ERSETZT, "e");
        ret.put(FEHLEND, "f");
        ret.put(IMPLANT, "i");
        ret.put(CROWN, "k");
        ret.put(GESCHLOSSEN, "l");
        ret.put(TELESCOPIC_CROWN, "t");
        ret.put(LUECKE_VERENGT_NACH_LINKS, "(");
        ret.put(LUECKE_VERENGT_NACH_RECHTS, ")");
        ret.put(LUECKE_VERENGT_NACH_DISTAL, ")(d");
        ret.put(LUECKE_VERENGT_NACH_MESIAL, ")(m");
        return ret;
    }

    private static List<String> evidenceStatusMissingTeeth() {
        return ImmutableList.of(
                EVIDENCE_STATUS__BRUECKENGLIED,
                EVIDENCE_STATUS__ERSETZT,
                EVIDENCE_STATUS__FEHLEND,
                EVIDENCE_STATUS__GESCHLOSSEN,
                EVIDENCE_STATUS__IMPLANTAT,
                EVIDENCE_STATUS__LUECKE_VERENGT_NACH_DISTAL,
                EVIDENCE_STATUS__LUECKE_VERENGT_NACH_MESIAL
        );
    }

    private static List<String> evidenceStatusNoToothContact() {
        return ImmutableList.of(
                EVIDENCE_STATUS__BRUECKENGLIED,
                EVIDENCE_STATUS__ERSETZT,
                EVIDENCE_STATUS__FEHLEND
        );
    }

    private static Map<String, String> evidenceCaries() {
        Map<String, String> ret = new HashMap<>();
        ret.put(NO_CARIES, "0");
        ret.put(CARIES_UNKNOWN_SEVERITY, "1");
        ret.put(CARIES_TO_TREAT_WITHOUT_XRAY, "2");
        ret.put(CARIES_TO_TREAT_WITH_XRAY, "2r");
        ret.put(CARIES_NOT_TO_TREAT_WITHOUT_XRAY, "3");
        ret.put(CARIES_NOT_TO_TREAT_WITH_XRAY, "3r");
        return ret;
    }

    private static List<String> evidenceCariesWithoutXray() {
        return ImmutableList.of(
                EVIDENCE_CARIES__UNKNOWN_SEVERITY,
                EVIDENCE_CARIES__TO_TREAT_WITHOUT_XRAY,
                EVIDENCE_CARIES__NOT_TO_TREAT_WITHOUT_XRAY
        );
    }

    private static List<String> surfaces() {
        return ImmutableList.of(
                SURFACE_MESIAL,
                SURFACE_DISTAL,
                SURFACE_OKKLUSAL,
                SURFACE_LINGUAL,
                SURFACE_VESTIBULAER,
                SURFACE_CERVIKAL
        );
    }
}
