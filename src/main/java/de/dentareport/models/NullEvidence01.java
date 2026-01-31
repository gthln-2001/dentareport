package de.dentareport.models;

import com.google.common.collect.ImmutableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static de.dentareport.utils.Keys.NO_DATA;

// TODO: TEST?
public class NullEvidence01 implements Evidence01Interface {

    @Override
    public String date() {
        return NO_DATA;
    }

    @Override
    public String year() {
        return NO_DATA;
    }

    @Override
    public String billingcode() {
        return NO_DATA;
    }

    @Override
    public String dt() {
        return NO_DATA;
    }

    @Override
    public String mt() {
        return NO_DATA;
    }

    @Override
    public String ft() {
        return NO_DATA;
    }

    @Override
    public String dmft() {
        return NO_DATA;
    }

    @Override
    public String dft() {
        return NO_DATA;
    }

    @Override
    public String toothcount() {
        return NO_DATA;
    }

    @Override
    public String toothcountInJaw(String tooth) {
        return NO_DATA;
    }

    @Override
    public String toothcountQ1() {
        return NO_DATA;
    }

    @Override
    public String toothcountQ2() {
        return NO_DATA;
    }

    @Override
    public String toothcountQ3() {
        return NO_DATA;
    }

    @Override
    public String toothcountQ4() {
        return NO_DATA;
    }

    @Override
    public String endstaendigkeit(String tooth) {
        return NO_DATA;
    }

    @Override
    public String toothcontacts(String tooth) {
        return NO_DATA;
    }

    @Override
    public String status(String tooth) {
        return NO_DATA;
    }

    @Override
    public Boolean hasStatus(String tooth, String status) {
        return false;
    }

    @Override
    public Boolean hasBillingcode(Set<String> billingcodes) {
        return false;
    }

    @Override
    public Map<String, String> cariesSurfaces(String tooth) {
        return new HashMap<>();
    }

    @Override
    public List<String> cariesSurfaces(String tooth, String cariesSpecification) {
        return ImmutableList.of();
    }

    @Override
    public String cariesSpecification(String tooth, String surfaces) {
        return NO_DATA;
    }

    @Override
    public String countCariesSurfaces(String tooth, String cariesSpecification) {
        return NO_DATA;
    }

    @Override
    public String filling(String tooth, String surface) {
        return NO_DATA;
    }

    @Override
    public String countFillingSurfaces(String tooth) {
        return NO_DATA;
    }

    @Override
    public boolean isCensored() {
        return false;
    }

    @Override
    public boolean hasValidSourceForBillingposition() {
        return true;
    }
}