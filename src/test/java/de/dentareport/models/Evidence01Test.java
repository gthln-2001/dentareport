package de.dentareport.models;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.dentareport.utils.Keys;
import de.dentareport.utils.Toothnumbers;
import org.junit.jupiter.api.Test;

import java.util.*;

import static de.dentareport.utils.Keys.*;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class Evidence01Test {

    @Test
    public void it_can_be_initialized() {
        Evidence01 evidence01 = testEvidence();

        assertThat(evidence01.date()).isEqualTo("date");
        assertThat(evidence01.billingcode()).isEqualTo("billingcode");
        assertThat(evidence01.dft()).isEqualTo("dft");
        assertThat(evidence01.dmft()).isEqualTo("dmft");
        assertThat(evidence01.dt()).isEqualTo("dt");
        assertThat(evidence01.mt()).isEqualTo("mt");
        assertThat(evidence01.ft()).isEqualTo("ft");
        assertThat(evidence01.toothcount()).isEqualTo("20");
        assertThat(evidence01.toothcountInJaw("11")).isEqualTo("7");
        assertThat(evidence01.toothcountInJaw("22")).isEqualTo("7");
        assertThat(evidence01.toothcountInJaw("33")).isEqualTo("13");
        assertThat(evidence01.toothcountInJaw("44")).isEqualTo("13");
        assertThat(evidence01.toothcountQ1()).isEqualTo("3");
        assertThat(evidence01.toothcountQ2()).isEqualTo("4");
        assertThat(evidence01.toothcountQ3()).isEqualTo("6");
        assertThat(evidence01.toothcountQ4()).isEqualTo("7");
        assertThat(evidence01.status("11")).isEqualTo("status_11");
        assertThat(evidence01.status("12")).isEqualTo("status_12");
        assertThat(evidence01.status("13")).isEqualTo("status_13");
        assertThat(evidence01.status("14")).isEqualTo("status_14");
        assertThat(evidence01.status("15")).isEqualTo("status_15");
        assertThat(evidence01.status("16")).isEqualTo("status_16");
        assertThat(evidence01.status("17")).isEqualTo("status_17");
        assertThat(evidence01.status("18")).isEqualTo("status_18");
        assertThat(evidence01.status("21")).isEqualTo("status_21");
        assertThat(evidence01.status("22")).isEqualTo("status_22");
        assertThat(evidence01.status("23")).isEqualTo("status_23");
        assertThat(evidence01.status("24")).isEqualTo("status_24");
        assertThat(evidence01.status("25")).isEqualTo("status_25");
        assertThat(evidence01.status("26")).isEqualTo("status_26");
        assertThat(evidence01.status("27")).isEqualTo("status_27");
        assertThat(evidence01.status("28")).isEqualTo("status_28");
        assertThat(evidence01.status("31")).isEqualTo("status_31");
        assertThat(evidence01.status("32")).isEqualTo("status_32");
        assertThat(evidence01.status("33")).isEqualTo("status_33");
        assertThat(evidence01.status("34")).isEqualTo("status_34");
        assertThat(evidence01.status("35")).isEqualTo("status_35");
        assertThat(evidence01.status("36")).isEqualTo("status_36");
        assertThat(evidence01.status("37")).isEqualTo("status_37");
        assertThat(evidence01.status("38")).isEqualTo("status_38");
        assertThat(evidence01.status("41")).isEqualTo("status_41");
        assertThat(evidence01.status("42")).isEqualTo("status_42");
        assertThat(evidence01.status("43")).isEqualTo("status_43");
        assertThat(evidence01.status("44")).isEqualTo("status_44");
        assertThat(evidence01.status("45")).isEqualTo("status_45");
        assertThat(evidence01.status("46")).isEqualTo("status_46");
        assertThat(evidence01.status("47")).isEqualTo("status_47");
        assertThat(evidence01.status("48")).isEqualTo("status_48");

        assertThat(evidence01.cariesSurfaces("42").size()).isEqualTo(6);
        assertThat(evidence01.cariesSpecification("42", Keys.SURFACE_LINGUAL)).isEqualTo("caries_surface_42_"
                + Keys.SURFACE_LINGUAL);
        assertThat(evidence01.filling("23", Keys.SURFACE_LINGUAL)).isEqualTo("filling_surface_23_"
                + Keys.SURFACE_LINGUAL);
    }

    @Test
    public void it_censors_evidence() {
        Evidence01 evidence01 = testEvidence();

        assertThat(evidence01.censored()).isEqualTo(Keys.CENSORED_YES);
        assertThat(evidence01.isCensored()).isTrue();
    }

    @Test
    public void it_checks_tooth_for_given_status() {
        Evidence01 evidence01 = testEvidence();

        assertThat(evidence01.hasStatus("12", "status_12")).isTrue();
        assertThat(evidence01.hasStatus("12", "something_else")).isFalse();
    }

    @Test
    public void it_checks_for_valid_source() {
        Evidence01 evidence01 = testEvidence();

        assertThat(evidence01.hasValidSourceForBillingposition()).isTrue();
    }

    @Test
    public void it_gets_endstaendigkeit_for_7th_or_8th_tooth() {
        Evidence01 evidence01 = testEvidence();

        assertThat(evidence01.endstaendigkeit("17")).isEqualTo("");
        assertThat(evidence01.endstaendigkeit("18")).isEqualTo("");
        assertThat(evidence01.endstaendigkeit("28")).isEqualTo("");
        assertThat(evidence01.endstaendigkeit("38")).isEqualTo("");
        assertThat(evidence01.endstaendigkeit("48")).isEqualTo("");
    }

    @Test
    public void it_gets_endstaendigkeit_for_milktooth() {
        Evidence01 evidence01 = testEvidence();

        assertThat(evidence01.endstaendigkeit("51")).isEqualTo(Keys.NO_DATA);
        assertThat(evidence01.endstaendigkeit("62")).isEqualTo(Keys.NO_DATA);
        assertThat(evidence01.endstaendigkeit("73")).isEqualTo(Keys.NO_DATA);
        assertThat(evidence01.endstaendigkeit("84")).isEqualTo(Keys.NO_DATA);
        assertThat(evidence01.endstaendigkeit("88")).isEqualTo(Keys.NO_DATA);
    }

    @Test
    public void it_gets_endstaendigkeit() {
        Map<String, String> status = testStatus(".");
        Evidence01 evidence01 = testEvidence(status);
        assertThat(evidence01.endstaendigkeit("13")).isEqualTo(Keys.ZAHNREIHE);

        status = testStatus(".");
        status.put("14", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("15", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("16", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        evidence01 = testEvidence(status);
        assertThat(evidence01.endstaendigkeit("13")).isEqualTo(Keys.ENDSTAENDIG);

        status = testStatus(".");
        status.put("14", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("15", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("16", ".");
        evidence01 = testEvidence(status);
        assertThat(evidence01.endstaendigkeit("13")).isEqualTo(Keys.ZAHNREIHE);

        status = testStatus(".");
        status.put("14", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("15", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("16", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("17", ".");
        evidence01 = testEvidence(status);
        assertThat(evidence01.endstaendigkeit("13")).isEqualTo(Keys.ENDSTAENDIG);

        status = testStatus(".");
        status.put("11", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("12", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("14", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("15", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("16", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        evidence01 = testEvidence(status);
        assertThat(evidence01.endstaendigkeit("13")).isEqualTo(Keys.D_ENDSTAENDIG);

        status = testStatus(".");
        status.put("21", ".");
        status.put("11", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("12", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("14", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("15", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("16", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        evidence01 = testEvidence(status);
        assertThat(evidence01.endstaendigkeit("13")).isEqualTo(Keys.D_ENDSTAENDIG);

        status = testStatus(".");
        status.put("11", ".");
        status.put("12", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("14", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("15", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("16", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        evidence01 = testEvidence(status);
        assertThat(evidence01.endstaendigkeit("13")).isEqualTo(Keys.ENDSTAENDIG);
    }

    @Test
    public void it_gets_toothcontacts_for_milktooth() {
        Evidence01 evidence01 = testEvidence();

        assertThat(evidence01.toothcontacts("51")).isEqualTo(Keys.NO_DATA);
        assertThat(evidence01.toothcontacts("62")).isEqualTo(Keys.NO_DATA);
        assertThat(evidence01.toothcontacts("73")).isEqualTo(Keys.NO_DATA);
        assertThat(evidence01.toothcontacts("84")).isEqualTo(Keys.NO_DATA);
        assertThat(evidence01.toothcontacts("88")).isEqualTo(Keys.NO_DATA);
    }

    @Test
    public void it_gets_toothcontacts() {
        Map<String, String> status = testStatus(".");
        Evidence01 evidence01 = testEvidence(status);
        assertThat(evidence01.toothcontacts("13")).isEqualTo("2");

        status = testStatus(".");
        status.put("14", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        evidence01 = testEvidence(status);
        assertThat(evidence01.toothcontacts("13")).isEqualTo("1");

        status = testStatus(".");
        status.put("12", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("14", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        evidence01 = testEvidence(status);
        assertThat(evidence01.toothcontacts("13")).isEqualTo("0");

        status = testStatus(".");
        status.put("11", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        status.put("15", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        evidence01 = testEvidence(status);
        assertThat(evidence01.toothcontacts("13")).isEqualTo("2");

        status = testStatus(".");
        evidence01 = testEvidence(status);
        assertThat(evidence01.toothcontacts("48")).isEqualTo("1");

        status = testStatus(".");
        status.put("47", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        evidence01 = testEvidence(status);
        assertThat(evidence01.toothcontacts("48")).isEqualTo("0");

        status = testStatus(".");
        evidence01 = testEvidence(status);
        assertThat(evidence01.toothcontacts("11")).isEqualTo("2");

        status = testStatus(".");
        status.put("21", randomElement(Keys.EVIDENCE_STATUS__NO_TOOTH_CONTACT));
        evidence01 = testEvidence(status);
        assertThat(evidence01.toothcontacts("11")).isEqualTo("1");
    }

    @Test
    public void it_checks_if_billingcode_of_evidence_is_in_given_list_of_billingcodes() {
        Evidence01 evidence01 = testEvidence(testStatus());
        Set<String> billingcodes1 = ImmutableSet.of("Foo", "Bar", "c");
        Set<String> billingcodes2 = ImmutableSet.of("Foo", "Bar", "c", "billingcode");

        assertThat(evidence01.hasBillingcode(billingcodes1)).isFalse();
        assertThat(evidence01.hasBillingcode(billingcodes2)).isTrue();
    }

    @Test
    public void it_gets_caries_surfaces_with_given_specification_for_given_tooth() {
        Map<String, Map<String, String>> testSurfaces = testCariesSurfaces();
        Map<String, String> surfaces = testSurfaces.get("42");
        surfaces.put(SURFACE_DISTAL, EVIDENCE_CARIES.get(CARIES_NOT_TO_TREAT_WITH_XRAY));

        surfaces.put(SURFACE_LINGUAL, EVIDENCE_CARIES.get(CARIES_TO_TREAT_WITH_XRAY));
        surfaces.put(SURFACE_OKKLUSAL, EVIDENCE_CARIES.get(CARIES_TO_TREAT_WITH_XRAY));
        surfaces.put(SURFACE_MESIAL, EVIDENCE_CARIES.get(CARIES_TO_TREAT_WITH_XRAY));
        surfaces.put(SURFACE_CERVIKAL, EVIDENCE_CARIES.get(CARIES_TO_TREAT_WITH_XRAY));

        Evidence01 evidence01 = testEvidence(testStatus(), testSurfaces, testFillingSurfaces());

        assertThat(evidence01.cariesSurfaces("42", CARIES_TO_TREAT_WITH_XRAY)).isEqualTo(ImmutableList.of(
                SURFACE_CERVIKAL,
                SURFACE_LINGUAL,
                SURFACE_MESIAL,
                SURFACE_OKKLUSAL));
    }

    @Test
    public void it_counts_caries_surfaces_with_given_specification_for_given_tooth() {
        Map<String, Map<String, String>> testSurfaces = testCariesSurfaces();
        Map<String, String> surfaces = testSurfaces.get("42");
        surfaces.put(SURFACE_DISTAL, EVIDENCE_CARIES.get(CARIES_NOT_TO_TREAT_WITH_XRAY));

        surfaces.put(SURFACE_LINGUAL, EVIDENCE_CARIES.get(CARIES_TO_TREAT_WITH_XRAY));
        surfaces.put(SURFACE_MESIAL, EVIDENCE_CARIES.get(CARIES_TO_TREAT_WITH_XRAY));
        surfaces.put(SURFACE_OKKLUSAL, EVIDENCE_CARIES.get(CARIES_TO_TREAT_WITH_XRAY));

        Evidence01 evidence01 = testEvidence(testStatus(), testSurfaces, testFillingSurfaces());

        assertThat(evidence01.countCariesSurfaces("42", CARIES_TO_TREAT_WITH_XRAY)).isEqualTo("3");
    }

    @Test
    public void it_counts_filling_surfaces_for_given_tooth() {
        Map<String, Map<String, String>> testSurfaces = testFillingSurfaces();
        Map<String, String> surfaces = testSurfaces.get("42");
        surfaces.put(SURFACE_CERVIKAL, "0");
        surfaces.put(SURFACE_DISTAL, "some_filling_value");
        surfaces.put(SURFACE_LINGUAL, "some_filling_value");
        surfaces.put(SURFACE_MESIAL, "0");
        surfaces.put(SURFACE_OKKLUSAL, "some_other_filling_value");
        surfaces.put(SURFACE_VESTIBULAER, "a_third_filling_value");

        Evidence01 evidence01 = testEvidence(testStatus(), testCariesSurfaces(), testSurfaces);

        assertThat(evidence01.countFillingSurfaces("42")).isEqualTo("4");
    }

    private String randomElement(List<String> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    private Evidence01 testEvidence(Map<String, String> status, Map<String, Map<String, String>> cariesSurfaces, Map<String, Map<String, String>> fillingSurfaces) {
        return new Evidence01("date", "billingcode",
                "dft", "dmft", "dt", "mt", "ft",
                "3", "4", "6", "7",
                status,
                cariesSurfaces,
                fillingSurfaces);
    }

    private Evidence01 testEvidence(Map<String, String> status) {
        return testEvidence(status, testCariesSurfaces(), testFillingSurfaces());
    }

    private Evidence01 testEvidence() {
        return testEvidence(testStatus(), testCariesSurfaces(), testFillingSurfaces());
    }

    private Map<String, String> testStatus() {
        Map<String, String> ret = new HashMap<>();
        for (String toothnumber : Toothnumbers.ALL) {
            ret.put(toothnumber, String.format("status_%s", toothnumber));
        }
        return ret;
    }

    private Map<String, String> testStatus(String defaultStatus) {
        Map<String, String> ret = new HashMap<>();
        for (String toothnumber : Toothnumbers.ALL) {
            ret.put(toothnumber, defaultStatus);
        }
        return ret;
    }

    private Map<String, Map<String, String>> testCariesSurfaces() {
        Map<String, Map<String, String>> ret = new HashMap<>();
        for (String toothnumber : Toothnumbers.ALL) {
            Map<String, String> surfaces = new HashMap<>();
            for (String surface : Keys.SURFACES) {
                surfaces.put(surface, String.format("caries_surface_%s_%s", toothnumber, surface));
            }
            ret.put(toothnumber, surfaces);
        }
        return ret;
    }

    private Map<String, Map<String, String>> testFillingSurfaces() {
        Map<String, Map<String, String>> ret = new HashMap<>();
        for (String toothnumber : Toothnumbers.ALL) {
            Map<String, String> surfaces = new HashMap<>();
            for (String surface : Keys.SURFACES) {
                surfaces.put(surface, String.format("filling_surface_%s_%s", toothnumber, surface));
            }
            ret.put(toothnumber, surfaces);
        }
        return ret;
    }
}