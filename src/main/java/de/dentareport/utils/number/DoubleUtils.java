package de.dentareport.utils.number;

public class DoubleUtils {

    public static Double roundDownToTenth(double value) {
        return Math.max(0, (double) (int) (value * 10)) / 10;
    }
}
