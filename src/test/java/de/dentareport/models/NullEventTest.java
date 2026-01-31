package de.dentareport.models;

import com.google.common.collect.ImmutableSet;
import de.dentareport.utils.Keys;
import de.dentareport.utils.Toothnumbers;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static de.dentareport.utils.Keys.EVIDENCE_STATUS;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class NullEventTest {

    @Test
    public void it_can_be_initialized() {
        NullEvent event = new NullEvent();

        assertThat(event.date()).isEqualTo(Keys.NO_DATA);
        assertThat(event.tooth()).isEqualTo(Keys.NO_DATA);
        assertThat(event.billingcode()).isEqualTo(Keys.NO_DATA);
        assertThat(event.handler()).isEqualTo(Keys.NO_DATA);
        assertThat(event.insurance()).isEqualTo(Keys.NO_DATA);
        assertThat(event.source()).isEqualTo(Keys.NO_DATA);
        assertThat(event.dmft()).isEqualTo(Keys.NO_DATA);
        assertThat(event.dt()).isEqualTo(Keys.NO_DATA);
        assertThat(event.mt()).isEqualTo(Keys.NO_DATA);
        assertThat(event.ft()).isEqualTo(Keys.NO_DATA);
        assertThat(event.toothcount()).isEqualTo(Keys.NO_DATA);
        assertThat(event.toothcountQ1()).isEqualTo(Keys.NO_DATA);
        assertThat(event.toothcountQ2()).isEqualTo(Keys.NO_DATA);
        assertThat(event.toothcountQ3()).isEqualTo(Keys.NO_DATA);
        assertThat(event.toothcountQ4()).isEqualTo(Keys.NO_DATA);
        for (String toothnumber : Toothnumbers.ALL) {
            assertThat(event.toothcountInJaw(toothnumber)).isEqualTo(Keys.NO_DATA);
            assertThat(event.endstaendigkeit(toothnumber)).isEqualTo(Keys.NO_DATA);
            assertThat(event.toothcontacts(toothnumber)).isEqualTo(Keys.NO_DATA);
            assertThat(event.status(toothnumber)).isEqualTo(Keys.NO_DATA);
            EVIDENCE_STATUS.forEach((key, value) -> {
                assertThat(event.hasStatus(toothnumber, key)).isFalse();
                assertThat(event.hasStatus(toothnumber, value)).isFalse();
            });
        }
    }

    @Test
    public void it_censors_event() {
        NullEvent event = new NullEvent();

        assertThat(event.censored()).isEqualTo(Keys.CENSORED_NO);
        assertThat(event.isCensored()).isFalse();
    }

    @Test
    public void it_checks_for_valid_source() {
        NullEvent event = new NullEvent();

        assertThat(event.hasValidSourceForBillingposition()).isTrue();
    }

    @Test
    public void it_checks_for_valid_tooth() {
        NullEvent event = new NullEvent();
        Toothnumbers.ALL_WITH_MILKTEETH.forEach(toothnumber -> assertThat(event.isOnTooth(toothnumber)).isTrue());
    }

    @Test
    public void it_checks_if_billingcode_is_in_given_list_of_billingcodes() {
        NullEvent event = new NullEvent();
        Set<String> billingcodes = ImmutableSet.of("Foo", "Bar");

        assertThat(event.hasBillingcode(billingcodes)).isFalse();
    }
}