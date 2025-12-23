package de.dentareport.utils;

import de.dentareport.models.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Treatments {

    private final RawData rawData;
    private final CaseData caseData;
    private String until;
    private String billingposition;
    private String from;
    private String after;
    private String before;
    private Set<String> billingpositions;
    private String only;

    public Treatments(RawData rawData, CaseData caseData) {
        this.rawData = rawData;
        this.caseData = caseData;
    }

    public Treatments from(String from) {
        this.from = from;
        return this;
    }

    public Treatments after(String after) {
        this.after = after;
        return this;
    }

    public Treatments before(String before) {
        this.before = before;
        return this;
    }

    public Treatments until(String until) {
        this.until = until;
        return this;
    }

    public Treatments withBillingposition(String billingposition) {
        this.billingposition = billingposition;
        return this;
    }

    public Treatments withBillingpositions(Set<String> billingpositions) {
        this.billingpositions = billingpositions;
        return this;
    }

    public Treatments filterOnly(String filter) {
        this.only = filter;
        return this;
    }

    public TreatmentInterface first() {
        return buildStream().findFirst().orElse(new NullTreatment());
    }

    public TreatmentInterface firstOnDentition() {
        return buildStreamOnDentition().findFirst().orElse(new NullTreatment());
    }

    public String firstDate() {
        return first().date();
    }

    public String firstDateOnDentition() {
        return firstOnDentition().date();
    }

    public TreatmentInterface last() {
        return buildStream().reduce((first, second) -> second) /* Get the last element of the stream*/
                            .orElse(new NullTreatment());
    }

    public TreatmentInterface lastOnDentition() {
        return buildStreamOnDentition().reduce((first, second) -> second) /* Get the last element of the stream*/
                                       .orElse(new NullTreatment());
    }

    public String lastDate() {
        return last().date();
    }

    public String lastDateOnDentition() {
        return lastOnDentition().date();
    }

    public Eventlist list() {
        Eventlist eventlist = new Eventlist();
        buildStream().forEach(eventlist::addEvent);
        return eventlist;
    }

    public String count() {
        return String.valueOf(buildStream().count());
    }

    public String countOnDentition() {
        return String.valueOf(buildStreamOnDentition().count());
    }

    public String countFillingSurfaces() {
        Set<String> billingcodes = Billingcodes.forPosition(Keys.FUELLUNG);
        return String.valueOf(buildStream()
                                      .filter(t -> t.hasBillingcode(billingcodes))
                                      .map(t -> t.surfaces().size())
                                      .mapToInt(Integer::intValue)
                                      .sum());
    }

    public String countFillingSurfacesOnDentition() {
        Set<String> billingcodes = Billingcodes.forPosition(Keys.FUELLUNG);
        return String.valueOf(buildStreamOnDentition()
                                      .filter(t -> t.hasBillingcode(billingcodes))
                                      .map(t -> t.surfaces().size())
                                      .mapToInt(Integer::intValue)
                                      .sum());
    }

    private Stream<TreatmentInterface> buildStreamOnDentition() {
        return filterTreatments(rawData.treatments());
    }

    private Stream<TreatmentInterface> buildStream() {
        return filterTreatments(rawData.treatmentsOnEvaluatedTooth());
    }

    private Stream<TreatmentInterface> filterTreatments(List<TreatmentInterface> treatments) {
        Stream<TreatmentInterface> filteredTreatments = treatments.stream();
        if (from != null) {
            filteredTreatments = filteredTreatments.filter(t -> t.isFrom(from));
        }
        if (after != null) {
            filteredTreatments = filteredTreatments.filter(t -> t.isAfter(after));
        }
        if (before != null) {
            filteredTreatments = filteredTreatments.filter(t -> t.isBefore(before));
        }
        if (until != null) {
            filteredTreatments = filteredTreatments.filter(t -> t.isUntil(until));
        }
        if (billingposition != null) {
            filteredTreatments = filteredTreatments
                    .filter(t -> t.hasBillingcode(Billingcodes.forPosition(billingposition)))
                    .filter(EventInterface::hasValidSourceForBillingposition);
        }
        if (billingpositions != null) {
            filteredTreatments = filteredTreatments
                    .filter(t -> t.hasBillingcode(Billingcodes.forPosition(
                            billingpositions)))
                    .filter(EventInterface::hasValidSourceForBillingposition);
        }
        if (only != null) {
            filteredTreatments = filteredTreatments
                    .filter(TreatmentFilters.get(only, caseData));
        }
        return filteredTreatments;
    }
}
