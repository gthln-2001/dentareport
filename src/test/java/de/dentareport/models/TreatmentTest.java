package de.dentareport.models;

import com.google.common.collect.ImmutableSet;
import de.dentareport.utils.Billingcodes;
import de.dentareport.utils.Keys;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class TreatmentTest {

    @Test
    public void it_can_be_initialized() {
        Treatment treatment = new Treatment("a", "b", "c", "", "d", "e", "comment", "f");

        assertThat(treatment.date()).isEqualTo("a");
        assertThat(treatment.tooth()).isEqualTo("b");
        assertThat(treatment.billingcode()).isEqualTo("c");
        assertThat(treatment.handler()).isEqualTo("d");
        assertThat(treatment.insurance()).isEqualTo("e");
        assertThat(treatment.comment()).isEqualTo("comment");
        assertThat(treatment.source()).isEqualTo("f");
    }

    @Test
    public void it_censors_treatment() {
        Treatment treatment = new Treatment("a", "b", "c", "", "d", "e", "comment", "f");

        assertThat(treatment.censored()).isEqualTo(Keys.CENSORED_YES);
        assertThat(treatment.isCensored()).isTrue();
    }

    @Test
    public void it_gets_billingposition(@Mocked Billingcodes mockBillingcodes) {
        Treatment treatment = new Treatment("a", "b", "c", "", "d", "e", "comment", "f");

        new Expectations() {{
            Billingcodes.getBillingposition("c");
            result = "Foo";
        }};

        assertThat(treatment.billingposition()).isEqualTo("Foo");
    }

    @Test
    public void it_checks_if_treatment_is_on_given_tooth() {
        Treatment treatment = new Treatment("a", "b", "c", "", "d", "e", "comment", "f");

        assertThat(treatment.isOnTooth("b")).isTrue();
        assertThat(treatment.isOnTooth("x")).isFalse();
    }

    @Test
    public void it_checks_if_billingcode_of_treatment_is_in_given_list_of_billingcodes() {
        Treatment treatment1 = new Treatment("a", "b", "c", "", "d", "e", "comment", "f");
        Treatment treatment2 = new Treatment("g", "h", "i", "", "j", "k", "comment", "l");
        Set<String> billingcodes = ImmutableSet.of("Foo", "Bar", "c");

        assertThat(treatment1.hasBillingcode(billingcodes)).isTrue();
        assertThat(treatment2.hasBillingcode(billingcodes)).isFalse();
    }

    @Test
    public void it_checks_if_date_is_after_given_date() {
        Treatment treatment = new Treatment("1990-01-01", "b", "c", "", "d", "e", "comment", "f");

        assertThat(treatment.isAfter("1980-01-01")).isTrue();
        assertThat(treatment.isAfter("1990-01-01")).isFalse();
        assertThat(treatment.isAfter("2000-01-01")).isFalse();
    }

    @Test
    public void it_checks_if_date_is_before_given_date() {
        Treatment treatment = new Treatment("1990-01-01", "b", "c", "", "d", "e", "comment", "f");

        assertThat(treatment.isBefore("1980-01-01")).isFalse();
        assertThat(treatment.isBefore("1990-01-01")).isFalse();
        assertThat(treatment.isBefore("2000-01-01")).isTrue();
    }

    @Test
    public void it_checks_if_date_is_until_given_date() {
        Treatment treatment = new Treatment("1990-01-01", "b", "c", "", "d", "e", "comment", "f");

        assertThat(treatment.isUntil("1980-01-01")).isFalse();
        assertThat(treatment.isUntil("1990-01-01")).isTrue();
        assertThat(treatment.isUntil("2000-01-01")).isTrue();
    }

    @Test
    public void it_checks_if_date_is_from_given_date() {
        Treatment treatment = new Treatment("1990-01-01", "b", "c", "", "d", "e", "comment", "f");

        assertThat(treatment.isFrom("1980-01-01")).isTrue();
        assertThat(treatment.isFrom("1990-01-01")).isTrue();
        assertThat(treatment.isFrom("2000-01-01")).isFalse();
    }

    @Test
    public void it_checks_if_source_is_hkp() {
        Treatment treatment1 = new Treatment("a", "b", "c", "", "d", "e", "comment", "f");
        Treatment treatment2 = new Treatment("a", "b", "c", "", "d", "e", "comment", "hkp");

        assertThat(treatment1.isFromHkp()).isFalse();
        assertThat(treatment2.isFromHkp()).isTrue();
    }

    @Test
    public void it_checks_for_valid_source() {
        Treatment treatment;

        for (String billingcode : Billingcodes.billingcodesThatAllowHkpSearch()) {
            treatment = new Treatment("a", "b", billingcode, "", "d", "e", "comment", "f");
            assertThat(treatment.hasValidSourceForBillingposition()).isTrue();

            treatment = new Treatment("a", "b", billingcode, "", "d", "e", "comment", "hkp");
            assertThat(treatment.hasValidSourceForBillingposition()).isTrue();
        }

        treatment = new Treatment("a", "b", "some_other_billingcode", "", "d", "e", "comment", "f");
        assertThat(treatment.hasValidSourceForBillingposition()).isTrue();

        treatment = new Treatment("a", "b", "some_other_billingcode", "", "d", "e", "comment", "hkp");
        assertThat(treatment.hasValidSourceForBillingposition()).isFalse();
    }

    @Test
    public void it_gets_surfaces(@Mocked Billingcodes mockBillingcodes) {
        Treatment treatment = new Treatment("a", "b", "c", "s1,s2,s3", "d", "e", "comment", "f");

        List<String> surfaces = treatment.surfaces();

        assertThat(surfaces.size()).isEqualTo(3);
        assertThat(surfaces.contains("s1")).isTrue();
        assertThat(surfaces.contains("s2")).isTrue();
        assertThat(surfaces.contains("s3")).isTrue();
    }
}