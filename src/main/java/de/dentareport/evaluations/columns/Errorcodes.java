package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;
import de.dentareport.utils.Billingcodes;
import de.dentareport.utils.Keys;
import de.dentareport.utils.Log;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static de.dentareport.utils.Keys.ORIGINAL_NAME;

public class Errorcodes extends EvaluationColumn {

    public Errorcodes(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData, CaseData caseData) {
        caseData.setString(options.get(ORIGINAL_NAME), StringUtils.join(errorcodes(caseData), ", "));
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return "Fehlercodes";
    }

    @Override
    public String documentationLongDe() {
        return "Debugspalte, prüft Fälle auf logische Fehler in Abhängigkeit vom Auswertungstyp. 100: Befund fehlend im letzten 01 vor Start Beobachtung. 101: letzte Restauration leer. 102: Gebührennummer bei Start Beobachtung passt nicht zu Anzahl behandelter Flächen bei Start Beobachtung.";
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of(
                "billingcode__of__event_start_observation",
                "evidence_01__position__last__before__date_start_observation",
                "surfaces__of__event_start_observation",
                "tooth"
        );
    }

    private List<String> errorcodes(CaseData caseData) {
        List<String> ret = new ArrayList<>();
        if (hasError100(caseData)) {
            ret.add("100");
        }
        if (hasError101(caseData)) {
            ret.add("101");
        }
        if (hasError102(caseData)) {
            ret.add("102");
        }

        return ret;
    }

    private boolean hasError100(CaseData caseData) {
        return caseData.event("evidence_01__position__last__before__date_start_observation")
                       .status(caseData.string("tooth"))
                       .equals(Keys.EVIDENCE_STATUS__FEHLEND);
    }

    // TODO: Implement & Test
    private boolean hasError101(CaseData caseData) {
        // Code 101: letzte Restauration leer
//        if ($this->zeile['ereignis_letzte_restauration_ab_start_beobachtung_bis_vor_ereignis_zahnverlust'] == '') {
//            $fehler[] = '101';
//        }
        return false;
    }

    // TODO: Test
    private boolean hasError102(CaseData caseData) {
        String billingCode = caseData.string("billingcode__of__event_start_observation");
        int countSurfaces = caseData.string("surfaces__of__event_start_observation").split(",").length;
        if (!Billingcodes.getBillingposition(billingCode).equals(Keys.FUELLUNG)) {
            return false;
        }
        switch (billingCode) {
            case "13A":
            case "13E":
            case "205":
            case "2050":
            case "215":
            case "2150":
                return countSurfaces != 1;
            case "13B":
            case "13F":
            case "207":
            case "2070":
            case "216":
            case "2160":
                return countSurfaces != 2;
            case "13C":
            case "209":
            case "2090":
                return countSurfaces != 3;
            case "13G":
            case "217":
            case "2170":
                return countSurfaces < 3;
            case "13D":
            case "211":
            case "2110":
                return countSurfaces < 4;
        }
        return false;
    }
}
