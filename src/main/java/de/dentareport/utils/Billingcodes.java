package de.dentareport.utils;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.*;

public class Billingcodes {

    public static Map<String, Set<String>> billingcodes() {
        Map<String, Set<String>> ret = new HashMap<>();
        ret.put(Keys.BRUECKENANKER, ImmutableSet.of("91A", "91B", "91C", "500", "501", "502", "503", "5000", "5010",
                                                    "5020", "5030"));
        ret.put(Keys.CROWN, ImmutableSet.of("20A", "20B", "20C", "220", "221", "222", "2200", "2210", "2220"));
        ret.put(Keys.ERNEUERUNG_WURZELSTIFT, ImmutableSet.of("219", "2190"));
        ret.put(Keys.EXTRACTION, ImmutableSet.of("43", "300", "3000", "44", "301", "3010", "45", "302", "3020"));
        ret.put(Keys.FLUORIDIERUNG, ImmutableSet.of("102", "1020", "IP4"));
        ret.put(Keys.FUELLUNG, ImmutableSet.of("13A", "13B", "13C", "13D", "13E", "13F", "13G", "205", "205MK", "207",
                                               "207MK", "209", "209MK", "211", "211MK", "215A", "215MK", "216A",
                                               "216MK", "217A", "217MK", "217MK2", "2050", "2070", "2090", "2110",
                                               "218", "2180", "217", "2170", "215", "2150", "216", "2160"));
        ret.put(Keys.KOFFERDAM, ImmutableSet.of("12", "203", "204", "2030", "2040"));
        ret.put(Keys.MUNDGESUNDHEITSAUFKLAERUNG, ImmutableSet.of("101", "1010", "IP2"));
        ret.put(Keys.MUNDHYGIENESTATUS, ImmutableSet.of("100", "1000", "IP1"));
        ret.put(Keys.OSTEOTOMIE, ImmutableSet.of("47A", "48", "303", "304", "3030", "3040"));
        ret.put(Keys.PROVISORIUM, ImmutableSet.of("19", "21", "226", "227", "228", "512", "513", "708", "2270", "5120",
                                                  "7080"));
        ret.put(Keys.REZEMENTIERUNG, ImmutableSet.of("24A", "231", "2310", "95A", "95B", "511", "5110"));
        ret.put(Keys.SPLINT, ImmutableSet.of("701", "K1A", "700", "K2", "7010", "7000", "K3", "K1B", "K1C"));
        ret.put(Keys.TELESCOPIC_CROWN, ImmutableSet.of("91D", "504", "5040"));
        ret.put(Keys.VIPR, ImmutableSet.of("8", "007", "0070"));
        ret.put(Keys.VITE_TREP_WK, ImmutableSet.of("31", "239", "2390", "28", "236", "2360", "32", "241", "2410"));
        ret.put(Keys.WSR, ImmutableSet.of("54A", "54B", "54C", "311", "312", "3110", "3120"));
        ret.put(Keys.WURZELSTIFT, ImmutableSet.of("18", "18A", "18B", "2195"));
        ret.put(Keys.XRAY_EINZELBILD, ImmutableSet.of("Ä925A", "Ä925B", "Ä925C", "Ä5000"));
        ret.put(Keys.XRAY_OPG, ImmutableSet.of("Ä935D", "Ä5002", "Ä5004"));

        ret.put(Keys.TOOTH_LOSS, Sets.union(ret.get(Keys.EXTRACTION), ret.get(Keys.OSTEOTOMIE)));

        return ret;
    }

    public static List<String> billingcodesThatAllowHkpSearch() {
        List<String> ret = new ArrayList<>();
        ret.addAll(billingcodes().get(Keys.BRUECKENANKER));
        ret.addAll(billingcodes().get(Keys.CROWN));
        ret.addAll(billingcodes().get(Keys.TELESCOPIC_CROWN));
        ret.addAll(billingcodes().get(Keys.PROVISORIUM));
        return ret;
    }

    public static Set<String> forPosition(String billingposition) {
        return billingcodes().get(billingposition);
    }

    public static String forPositionAsString(String billingposition) {
        return String.join(", ", forPosition(billingposition));
    }

    public static Set<String> forPosition(Set<String> billingpositions) {
        Set<String> ret = new HashSet<>();
        for (String billingposition : billingpositions) {
            ret.addAll(forPosition(billingposition));
        }
        return ret;
    }

    public static String getBillingposition(String billingcode) {
        return billingcodes().keySet().stream()
                             .filter(b -> billingcodes().get(b).contains(billingcode))
                             .findFirst()
                             .orElse(Keys.NO_DATA);
    }
}
