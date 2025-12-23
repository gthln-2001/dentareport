package de.dentareport.models;

import com.google.common.collect.ImmutableList;
import de.dentareport.utils.Keys;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static de.dentareport.utils.Keys.*;
import static de.dentareport.utils.string.ToothStringUtils.*;

public class Evidence01 implements Evidence01Interface {

    private final String billingcode;
    private final String date;
    private final String dft;
    private final String dmft;
    private final String dt;
    private final String ft;
    private final String mt;
    private final String toothcountQ1;
    private final String toothcountQ2;
    private final String toothcountQ3;
    private final String toothcountQ4;
    private final Map<String, String> status;
    private final Map<String, Map<String, String>> cariesSurfaces;
    private final Map<String, Map<String, String>> fillingSurfaces;

    public Evidence01(String date,
                      String billingcode,
                      String dft,
                      String dmft,
                      String dt,
                      String mt,
                      String ft,
                      String toothcountQ1,
                      String toothcountQ2,
                      String toothcountQ3,
                      String toothcountQ4,
                      Map<String, String> status,
                      Map<String, Map<String, String>> cariesSurfaces,
                      Map<String, Map<String, String>> fillingSurfaces
    ) {
        this.date = date;
        this.billingcode = billingcode;
        this.dft = dft;
        this.dmft = dmft;
        this.dt = dt;
        this.mt = mt;
        this.ft = ft;
        this.toothcountQ1 = toothcountQ1;
        this.toothcountQ2 = toothcountQ2;
        this.toothcountQ3 = toothcountQ3;
        this.toothcountQ4 = toothcountQ4;
        this.status = status;
        this.cariesSurfaces = cariesSurfaces;
        this.fillingSurfaces = fillingSurfaces;
    }

    @Override
    public boolean isCensored() {
        return true;
    }

    @Override
    public boolean hasValidSourceForBillingposition() {
        return true;
    }

    public String date() {
        return date;
    }

    @Override
    public String billingcode() {
        return billingcode;
    }

    @Override
    public String dt() {
        return dt;
    }

    @Override
    public String mt() {
        return mt;
    }

    @Override
    public String ft() {
        return ft;
    }

    @Override
    public String dmft() {
        return dmft;
    }

    @Override
    public String dft() {
        return dft;
    }

    @Override
    public String toothcount() {
        return String.valueOf(
                Integer.parseInt(toothcountQ1())
                + Integer.parseInt(toothcountQ2())
                + Integer.parseInt(toothcountQ3())
                + Integer.parseInt(toothcountQ4())
        );
    }

    @Override
    public String toothcountInJaw(String tooth) {
        if (isInJaw(tooth, Keys.UPPER_JAW)) {
            return String.valueOf(Integer.parseInt(toothcountQ1()) + Integer.parseInt(toothcountQ2()));
        }
        return String.valueOf(Integer.parseInt(toothcountQ3()) + Integer.parseInt(toothcountQ4()));
    }

    @Override
    public String toothcountQ1() {
        return toothcountQ1;
    }

    @Override
    public String toothcountQ2() {
        return toothcountQ2;
    }

    @Override
    public String toothcountQ3() {
        return toothcountQ3;
    }

    @Override
    public String toothcountQ4() {
        return toothcountQ4;
    }

    @Override
    public String endstaendigkeit(String tooth) {
        if (isMilktooth(tooth)) {
            return Keys.NO_DATA;
        }
        if (Integer.parseInt(position(tooth)) > 6) {
            return ""; // do not evalute Endständigkeit for 7th or 8th position
        }
        if (hasDistalToothContact(tooth)) {
            return Keys.ZAHNREIHE;
        }
        if (hasMesialToothContactInQuadrant(tooth)) {
            return Keys.ENDSTAENDIG;
        }
        return Keys.D_ENDSTAENDIG;
    }

    @Override
    public String toothcontacts(String tooth) {
        if (isMilktooth(tooth)) {
            return Keys.NO_DATA;
        }
        int count = 0;
        for (String neighbour : directNeighbours(tooth)) {
            if (isValidToothcontact(neighbour)) {
                count++;
            }
        }
        return String.valueOf(count);
    }

