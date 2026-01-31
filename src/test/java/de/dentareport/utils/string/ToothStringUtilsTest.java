package de.dentareport.utils.string;

import com.google.common.collect.ImmutableList;
import de.dentareport.utils.Keys;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.dentareport.utils.string.ToothStringUtils.splitToothrange;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class ToothStringUtilsTest {

    @Test
    public void it_splits_toothrange_into_list_of_toothnumbers() {
        assertThat(splitToothrange("")).isEqualTo(ImmutableList.of(""));
        assertThat(splitToothrange("14")).isEqualTo(ImmutableList.of("14"));
        assertThat(splitToothrange("54")).isEqualTo(ImmutableList.of("54"));
        assertThat(splitToothrange("14, 15")).isEqualTo(ImmutableList.of("14", "15"));
        assertThat(splitToothrange(" 61   , 24    ")).isEqualTo(ImmutableList.of("24", "61"));
        assertThat(splitToothrange("14,16,18")).isEqualTo(ImmutableList.of("14", "16", "18"));
        assertThat(splitToothrange("14,16,14")).isEqualTo(ImmutableList.of("14", "16"));
        assertThat(splitToothrange("24-26")).isEqualTo(ImmutableList.of("24", "25", "26"));
        assertThat(splitToothrange("35-31")).isEqualTo(ImmutableList.of("31", "32", "33", "34", "35"));
        assertThat(splitToothrange("47-47")).isEqualTo(ImmutableList.of("47"));
        assertThat(splitToothrange("17-14, 22")).isEqualTo(ImmutableList.of("14", "15", "16", "17", "22"));
        assertThat(splitToothrange("14-15, 22-24"))
                .isEqualTo(ImmutableList.of("14", "15", "22", "23", "24"));
        assertThat(splitToothrange("12-22")).isEqualTo(ImmutableList.of("11", "12", "21", "22"));
        assertThat(splitToothrange("22-12")).isEqualTo(ImmutableList.of("11", "12", "21", "22"));
        assertThat(splitToothrange("22 - 12")).isEqualTo(ImmutableList.of("11", "12", "21", "22"));
        assertThat(splitToothrange("15-13,12-22"))
                .isEqualTo(ImmutableList.of("11", "12", "13", "14", "15", "21", "22"));
        assertThat(splitToothrange("OK"))
                .isEqualTo(ImmutableList.of("11", "12", "13", "14", "15", "16", "17", "18",
                        "21", "22", "23", "24", "25", "26", "27", "28"));
        assertThat(splitToothrange("UK"))
                .isEqualTo(ImmutableList.of("31", "32", "33", "34", "35", "36", "37", "38",
                        "41", "42", "43", "44", "45", "46", "47", "48"));
        assertThat(splitToothrange("Uk"))
                .isEqualTo(ImmutableList.of("31", "32", "33", "34", "35", "36", "37", "38",
                        "41", "42", "43", "44", "45", "46", "47", "48"));
        assertThat(splitToothrange("OK/UK"))
                .isEqualTo(ImmutableList.of("11", "12", "13", "14", "15", "16", "17", "18",
                        "21", "22", "23", "24", "25", "26", "27", "28",
                        "31", "32", "33", "34", "35", "36", "37", "38",
                        "41", "42", "43", "44", "45", "46", "47", "48"));
        assertThat(splitToothrange("OK + UK"))
                .isEqualTo(ImmutableList.of("11", "12", "13", "14", "15", "16", "17", "18",
                        "21", "22", "23", "24", "25", "26", "27", "28",
                        "31", "32", "33", "34", "35", "36", "37", "38",
                        "41", "42", "43", "44", "45", "46", "47", "48"));
        assertThat(splitToothrange("UK + 15"))
                .isEqualTo(ImmutableList.of("15",
                        "31", "32", "33", "34", "35", "36", "37", "38",
                        "41", "42", "43", "44", "45", "46", "47", "48"));
        assertThat(splitToothrange("UK, 15"))
                .isEqualTo(ImmutableList.of("15",
                        "31", "32", "33", "34", "35", "36", "37", "38",
                        "41", "42", "43", "44", "45", "46", "47", "48"));
        assertThat(splitToothrange("UK / 15"))
                .isEqualTo(ImmutableList.of("15",
                        "31", "32", "33", "34", "35", "36", "37", "38",
                        "41", "42", "43", "44", "45", "46", "47", "48"));
        assertThat(splitToothrange("UK 15"))
                .isEqualTo(ImmutableList.of("15",
                        "31", "32", "33", "34", "35", "36", "37", "38",
                        "41", "42", "43", "44", "45", "46", "47", "48"));
        assertThat(splitToothrange("UK - 15")).isEqualTo(ImmutableList.of(""));
        assertThat(splitToothrange("13-42949658")).isEqualTo(ImmutableList.of(""));
        assertThat(splitToothrange("42949658-13")).isEqualTo(ImmutableList.of(""));
        assertThat(splitToothrange("29-35")).isEqualTo(ImmutableList.of(""));
        assertThat(splitToothrange("2")).isEqualTo(ImmutableList.of(""));
        assertThat(splitToothrange("24+36")).isEqualTo(ImmutableList.of("24", "36"));
        assertThat(splitToothrange("24 +  33")).isEqualTo(ImmutableList.of("24", "33"));
        assertThat(splitToothrange("13,21,2343,34")).isEqualTo(ImmutableList.of("", "13", "21", "34"));
        assertThat(splitToothrange("75-36")).isEqualTo(ImmutableList.of(""));
        assertThat(splitToothrange("13-")).isEqualTo(ImmutableList.of(""));
    }

    @Test
    public void it_checks_if_a_string_is_a_valid_toothnumber() {
        assertThat(ToothStringUtils.isTooth("12")).isTrue();
        assertThat(ToothStringUtils.isTooth("19")).isFalse();
        assertThat(ToothStringUtils.isTooth("121")).isFalse();
        assertThat(ToothStringUtils.isTooth("a")).isFalse();
        assertThat(ToothStringUtils.isTooth("")).isFalse();
    }

    @Test
    public void it_checks_if_tooth_is_in_given_quadrant() {
        assertThat(ToothStringUtils.isInQuadrant("12", "1")).isTrue();
        assertThat(ToothStringUtils.isInQuadrant("12", "2")).isFalse();
        assertThat(ToothStringUtils.isInQuadrant("12", "3")).isFalse();
        assertThat(ToothStringUtils.isInQuadrant("12", "4")).isFalse();
        assertThat(ToothStringUtils.isInQuadrant("24", "2")).isTrue();
        assertThat(ToothStringUtils.isInQuadrant("36", "3")).isTrue();
        assertThat(ToothStringUtils.isInQuadrant("48", "4")).isTrue();
    }

    @Test
    public void it_checks_if_tooth_is_in_given_jaw() {
        assertThat(ToothStringUtils.isInJaw("18", Keys.UPPER_JAW)).isTrue();
        assertThat(ToothStringUtils.isInJaw("18", Keys.LOWER_JAW)).isFalse();
        assertThat(ToothStringUtils.isInJaw("22", Keys.UPPER_JAW)).isTrue();
        assertThat(ToothStringUtils.isInJaw("32", Keys.LOWER_JAW)).isTrue();
        assertThat(ToothStringUtils.isInJaw("42", Keys.LOWER_JAW)).isTrue();
    }

    @Test
    public void it_gets_jaw_for_tooth() {
        assertThat(ToothStringUtils.jaw("18")).isEqualTo(Keys.UPPER_JAW);
        assertThat(ToothStringUtils.jaw("24")).isEqualTo(Keys.UPPER_JAW);
        assertThat(ToothStringUtils.jaw("35")).isEqualTo(Keys.LOWER_JAW);
        assertThat(ToothStringUtils.jaw("43")).isEqualTo(Keys.LOWER_JAW);
    }

    @Test
    public void it_gets_distal_neighbour() {
        assertThat(ToothStringUtils.neighbourDistal("18")).isEqualTo("");
        assertThat(ToothStringUtils.neighbourDistal("17")).isEqualTo("18");
        assertThat(ToothStringUtils.neighbourDistal("11")).isEqualTo("12");
        assertThat(ToothStringUtils.neighbourDistal("22")).isEqualTo("23");
        assertThat(ToothStringUtils.neighbourDistal("34")).isEqualTo("35");
        assertThat(ToothStringUtils.neighbourDistal("44")).isEqualTo("45");
        assertThat(ToothStringUtils.neighbourDistal("55")).isEqualTo("56");
    }

    @Test
    public void it_gets_distal_neighbours() {
        List<String> neighbours = ToothStringUtils.neighboursDistal("18", 3);
        assertThat(neighbours.size()).isEqualTo(0);

        neighbours = ToothStringUtils.neighboursDistal("15", 3);
        assertThat(neighbours.size()).isEqualTo(3);
        assertThat(neighbours).contains("16");
        assertThat(neighbours).contains("17");
        assertThat(neighbours).contains("18");
    }

    @Test
    public void it_gets_mesial_neighbour() {
        assertThat(ToothStringUtils.neighbourMesial("18")).isEqualTo("17");
        assertThat(ToothStringUtils.neighbourMesial("17")).isEqualTo("16");
        assertThat(ToothStringUtils.neighbourMesial("11")).isEqualTo("21");
        assertThat(ToothStringUtils.neighbourMesial("21")).isEqualTo("11");
        assertThat(ToothStringUtils.neighbourMesial("31")).isEqualTo("41");
        assertThat(ToothStringUtils.neighbourMesial("41")).isEqualTo("31");
        assertThat(ToothStringUtils.neighbourMesial("22")).isEqualTo("21");
        assertThat(ToothStringUtils.neighbourMesial("34")).isEqualTo("33");
        assertThat(ToothStringUtils.neighbourMesial("44")).isEqualTo("43");
        assertThat(ToothStringUtils.neighbourMesial("55")).isEqualTo("54");
    }

    @Test
    public void it_gets_mesial_neighbours() {
        List<String> neighbours = ToothStringUtils.neighboursMesial("18", 3);
        assertThat(neighbours.size()).isEqualTo(3);
        assertThat(neighbours).contains("17");
        assertThat(neighbours).contains("16");
        assertThat(neighbours).contains("15");

        neighbours = ToothStringUtils.neighboursMesial("33", 6);
        assertThat(neighbours.size()).isEqualTo(6);
        assertThat(neighbours).contains("32");
        assertThat(neighbours).contains("31");
        assertThat(neighbours).contains("41");
        assertThat(neighbours).contains("42");
        assertThat(neighbours).contains("43");
        assertThat(neighbours).contains("44");
    }

    @Test
    public void it_gets_direct_neighbours() {
        List<String> neighbours = ToothStringUtils.directNeighbours("34");
        assertThat(neighbours.size()).isEqualTo(2);
        assertThat(neighbours).contains("33");
        assertThat(neighbours).contains("35");

        neighbours = ToothStringUtils.directNeighbours("28");
        assertThat(neighbours.size()).isEqualTo(1);
        assertThat(neighbours).contains("27");

        neighbours = ToothStringUtils.directNeighbours("41");
        assertThat(neighbours.size()).isEqualTo(2);
        assertThat(neighbours).contains("42");
        assertThat(neighbours).contains("31");
    }

    @Test
    public void it_checks_if_tooth_is_milktooth() {
        assertThat(ToothStringUtils.isMilktooth("18")).isFalse();
        assertThat(ToothStringUtils.isMilktooth("25")).isFalse();
        assertThat(ToothStringUtils.isMilktooth("34")).isFalse();
        assertThat(ToothStringUtils.isMilktooth("44")).isFalse();
        assertThat(ToothStringUtils.isMilktooth("51")).isTrue();
        assertThat(ToothStringUtils.isMilktooth("65")).isTrue();
        assertThat(ToothStringUtils.isMilktooth("77")).isTrue();
        assertThat(ToothStringUtils.isMilktooth("88")).isTrue();
    }

    @Test
    public void it_gets_position_of_tooth() {
        assertThat(ToothStringUtils.position("18")).isEqualTo("8");
        assertThat(ToothStringUtils.position("14")).isEqualTo("4");
        assertThat(ToothStringUtils.position("23")).isEqualTo("3");
        assertThat(ToothStringUtils.position("67")).isEqualTo("7");
    }

    @Test
    public void it_gets_quadrant_of_tooth() {
        assertThat(ToothStringUtils.quadrant("18")).isEqualTo("1");
        assertThat(ToothStringUtils.quadrant("14")).isEqualTo("1");
        assertThat(ToothStringUtils.quadrant("23")).isEqualTo("2");
        assertThat(ToothStringUtils.quadrant("67")).isEqualTo("6");
    }

    @Test
    public void it_gets_toothtype() {
        assertThat(ToothStringUtils.type("11")).isEqualTo(Keys.FRONTZAHN);
        assertThat(ToothStringUtils.type("22")).isEqualTo(Keys.FRONTZAHN);
        assertThat(ToothStringUtils.type("33")).isEqualTo(Keys.ECKZAHN);
        assertThat(ToothStringUtils.type("44")).isEqualTo(Keys.PRAEMOLAR);
        assertThat(ToothStringUtils.type("15")).isEqualTo(Keys.PRAEMOLAR);
        assertThat(ToothStringUtils.type("26")).isEqualTo(Keys.MOLAR);
        assertThat(ToothStringUtils.type("37")).isEqualTo(Keys.MOLAR);
        assertThat(ToothStringUtils.type("48")).isEqualTo(Keys.MOLAR);
    }
}