package de.dentareport.evaluations;

import com.google.common.collect.ImmutableSet;
import de.dentareport.models.CaseData;
import de.dentareport.models.EventInterface;
import de.dentareport.models.RawData;
import de.dentareport.models.TreatmentInterface;
import de.dentareport.utils.Keys;
import de.dentareport.utils.Treatments;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class Evaluation9Test {

    @Test
    public void all_required_columns_for_this_evaluation_are_in_list_of_available_columns() {
        // When we new up an Evaluation a method to prepare the required columns is called.
        // There we check if all colums are in the list of available columns. If not, we throw an exception.
        // If we remove this test, that exception would be thrown at runtime, not at compile time.
        new Evaluation9();
    }

    @Test
    public void it_gets_event_start_observation(@Mocked Treatments mockTreatments,
                                                @Mocked RawData mockRawData,
                                                @Mocked TreatmentInterface mockTreatment) {
        Set<String> billingPositions = ImmutableSet.of(
                Keys.TELESCOPIC_CROWN
        );
        CaseData caseData = new CaseData("foo");
        new Expectations() {{
            new Treatments(mockRawData, caseData).withBillingpositions(billingPositions).first();
        }};
        Evaluation evaluation = new Evaluation9();

        EventInterface event = evaluation.eventStartObservation(mockRawData, caseData);
        assertThat(event).isEqualTo(mockTreatment);
    }

    @Test
    public void it_gets_event_end_observation(@Mocked Treatments mockTreatments,
                                              @Mocked RawData mockRawData,
                                              @Mocked TreatmentInterface mockTreatment) {

        Set<String> billingPositions = ImmutableSet.of(
                Keys.BRUECKENANKER,
                Keys.EXTRACTION,
                Keys.CROWN,
                Keys.OSTEOTOMIE,
                Keys.TELESCOPIC_CROWN,
                Keys.VITE_TREP_WK
        );
        CaseData caseData = new CaseData("foo");
        caseData.setString("date__of__event_start_observation", "some-date");
        new Expectations() {{
            new Treatments(mockRawData, caseData).after("some-date").withBillingpositions(billingPositions).first();
        }};
        Evaluation evaluation = new Evaluation9();

        EventInterface event = evaluation.eventEndObservation(mockRawData, caseData);
        assertThat(event).isEqualTo(mockTreatment);
    }
}