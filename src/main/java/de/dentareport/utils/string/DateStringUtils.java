package de.dentareport.utils.string;

import de.dentareport.exceptions.DentareportParseException;
import de.dentareport.utils.Keys;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static de.dentareport.utils.string.StringUtils.isNoData;
import static de.dentareport.utils.string.StringUtils.isNumeric;

public class DateStringUtils {

    private final static Set<String> validDates = validDates();
    private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static boolean isValidDate(String value) {
        return validDates.contains(value);
    }

    public static String daysBetween(String date1, String date2) {
        if (datesAreNotValid(date1, date2)) {
            return Keys.NO_DATA;
        }
        return String.valueOf(ChronoUnit.DAYS.between(LocalDate.parse(date1, dateFormatter),
                                                      LocalDate.parse(date2, dateFormatter)));
    }

    public static String daysBetween(String date1, String date2, String fallback) {
        if (isNoData(date2)) {
            return daysBetween(date1, fallback);
        }
        return daysBetween(date1, date2);
    }

    public static String daysBetweenWithOffset(String date1, String date2, Integer offset) {
        if (datesAreNotValid(date1, date2)) {
            return Keys.NO_DATA;
        }
        long daysBetween = ChronoUnit.DAYS.between(LocalDate.parse(date1, dateFormatter),
                                                   LocalDate.parse(date2, dateFormatter));
        return String.valueOf(daysBetween + offset);
    }

    public static String yearsBetween(String date1, String date2) {
        if (datesAreNotValid(date1, date2)) {
            return Keys.NO_DATA;
        }
        return String.valueOf(ChronoUnit.YEARS.between(
                LocalDate.parse(date1, dateFormatter),
                LocalDate.parse(date2, dateFormatter))
        );
    }

    public static String age(String dateOfBirth, String date) {
        String age = yearsBetween(dateOfBirth, date);
        if (isInvalidAge(age)) {
            return Keys.NO_DATA;
        }
        return age;
    }

    public static String groupAgeByDecade(String age) {
        if (!isNumeric(age)) {
            return Keys.NO_DATA;
        }
        if (Integer.parseInt(age) <= 19) {
            return "0-19";
        }
        int decade = Integer.parseInt(age) / 10;
        return String.format("%s-%s", decade * 10, (decade * 10 + 9));
    }

    public static boolean isAfter(String date1, String date2) {
        if (isNoData(date1)) {
            return false;
        }
        if (isNoData(date2)) {
            return true;
        }
        Date d1 = parse(date1);
        Date d2 = parse(date2);
        return d1.after(d2);
    }

    public static boolean isFrom(String date1, String date2) {
        if (isNoData(date1)) {
            return false;
        }
        if (isNoData(date2)) {
            return true;
        }

        Date d1 = parse(date1);
        Date d2 = parse(date2);
        return d1.equals(d2) || d1.after(d2);
    }

    public static boolean isBefore(String date1, String date2) {
        if (isNoData(date1)) {
            return false;
        }
        if (isNoData(date2)) {
            return true;
        }

        Date d1 = parse(date1);
        Date d2 = parse(date2);
        return d1.before(d2);
    }

    public static boolean isUntil(String date1, String date2) {
        if (isNoData(date1)) {
            return false;
        }
        if (isNoData(date2)) {
            return true;
        }

        Date d1 = parse(date1);
        Date d2 = parse(date2);
        return d1.before(d2) || d1.equals(d2);
    }

    public static String convertDateFromCharlyToGregorian(String charlyDate) {
        int charly = Integer.parseInt(charlyDate);
        charly += 1; // Charly uses julian convert format, but their implementation is one day off...
        return convertDateFromJulianToGregorian(charly);
    }

    public static String reformatSqlToGerman(String sqlDate) {
        if (!isValidDate(sqlDate)) {
            return sqlDate;
        }
        String[] chunks = sqlDate.split("-");
        return String.format("%s.%s.%s", chunks[2], chunks[1], chunks[0]);
    }

    public static String now() {
        return new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss").format(new Date());
    }

    private static Date parse(String date) {
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new DentareportParseException(e);
        }
    }

    private static boolean datesAreNotValid(String date1, String date2) {
        return !isValidDate(date1) || !isValidDate(date2);
    }

    private static boolean isInvalidAge(String age) {
        return isNoData(age) || Integer.parseInt(age) < 0;
    }

    /**
     * @see <a href="https://de.wikipedia.org/wiki/Julianisches_Datum">https://de.wikipedia.org/wiki/Julianisches_Datum</a>
     * @see <a href="http://www.hermetic.ch/cal_stud/jdn.htm">http://www.hermetic.ch/cal_stud/jdn.htm</a>
     */
    private static String convertDateFromJulianToGregorian(int julian) {
        int l = julian + 68569;
        int n = (4 * l) / 146097;
        l = l - (146097 * n + 3) / 4;
        int i = (4000 * (l + 1)) / 1461001;
        l = l - (1461 * i) / 4 + 31;
        int j = (80 * l) / 2447;
        int day = l - (2447 * j) / 80;
        l = j / 11;
        int month = j + 2 - (12 * l);
        int year = 100 * (n - 49) + i + l;
        return String.format("%s-%s-%s",
                             year,
                             StringUtils.leftPad(String.valueOf(month), 2, "0"),
                             StringUtils.leftPad(String.valueOf(day), 2, "0")
        );
    }

    private static Set<String> validDates() {
        Set<String> dates = new HashSet<>();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        for (int year = 1880; year <= currentYear; year++) {
            dates.addAll(generateDatesForYear(year));
        }
        return dates;
    }

    private static Set<String> generateDatesForYear(int year) {
        Set<String> dates = new HashSet<>();
        for (int month = 1; month <= 12; month++) {
            dates.addAll(generateDatesForMonth(year, month));
        }
        return dates;
    }

    private static Set<String> generateDatesForMonth(int year, int month) {
        Set<String> dates = new HashSet<>();
        for (int day = 1; day <= daysInMonth(year, month); day++) {
            dates.add(String.format("%04d-%02d-%02d", year, month, day));
        }
        return dates;
    }

    private static int daysInMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                if (isLeapYear(year)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return 30;
        }
    }

    private static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    public static String yearOf(String date) {
        if (date.equals("") || date.equals(Keys.NO_DATA)) {
            return date;
        }
        return date.substring(0, 4);
    }
}
