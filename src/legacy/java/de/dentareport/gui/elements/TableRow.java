package de.dentareport.gui.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: TEST?
public class TableRow {

    private List<String> columns;

    public TableRow(String... values) {
        columns = new ArrayList<>(Arrays.asList(values));
    }

    public void addColumn(String value) {
        columns.add(value);
    }

    public String getColumn1() {
        return columns.get(0);
    }

    public void setColumn1(String value) {
        columns.set(0, value);
    }

    public String getColumn2() {
        return columns.get(1);
    }

    public void setColumn2(String value) {
        columns.set(1, value);
    }

    public String getColumn3() {
        return columns.get(2);
    }

    public void setColumn3(String value) {
        columns.set(2, value);
    }

    public String getColumn4() {
        return columns.get(3);
    }

    public void setColumn4(String value) {
        columns.set(3, value);
    }

    public String getColumn5() {
        return columns.get(4);
    }

    public void setColumn5(String value) {
        columns.set(4, value);
    }

    public String getColumn6() {
        return columns.get(5);
    }

    public void setColumn6(String value) {
        columns.set(5, value);
    }

    public String getColumn7() {
        return columns.get(6);
    }

    public void setColumn7(String value) {
        columns.set(6, value);
    }

    public String getColumn8() {
        return columns.get(7);
    }

    public void setColumn8(String value) {
        columns.set(7, value);
    }

    public String getColumn9() {
        return columns.get(8);
    }

    public void setColumn9(String value) {
        columns.set(8, value);
    }

    public String getColumn10() {
        return columns.get(9);
    }

    public void setColumn10(String value) {
        columns.set(9, value);
    }

    public String getColumn11() {
        return columns.get(10);
    }

    public void setColumn11(String value) {
        columns.set(10, value);
    }

    public String getColumn12() {
        return columns.get(11);
    }

    public void setColumn12(String value) {
        columns.set(11, value);
    }

    public String getColumn13() {
        return columns.get(12);
    }

    public void setColumn13(String value) {
        columns.set(12, value);
    }

    public String getColumn14() {
        return columns.get(13);
    }

    public void setColumn14(String value) {
        columns.set(13, value);
    }

    public String getColumn15() {
        return columns.get(14);
    }

    public void setColumn15(String value) {
        columns.set(14, value);
    }
}