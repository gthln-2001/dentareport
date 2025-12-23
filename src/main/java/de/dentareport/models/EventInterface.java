package de.dentareport.models;

import de.dentareport.utils.Billingcodes;
import de.dentareport.utils.Keys;
import de.dentareport.utils.string.DateStringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public interface EventInterface {

    boolean isCensored();

    boolean hasValidSourceForBillingposition();

    default boolean isAfter(String date) {
        return DateStringUtils.isAfter(date(), date);
    }

    default boolean isUntil(String date) {
        return DateStringUtils.isUntil(date(), date);
    }

    default boolean isBefore(String date) {
        return DateStringUtils.isBefore(date(), date);
    }

    default boolean isFrom(String date) {
        return DateStringUtils.isFrom(date(), date);
    }

    default boolean isFromHkp() {
        return Objects.equals(source(), "hkp");
    }

    default String censored() {
        if (isCensored()) {
            return Keys.CENSORED_YES;
        }
        return Keys.CENSORED_NO;
    }

    String date();

    default String year() {
        return DateStringUtils.yearOf(date());
    }

    String billingcode();

    default String billingposition() {
        return Billingcodes.getBillingposition(billingcode());
    }

    default boolean isOnTooth(String tooth) {
        return true;
    }

    String dt();

    String mt();

    String ft();

    String dmft();

    String dft();

    String toothcount();

    String toothcountInJaw(String tooth);

    String toothcountQ1();

    String toothcountQ2();

    String toothcountQ3();

    String toothcountQ4();

    String endstaendigkeit(String tooth);

    String toothcontacts(String tooth);

    String status(String tooth);

    Boolean hasStatus(String tooth, String status);

    String tooth();

    List<String> surfaces();

    String handler();

    String insurance();

    String source();

    Boolean hasBillingcode(Set<String> billingcodes);

    String comment();

    Map<String, String> cariesSurfaces(String tooth);

    List<String> cariesSurfaces(String tooth, String cariesSpecification);

    String cariesSpecification(String tooth, String surface);

    String countCariesSurfaces(String tooth, String cariesSpecification);

    String filling(String tooth, String surfaces);

    String countFillingSurfaces(String tooth);
}
