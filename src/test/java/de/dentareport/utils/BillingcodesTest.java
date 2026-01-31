package de.dentareport.utils;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class BillingcodesTest {

    @Test
    public void it_gets_billingcodes() {
        assertThat(Billingcodes.billingcodes().get(Keys.CROWN).contains("220")).isTrue();
    }

    @Test
    public void it_gets_billingcodes_for_billingposition() {
        Set<String> billingcodes = Billingcodes.forPosition(Keys.TELESCOPIC_CROWN);
        assertThat(billingcodes.size()).isEqualTo(3);
        assertThat(billingcodes).contains("91D");
        assertThat(billingcodes).contains("504");
        assertThat(billingcodes).contains("5040");
    }

    @Test
    public void it_gets_billingcodes_as_string_for_billingposition() {
        assertThat(Billingcodes.forPositionAsString(Keys.TELESCOPIC_CROWN)).isEqualTo("91D, 504, 5040");
    }

    @Test
    public void it_gets_billingcodes_for_set_of_billingpositions() {
        Set<String> billingcodes = Billingcodes.forPosition(ImmutableSet.of(
                Keys.TELESCOPIC_CROWN,
                Keys.ERNEUERUNG_WURZELSTIFT
        ));
        assertThat(billingcodes.size()).isEqualTo(5);
        assertThat(billingcodes).contains("91D");
        assertThat(billingcodes).contains("504");
        assertThat(billingcodes).contains("5040");
        assertThat(billingcodes).contains("219");
        assertThat(billingcodes).contains("2190");
    }

    @Test
    public void it_gets_billingposition_for_billingcode() {
        assertThat(Billingcodes.getBillingposition("91D")).isEqualTo(Keys.TELESCOPIC_CROWN);
        assertThat(Billingcodes.getBillingposition("219")).isEqualTo(Keys.ERNEUERUNG_WURZELSTIFT);
        assertThat(Billingcodes.getBillingposition("something_unknown")).isEqualTo(Keys.NO_DATA);
    }

    @Test
    public void it_gets_billing_codes_that_allow_search_for_treatment_in_hkp() {
        assertThat(Billingcodes.billingcodesThatAllowHkpSearch()).isInstanceOf(List.class);
    }
}