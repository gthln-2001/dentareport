package de.dentareport.utils;

import com.google.common.collect.ImmutableSet;
import de.dentareport.models.*;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static de.dentareport.evaluations.columns.Helper.testCase;
import static org.assertj.core.api.Assertions.assertThat;

public class TreatmentsTest {

    private static final String BILLINGPOSITION = "Foo";
    private static final ImmutableSet<String> BILLINGPOSITIONS = ImmutableSet.of(BILLINGPOSITION);
    private static final String BILLINGCODE_FOO = "foo";
    private static final String BILLINGCODE_BAR = "bar";
    private static final String BILLINGCODE_BIZ = "biz";
    private static final String BILLINGPOSITION_NON_EXISTENT = "non_existent";
    private static final ImmutableSet<String> BILLINGPOSITIONS_NON_EXISTENT = ImmutableSet.of(
            BILLINGPOSITION_NON_EXISTENT);
    private static final String BILLINGCODE_FILLING = "billing_code_filling";
    private static final String BILLINGCODE_NO_FILLING = "billing_code_no_filling";

    @Mocked
    Billingcodes mockBillingcodes;
    @Mocked
    RawData mockRawData;
    @Mocked
    RawData mockRawDataFillings;
    private CaseData caseData;

    @BeforeEach
    public void setUp() {
        new Expectations() {{
            Billingcodes.billingcodesThatAllowHkpSearch();
            minTimes = 0;
            result = new ArrayList<>();

            Billingcodes.forPosition(BILLINGPOSITION);
            minTimes = 0;
            result = ImmutableSet.of(BILLINGCODE_FOO);

            Billingcodes.forPosition(BILLINGPOSITIONS);
            minTimes = 0;
            result = ImmutableSet.of(BILLINGCODE_FOO);

            Billingcodes.forPosition(Keys.FUELLUNG);
            minTimes = 0;
            result = ImmutableSet.of(BILLINGCODE_FILLING);

            mockRawData.treatments();
            minTimes = 0;
            result = testTreatmentsOnDentition();

            mockRawData.treatmentsOnEvaluatedTooth();
            minTimes = 0;
            result = testTreatmentsOnEvaluatedTooth();

            mockRawDataFillings.treatments();
            minTimes = 0;
            result = testFillingsOnDentition();

            mockRawDataFillings.treatmentsOnEvaluatedTooth();
            minTimes = 0;
            result = testFillingsOnEvaluatedTooth();
        }};
        caseData = testCase();
    }

    @Test
    public void it_gets_first_treatment() {
        TreatmentInterface result = new Treatments(mockRawData, caseData).first();
        assertThat(result.date()).isEqualTo("1982-01-01");
    }

    @Test
    public void it_gets_date_of_first_treatment() {
        String result = new Treatments(mockRawData, caseData).firstDate();
        assertThat(result).isEqualTo("1982-01-01");
    }

    @Test
    public void it_gets_last_treatment() {
        TreatmentInterface result = new Treatments(mockRawData, caseData).last();
        assertThat(result.date()).isEqualTo("1985-01-01");
    }

    @Test
    public void it_gets_date_of_last_treatment() {
        String result = new Treatments(mockRawData, caseData).lastDate();
        assertThat(result).isEqualTo("1985-01-01");
    }

    @Test
    public void it_gets_first_treatment_on_dentition() {
        TreatmentInterface result = new Treatments(mockRawData, caseData).firstOnDentition();
        assertThat(result.date()).isEqualTo("2000-01-01");
    }

    @Test
    public void it_gets_first_date_on_dentition() {
        String result = new Treatments(mockRawData, caseData).firstDateOnDentition();
        assertThat(result).isEqualTo("2000-01-01");
    }

    @Test
    public void it_gets_last_treatment_on_dentition() {
        TreatmentInterface result = new Treatments(mockRawData, caseData).lastOnDentition();
        assertThat(result.date()).isEqualTo("2011-01-01");
    }

    @Test
    public void it_gets_last_date_on_dentition() {
        String result = new Treatments(mockRawData, caseData).lastDateOnDentition();
        assertThat(result).isEqualTo("2011-01-01");
    }

    @Test
    public void it_filters_billingposition() {
        TreatmentInterface result = new Treatments(mockRawData, caseData).withBillingposition(BILLINGPOSITION).first();
        assertThat(result.date()).isEqualTo("1984-01-01");
    }

