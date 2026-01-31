package de.dentareport.utils.number;

// TODO: TEST?
public class DoubleUtils {

    public static Double roundDownToTenth(double value) {
        return Math.max(0, (double) (int) (value * 10)) / 10;
    }
}