    @Override
    public String status(String tooth) {
        // TODO: Rethink how to handle milkteeth here...
        if (isMilktooth(tooth)) {
            return Keys.NO_DATA;
        }
        return status.get(tooth);
    }

    @Override
    public Boolean hasStatus(String tooth, String status) {
        String s = this.status.get(tooth);
        String orDefault = EVIDENCE_STATUS.getOrDefault(status, status);

        return s.equals(orDefault);
    }

    @Override
    public Boolean hasBillingcode(Set<String> billingcodes) {
        return billingcodes.contains(billingcode());
    }

    @Override
    public Map<String, String> cariesSurfaces(String tooth) {
        return cariesSurfaces.get(tooth);
    }

    @Override
    public List<String> cariesSurfaces(String tooth, String cariesSpecification) {
        // TODO: Rethink handling of milk teeth
        if (isMilktooth(tooth)) {
            return ImmutableList.of();
        }
        Predicate<Map.Entry<String, String>> filter;
        if (cariesSpecification.equals(CARIES_TO_TREAT)) {
            filter = entrySet -> (entrySet.getValue().equals(EVIDENCE_CARIES.get(CARIES_TO_TREAT_WITH_XRAY)) || entrySet
                    .getValue()
                    .equals(EVIDENCE_CARIES.get(CARIES_TO_TREAT_WITHOUT_XRAY)));
        } else if (cariesSpecification.equals(CARIES_NOT_TO_TREAT)) {
            filter = entrySet -> (entrySet.getValue().equals(EVIDENCE_CARIES.get(CARIES_NOT_TO_TREAT_WITH_XRAY))
                                  || entrySet.getValue().equals(EVIDENCE_CARIES.get(CARIES_NOT_TO_TREAT_WITHOUT_XRAY)));
        } else {
            filter = entrySet -> entrySet.getValue().equals(EVIDENCE_CARIES.get(cariesSpecification));
        }
        return cariesSurfaces(tooth).entrySet()
                                    .stream()
                                    .filter(filter)
                                    .map(Map.Entry::getKey)
                                    .sorted()
                                    .collect(Collectors.toList());
    }

    @Override
    public String cariesSpecification(String tooth, String surface) {
        // TODO: Rethink handling of milk teeth
        if (isMilktooth(tooth)) {
            return "";
        }
        return cariesSurfaces.get(tooth).get(surface);
    }

    @Override
    public String countCariesSurfaces(String tooth, String cariesSpecification) {
        return String.valueOf(cariesSurfaces(tooth, cariesSpecification).size());
    }

    @Override
    public String filling(String tooth, String surface) {
        // TODO: Rethink handling of milk teeth
        if (isMilktooth(tooth)) {
            return "";
        }
        return fillingSurfaces.get(tooth).get(surface);
    }

    @Override
    public String countFillingSurfaces(String tooth) {
        // TODO: Rethink handling of milk teeth
        if (isMilktooth(tooth)) {
            return "";
        }

        return String.valueOf(fillingSurfaces.get(tooth)
                                             .entrySet()
                                             .stream()
                                             .filter(entrySet -> !entrySet.getValue().equals(FILLING__NO_FILLING))
                                             .count());
    }

    private boolean hasDistalToothContact(String tooth) {
        for (String neighbour : neighboursDistal(tooth, 3)) {
            if (isValidToothcontact(neighbour)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasMesialToothContactInQuadrant(String tooth) {
        for (String neighbour : neighboursMesial(tooth, 3)) {
            if (inSameQuadrant(tooth, neighbour) && isValidToothcontact(neighbour)) {
                return true;
            }
        }
        return false;
    }

    private boolean inSameQuadrant(String tooth1,
                                   String tooth2) {
        return Objects.equals(quadrant(tooth1), quadrant(tooth2));
    }

    private boolean isValidToothcontact(String tooth) {
        return !Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT.contains(status(tooth));
    }
}