    @Test
    public void it_returns_null_treatment_if_billingposition_does_not_exist() {
        TreatmentInterface result = new Treatments(mockRawData,
                                                   caseData).withBillingposition(BILLINGPOSITION_NON_EXISTENT).first();
        assertThat(result).isInstanceOf(NullTreatment.class);
    }

    @Test
    public void it_filters_billingpositions() {
        TreatmentInterface result = new Treatments(mockRawData, caseData).withBillingpositions(BILLINGPOSITIONS)
                                                                         .first();
        assertThat(result.date()).isEqualTo("1984-01-01");
    }

    @Test
    public void it_returns_null_treatment_if_billingpositions_does_not_exist() {
        TreatmentInterface result = new Treatments(mockRawData,
                                                   caseData).withBillingpositions(BILLINGPOSITIONS_NON_EXISTENT)
                                                            .first();
        assertThat(result).isInstanceOf(NullTreatment.class);
    }

    @Test
    public void it_filters_from_date() {
        TreatmentInterface result = new Treatments(mockRawData, caseData).from("1984-01-01").first();
        assertThat(result.date()).isEqualTo("1984-01-01");
    }

    @Test
    public void it_filters_after_date() {
        TreatmentInterface result = new Treatments(mockRawData, caseData).after("1983-01-01").first();
        assertThat(result.date()).isEqualTo("1984-01-01");
    }

    @Test
    public void it_filters_before_date() {
        TreatmentInterface result = new Treatments(mockRawData, caseData).before("1984-01-01").last();
        assertThat(result.date()).isEqualTo("1983-01-01");
    }

    @Test
    public void it_filters_until_date() {
        TreatmentInterface result = new Treatments(mockRawData, caseData).until("1984-01-01").last();
        assertThat(result.date()).isEqualTo("1984-01-01");
    }

    @Test
    public void it_applies_treatment_filters(@Mocked TreatmentFilters treatmentFilters) {
        Predicate<TreatmentInterface> filter = t -> t.isAfter("1983-01-01");
        new Expectations() {{
            TreatmentFilters.get("some-filter", caseData);
            result = filter;
        }};
        TreatmentInterface result = new Treatments(mockRawData, caseData).filterOnly("some-filter").first();
        assertThat(result.date()).isEqualTo("1984-01-01");
    }

    @Test
    public void it_combines_filters() {
        String result = new Treatments(mockRawData, caseData).from("2004-01-01")
                                                             .before("2010-01-01")
                                                             .withBillingposition(BILLINGPOSITION)
                                                             .lastDateOnDentition();
        assertThat(result).isEqualTo("2007-01-01");
    }

    @Test
    public void it_gets_eventlist() {
        Eventlist result = new Treatments(mockRawData, caseData).after("1983-01-01")
                                                                .withBillingpositions(BILLINGPOSITIONS)
                                                                .list();
        assertThat(result.eventlist().size()).isEqualTo(2);
        assertThat(result.event("1").date()).isEqualTo("1984-01-01");
        assertThat(result.event("2").date()).isEqualTo("1985-01-01");
    }

    @Test
    public void it_counts_treatments_on_dentition() {
        String result = new Treatments(mockRawData, caseData).withBillingposition(BILLINGPOSITION).countOnDentition();
        assertThat(result).isEqualTo("8");
    }

    @Test
    public void it_counts_treatments() {
        String result = new Treatments(mockRawData, caseData).withBillingposition(BILLINGPOSITION).count();
        assertThat(result).isEqualTo("2");
    }

    @Test
    public void it_counts_filling_surfaces_on_dentition() {
        String result = new Treatments(mockRawDataFillings, caseData).countFillingSurfacesOnDentition();
        assertThat(result).isEqualTo("19");
    }

    @Test
    public void it_counts_filling_surfaces() {
        String result = new Treatments(mockRawDataFillings, caseData).countFillingSurfaces();
        assertThat(result).isEqualTo("18");
    }

    private List<Treatment> testTreatmentsOnEvaluatedTooth() {
        List<Treatment> ret = new ArrayList<>();

        ret.add(new Treatment("1982-01-01", "42", BILLINGCODE_BIZ, "", "j", "k", "", "l"));
        ret.add(new Treatment("1983-01-01", "42", BILLINGCODE_FOO, "", "j", "k", "", "hkp"));
        ret.add(new Treatment("1984-01-01", "42", BILLINGCODE_FOO, "", "j", "k", "", "l"));
        ret.add(new Treatment("1985-01-01", "42", BILLINGCODE_FOO, "", "j", "k", "", "l"));

        return ret;
    }

