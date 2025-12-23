package de.dentareport.evaluations.columns;

import de.dentareport.evaluations.Evaluation;
import de.dentareport.models.CaseData;
import de.dentareport.models.EventInterface;
import de.dentareport.models.RawData;
import de.dentareport.utils.Keys;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static de.dentareport.evaluations.columns.Helper.options;
import static de.dentareport.evaluations.columns.Helper.testCase;
import static org.assertj.core.api.Assertions.assertThat;

public class InsuranceTest {

    @Test
    public void it_evaluates_data_if_event_start_observation_is_marked_as_gesetzlich_versichert(
            @Mocked Evaluation mockEvaluation,
            @Mocked RawData mockRawData,
            @Mocked EventInterface mockEventInterface) {

        CaseData caseData = testCase();
        caseData.setEvent("event_start_observation", mockEventInterface);

        for (String dampsoftKey : Keys.DAMPSOFT_INSURANCE_GESETZLICH) {
            new Expectations() {{
                mockEventInterface.insurance();
                result = dampsoftKey;
            }};

            EvaluationColumn column = new Insurance(mockEvaluation, options());

            CaseData result = column.evaluate(mockRawData, caseData);

            assertThat(result.string("insurance")).isEqualTo(Keys.INSURANCE_GESETZLICH);
        }
    }

    @Test
    public void it_evaluates_data_if_event_start_observation_is_marked_as_privat_versichert(
            @Mocked Evaluation mockEvaluation,
            @Mocked RawData mockRawData,
            @Mocked EventInterface mockEventInterface) {

        CaseData caseData = testCase();
        caseData.setEvent("event_start_observation", mockEventInterface);

        for (String dampsoftKey : Keys.DAMPSOFT_INSURANCE_PRIVAT) {
            new Expectations() {{
                mockEventInterface.insurance();
                result = dampsoftKey;
            }};

            EvaluationColumn column = new Insurance(mockEvaluation, options());

            CaseData result = column.evaluate(mockRawData, caseData);

            assertThat(result.string("insurance")).isEqualTo(Keys.INSURANCE_PRIVAT);
        }
    }

    @Test
    public void it_evaluates_data_if_event_start_observation_has_unclear_insurance_status(
            @Mocked Evaluation mockEvaluation,
            @Mocked RawData mockRawData,
            @Mocked EventInterface mockEventStatObservation,
            @Mocked EventInterface mockEvidence) {

        CaseData caseData = testCase();
        caseData.setEvent("event_start_observation", mockEventStatObservation);
        caseData.setEvent("evidence_01__position__last__before__date_start_observation", mockEvidence);

        Map<String, String> provider = new HashMap<>();
        provider.put("01", Keys.INSURANCE_GESETZLICH);
        provider.put("001", Keys.INSURANCE_PRIVAT);
        provider.put("0010", Keys.INSURANCE_PRIVAT);
        provider.put("something-else", Keys.INSURANCE_GESETZLICH);

        for (String billingcode : provider.keySet()) {
            new Expectations() {{
                mockEventStatObservation.insurance();
                result = "some-unclear-status";
                mockEvidence.billingcode();
                result = billingcode;
            }};
            EvaluationColumn column = new Insurance(mockEvaluation, options());

            CaseData result = column.evaluate(mockRawData, caseData);

            assertThat(result.string("insurance")).isEqualTo(provider.get(billingcode));
        }
    }
}