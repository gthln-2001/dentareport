package de.dentareport;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class Glossary {

    public Map<String, String> glossary() {
        return ImmutableMap.<String, String>builder()
                .put(
                        "Anzahl Zähne",
                        "Anzähl Zähne, die im 01-Befund nicht als Brückenglied, ersetzt, fehlend, geschlossen, " +
                        "Implantat, Lücke verengt links oder Lücke verengt rechts markiert sind.")
                .put(
                        "DFT",
                        "Anzahl Zähne, die im 01-Befund entweder für DT oder FT gezählt werden.")
                .put(
                        "DMFT",
                        "Anzahl Zähne, die im 01-Befund entweder für DT, MT oder FT gezählt werden.")
                .put(
                        "DT",
                        "Anzahl Zähne, die im 01-Befund mindestens eine Fläche haben, die mit Karies zu behandeln " +
                        "ohne Röntgen, Karies nicht zu behandeln ohne Röntgen oder Karies unbekannter " +
                        "Ausprägung markiert ist. 8er werden nicht gezählt.")
                .put(
                        "Endständigkeit",
                        "1: Wird nicht für 7er und 8er ermittelt. 2: Unter den drei distalen " +
                        "Nachbarzähnen gibt es mindestens einen, der nicht als Brückenglied, " +
                        "ersetzt oder fehlend markiert ist -> Zahnreihe. 3: Keine Zahnreihe " +
                        "und unter den drei mesialen Nachbarzähnen (begrenzt auf Quadrant) " +
                        "gibt es mindestens einen, der nicht als Brückenglied, ersetzt oder " +
                        "fehlend markiert ist -> Endständig. 4: Nicht Zahnreihe und nicht " +
                        "Endständig -> D-Endständig.")
                .put(
                        "Ereignis n",
                        "Als Ereignis gilt: Füllung, Krone, Rezementierung, Wurzelstift, " +
                        "Erneuerung Wurzelstift, Extraktion, Osteotomie, VitE/Trep/WK, WSR")
                .put(
                        "FT",
                        "Anzahl Zähne, die im 01-Befund entweder als Krone markiert sind oder " +
                        "mindestens eine Fläche haben, die mit Füllung markiert ist. 8er " +
                        "werden nicht gezählt.")
                .put(
                        "MT",
                        "Anzähl Zähne, die im 01-Befund als Brückenglied, ersetzt, fehlend, " +
                        "geschlossen, Implantat, Lücke verengt links oder Lücke verengt rechts " +
                        "markiert sind. 8er werden nicht gezählt.")
                .put(
                        "Zahnkontakte",
                        "Anzahl direkter Nachbarzähne, die im 01-Befund nicht als Brückenglied, " +
                        "ersetzt oder fehlend markiert sind.")
                .build();
    }
}
