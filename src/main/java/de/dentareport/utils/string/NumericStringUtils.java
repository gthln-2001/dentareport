package de.dentareport.utils.string;

import com.google.common.collect.ImmutableMap;
import de.dentareport.utils.Keys;

import java.util.Map;

// TODO: TEST?
public class NumericStringUtils {

    private static final double MINIMAL_NUMBER_OF_DAYS = 365d / 2d;
    private static final Map<String, Double> TIMEFRAMES = ImmutableMap.of(
            Keys.YEAR, 365d,
            Keys.HALF_YEAR, 365d / 2d
    );

    public static String countPerTimeframe(String count, String timeframe, String daysInTimeframe) {
        if (Double.parseDouble(daysInTimeframe) < MINIMAL_NUMBER_OF_DAYS) {
            return "";
        }
        return String.format(
                "%.3f",
                Double.parseDouble(count) /
                        (Double.parseDouble(daysInTimeframe) / TIMEFRAMES.get(timeframe))
        );
    }
}
