package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.EventInterface;
import de.dentareport.models.RawData;
import de.dentareport.utils.Keys;
import de.dentareport.utils.Treatments;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import static de.dentareport.utils.Keys.*;

// TODO: Test!
// TODO: TEST?
public class Cofferdam extends EvaluationColumn {

    public Cofferdam(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData, CaseData caseData) {
        caseData.setString(options.get(ORIGINAL_NAME), value(rawData, caseData));
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return String.format("~~%s~~ ##%s##", KOFFERDAM, options.get(AT));
    }

    @Override
    public String documentationLongDe() {
        return "Gebührennummer 12/203/2030 plus 'Kofferdam' oder 'Cofferdam' im Kommentar oder Gebührennummer 204/2040";
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of(options.get(AT));
    }

    @Override
    public Set<String> requiredBillingpositions() {
        return ImmutableSet.of(KOFFERDAM);
    }

    private String value(RawData rawData, CaseData caseData) {
        String date = caseData.event(options.get(AT)).date();
        List<EventInterface> treatments = new Treatments(rawData, caseData).from(date)
                                                                           .until(date)
                                                                           .withBillingposition(Keys.KOFFERDAM)
                                                                           .list()
                                                                           .eventlist();

        return treatments.stream().anyMatch(validKofferdamTreatment()) ? "1" : "0";
    }

    private Predicate<EventInterface> validKofferdamTreatment() {
        return t -> t.hasBillingcode(ImmutableSet.of("204", "2040"))
                    || t.comment().toLowerCase().contains("kofferdam")
                    || t.comment().toLowerCase().contains("cofferdam");
    }
}