    private List<Treatment> testTreatmentsOnDentition() {
        List<Treatment> ret = new ArrayList<>();

        ret.add(new Treatment("2000-01-01", "11", BILLINGCODE_BIZ, "", "d", "e", "", "f"));
        ret.add(new Treatment("2001-01-01", "12", BILLINGCODE_FOO, "", "j", "k", "", "l"));
        ret.add(new Treatment("2002-01-01", "42", BILLINGCODE_BIZ, "", "j", "k", "", "l"));
        ret.add(new Treatment("2003-01-01", "42", BILLINGCODE_FOO, "", "j", "k", "", "l"));
        ret.add(new Treatment("2004-01-01", "42", BILLINGCODE_FOO, "", "j", "k", "", "l"));
        ret.add(new Treatment("2005-01-01", "42", BILLINGCODE_FOO, "", "j", "k", "", "l"));
        ret.add(new Treatment("2006-01-01", "42", BILLINGCODE_FOO, "", "j", "k", "", "l"));
        ret.add(new Treatment("2007-01-01", "12", BILLINGCODE_FOO, "", "j", "k", "", "l"));
        ret.add(new Treatment("2008-01-01", "12", BILLINGCODE_BAR, "", "j", "k", "", "l"));
        ret.add(new Treatment("2009-01-01", "12", BILLINGCODE_BAR, "", "j", "k", "", "l"));
        ret.add(new Treatment("2010-01-01", "42", BILLINGCODE_FOO, "", "j", "k", "", "l"));
        ret.add(new Treatment("2011-01-01", "42", BILLINGCODE_FOO, "", "j", "k", "", "l"));

        return ret;
    }

    private List<Treatment> testFillingsOnDentition() {
        List<Treatment> ret = new ArrayList<>();

        ret.add(new Treatment("1982-01-01", "42", BILLINGCODE_FILLING, "a,b,c", "j", "k", "", "l"));
        ret.add(new Treatment("1982-01-01", "42", BILLINGCODE_NO_FILLING, "a,b,c", "j", "k", "", "l"));
        ret.add(new Treatment("1982-01-01", "42", BILLINGCODE_FILLING, "", "j", "k", "", "l"));
        ret.add(new Treatment("1982-01-01", "42", BILLINGCODE_NO_FILLING, "", "j", "k", "", "l"));
        ret.add(new Treatment("1982-01-01", "23", BILLINGCODE_FILLING, "a", "j", "k", "", "l"));
        ret.add(new Treatment("1983-01-01", "42", BILLINGCODE_FILLING, "a", "j", "k", "", "hkp"));
        ret.add(new Treatment("1984-01-01", "42", BILLINGCODE_FILLING, "a,b", "j", "k", "", "l"));
        ret.add(new Treatment("1985-01-01", "42", BILLINGCODE_FILLING, "a,b,c", "j", "k", "", "l"));
        ret.add(new Treatment("1986-01-01", "42", BILLINGCODE_FILLING, "a,b,c,d", "j", "k", "", "l"));
        ret.add(new Treatment("1987-01-01", "42", BILLINGCODE_FILLING, "a,b,c,d,e", "j", "k", "", "l"));

        return ret;
    }

    private List<Treatment> testFillingsOnEvaluatedTooth() {
        List<Treatment> ret = new ArrayList<>();

        ret.add(new Treatment("1982-01-01", "42", BILLINGCODE_FILLING, "a,b,c", "j", "k", "", "l"));
        ret.add(new Treatment("1982-01-01", "42", BILLINGCODE_NO_FILLING, "a,b,c", "j", "k", "", "l"));
        ret.add(new Treatment("1982-01-01", "42", BILLINGCODE_FILLING, "", "j", "k", "", "l"));
        ret.add(new Treatment("1982-01-01", "42", BILLINGCODE_NO_FILLING, "", "j", "k", "", "l"));
        ret.add(new Treatment("1983-01-01", "42", BILLINGCODE_FILLING, "a", "j", "", "k", "hkp"));
        ret.add(new Treatment("1984-01-01", "42", BILLINGCODE_FILLING, "a,b", "j", "k", "", "l"));
        ret.add(new Treatment("1985-01-01", "42", BILLINGCODE_FILLING, "a,b,c", "j", "k", "", "l"));
        ret.add(new Treatment("1986-01-01", "42", BILLINGCODE_FILLING, "a,b,c,d", "j", "k", "", "l"));
        ret.add(new Treatment("1987-01-01", "42", BILLINGCODE_FILLING, "a,b,c,d,e", "j", "k", "", "l"));

        return ret;
    }
}