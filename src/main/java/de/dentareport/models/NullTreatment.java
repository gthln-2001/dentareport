package de.dentareport.models;

import de.dentareport.utils.Keys;

import java.util.Set;

// TODO: TEST?
public class NullTreatment implements TreatmentInterface {

    @Override
    public String date() {
        return Keys.NO_DATA;
    }

    @Override
    public String year() {
        return Keys.NO_DATA;
    }

    @Override
    public boolean isCensored() {
        return false;
    }

    @Override
    public boolean hasValidSourceForBillingposition() {
        return true;
    }

    @Override
    public String billingcode() {
        return Keys.NO_DATA;
    }

    @Override
    public String tooth() {
        return Keys.NO_DATA;
    }

    @Override
    public String handler() {
        return Keys.NO_DATA;
    }

    @Override
    public String insurance() {
        return Keys.NO_DATA;
    }

    @Override
    public String source() {
        return Keys.NO_DATA;
    }

    @Override
    public Boolean hasBillingcode(Set<String> billingcodes) {
        return false;
    }

    @Override
    public String comment() {
        return Keys.NO_DATA;
    }
}