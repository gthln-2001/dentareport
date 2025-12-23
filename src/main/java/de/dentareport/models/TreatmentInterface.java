package de.dentareport.models;

import com.google.common.collect.ImmutableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.dentareport.utils.Keys.NO_DATA;

public interface TreatmentInterface extends EventInterface {

    default String dt() {
        return NO_DATA;
    }

    default String mt() {
        return NO_DATA;
    }

    default String ft() {
        return NO_DATA;
    }

    default String dmft() {
        return NO_DATA;
    }

    default Map<String, String> cariesSurfaces(String tooth) {
        return new HashMap<>();
    }

    default List<String> cariesSurfaces(String tooth, String cariesSpecification) {
        return ImmutableList.of();
    }

    default String cariesSpecification(String tooth, String surface) {
        return NO_DATA;
    }

    default String countCariesSurfaces(String tooth, String cariesSpecification) {
        return NO_DATA;
    }

    default String filling(String tooth, String surface) {
        return NO_DATA;
    }

    default String countFillingSurfaces(String tooth) {
        return NO_DATA;
    }

    default String dft() {
        return NO_DATA;
    }

    default String toothcount() {
        return NO_DATA;
    }

    default String toothcountInJaw(String tooth) {
        return NO_DATA;
    }

    default String toothcountQ1() {
        return NO_DATA;
    }

    default String toothcountQ2() {
        return NO_DATA;
    }

    default String toothcountQ3() {
        return NO_DATA;
    }

    default String toothcountQ4() {
        return NO_DATA;
    }

    default String endstaendigkeit(String tooth) {
        return NO_DATA;
    }

    default String toothcontacts(String tooth) {
        return NO_DATA;
    }

    default String status(String tooth) {
        return NO_DATA;
    }

    default Boolean hasStatus(String tooth, String status) {
        return false;
    }

    default List<String> surfaces() {
        return ImmutableList.of();
    }
}
