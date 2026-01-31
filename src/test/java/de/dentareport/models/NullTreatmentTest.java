package de.dentareport.models;

import com.google.common.collect.ImmutableSet;
import de.dentareport.utils.Keys;
import de.dentareport.utils.Toothnumbers;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class NullTreatmentTest {

    @Test
    public void it_can_be_initialized() {
        NullTreatment treatment = new NullTreatment();

        assertThat(treatment.date()).isEqualTo(Keys.NO_DATA);
        assertThat(treatment.tooth()).isEqualTo(Keys.NO_DATA);
        assertThat(treatment.billingcode()).isEqualTo(Keys.NO_DATA);
        assertThat(treatment.handler()).isEqualTo(Keys.NO_DATA);
        assertThat(treatment.insurance()).isEqualTo(Keys.NO_DATA);
        assertThat(treatment.source()).isEqualTo(Keys.NO_DATA);
    }

    @Test
    public void it_censors_treatment() {
        NullTreatment treatment = new NullTreatment();

        assertThat(treatment.censored()).isEqualTo(Keys.CENSORED_NO);
        assertThat(treatment.isCensored()).isFalse();
    }

    @Test
    public void it_checks_for_valid_source() {
        NullTreatment treatment = new NullTreatment();

        assertThat(treatment.hasValidSourceForBillingposition()).isTrue();
    }

    @Test
    public void it_checks_if_billingcode_of_treatment_is_in_given_list_of_billingcodes() {
        NullTreatment treatment = new NullTreatment();
        Set<String> billingcodes = ImmutableSet.of("Foo", "Bar");

        assertThat(treatment.hasBillingcode(billingcodes)).isFalse();
    }

    @Test
    public void it_checks_for_valid_tooth() {
        NullTreatment treatment = new NullTreatment();
        Toothnumbers.ALL_WITH_MILKTEETH.forEach(toothnumber -> assertThat(treatment.isOnTooth(toothnumber)).isTrue());
    }
}