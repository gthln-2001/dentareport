package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;
import de.dentareport.utils.Treatments;

import java.util.List;
import java.util.Map;

import static de.dentareport.utils.Keys.DATE_END_SEARCH_PERIOD;
import static de.dentareport.utils.Keys.TOOTH_LOSS;
import static de.dentareport.utils.string.StringUtils.isNoData;

public class DateEndSearchPeriod extends DateEvaluationColumn {

    public DateEndSearchPeriod(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData, CaseData caseData) {
        String dateToothLoss = dateToothLoss(rawData, caseData);
        caseData.setString(DATE_END_SEARCH_PERIOD,
                           isNoData(dateToothLoss) ? caseData.string("date_last_visit") : dateToothLoss
        );
        return caseData;
    }

    @Override
    public String documentationShortDe() {
        return "Ende Suchzeitraum";
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of(
                "date_start_observation",
                "date_last_visit"
        );
    }

    private String dateToothLoss(RawData rawData, CaseData caseData) {
        return new Treatments(rawData, caseData).after(caseData.string("date_start_observation"))
                                                .withBillingposition(TOOTH_LOSS)
                                                .firstDate();
    }
}
