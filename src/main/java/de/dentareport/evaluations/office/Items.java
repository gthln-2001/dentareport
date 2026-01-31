package de.dentareport.evaluations.office;

import com.google.common.collect.ImmutableList;

import java.util.List;

// TODO: TEST?
public class Items {

    public List<Item> items() {
        return ImmutableList.of(
                // TODO: "anzahl_patienten_pro_jahr", //  es gilt 01 -> a) durchschnitt über Jahre, b) Kurver Verteilung pro Jahr
                new AgeLast01(),
                new PatientCountGroupedByAgeLast01(),
                new DmftLast01(),
                new DmftLast01GroupedByAgeLast01(),
                new ToothcountLast01(),
                new ToothcountLast01GroupedByAgeLast01()
                // TODO: "anzahl_implantate" // zählen in jeweils letzten 01
        );
    }
}
