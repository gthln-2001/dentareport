package de.dentareport.models;

import de.dentareport.utils.Billingcodes;

import java.util.*;

// TODO: TEST?
public class Treatment implements TreatmentInterface {

    private final List<String> surfaces;
    private final String billingcode;
    private final String date;
    private final String handler;
    private final String insurance;
    private final String comment;
    private final String source;
    private final String tooth;

    public Treatment(String date,
                     String tooth,
                     String billingcode,
                     String surfaces,
                     String handler,
                     String insurance,
                     String comment,
                     String source) {
        this.date = date;
        this.tooth = tooth;
        this.surfaces = !surfaces.equals("") ? Arrays.asList(surfaces.split(",")) : new ArrayList<>();
        this.billingcode = billingcode;
        this.handler = handler;
        this.insurance = insurance;
        this.comment = comment;
        this.source = source;
    }

    @Override
    public boolean isCensored() {
        return true;
    }

    @Override
    public boolean hasValidSourceForBillingposition() {
        return !isFromHkp() || billingcodeAllowsHkpSearch();
    }

    public String date() {
        return date;
    }

    public String billingcode() {
        return billingcode;
    }

    public String tooth() {
        return tooth;
    }

    public List<String> surfaces() {
        return surfaces;
    }

    public String handler() {
        return handler;
    }

    public String insurance() {
        return insurance;
    }

    public String source() {
        return source;
    }

    public Boolean hasBillingcode(Set<String> billingcodes) {
        return billingcodes.contains(billingcode());
    }

    @Override
    public String comment() {
        return comment;
    }

    public boolean isOnTooth(String tooth) {
        return Objects.equals(tooth, tooth());
    }

    private boolean billingcodeAllowsHkpSearch() {
        return Billingcodes.billingcodesThatAllowHkpSearch().contains(billingcode());
    }
}