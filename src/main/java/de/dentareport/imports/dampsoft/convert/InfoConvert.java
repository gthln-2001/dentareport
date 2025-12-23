package de.dentareport.imports.dampsoft.convert;

import de.dentareport.utils.string.ToothStringUtils;

import java.util.*;

public class InfoConvert {

    public static String convertToBillingCode(String info,
                                              String type) {
        if (isInvalidType(type)) {
            return "";
        }
        return info.substring(2, 8).toUpperCase().trim();
    }

    public static String convertToQuantity(String info,
                                           String type) {
        if (isInvalidType(type)) {
            return "";
        }
        return info.substring(23, 24).replaceAll("[^0-9]", "");
    }

    public static String convertToSurfaces(String info,
                                           String type) {
        if (isInvalidType(type)) {
            return "";
        }
        return convertSurfaceString(info.substring(8, 18).trim().toLowerCase());
    }

    public static String convertToTooth(String info,
                                        String type) {
        if (isInvalidType(type)) {
            return "";
        }
        return ToothStringUtils.isTooth(info.substring(0, 2))
                ? info.substring(0, 2)
                : "";
    }

    private static String sortAndJoinSet(Set<String> stringSet) {
        List<String> ret = new ArrayList<>(stringSet);
        Collections.sort(ret);
        return String.join(",", ret);
    }

    private static String removeFoundKeyFromSurfaceString(String surfaceString,
                                                          String surfaceKey) {
        return surfaceString.replaceAll(surfaceKey, "");
    }

    private static boolean isInvalidType(String type) {
        return !Objects.equals(type, "LG") && !Objects.equals(type, "LK");
    }

    static String convertSurfaceString(String surfaceString) {
        Set<String> ret = new HashSet<>();
        for (String surfaceKey : DampsoftKeys.SURFACE_KEYS) {
            if (surfaceString.contains(surfaceKey)) {
                ret.add(DampsoftKeys.SURFACES.get(surfaceKey));
                surfaceString = removeFoundKeyFromSurfaceString(surfaceString, surfaceKey); // This is necessary to avoid invalid double hits, e.g "inc" would be found as "inc" and as "i"
            }
        }
        return sortAndJoinSet(ret);
    }
}
