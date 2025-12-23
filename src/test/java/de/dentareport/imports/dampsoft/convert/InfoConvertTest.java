package de.dentareport.imports.dampsoft.convert;

import org.junit.jupiter.api.Test;

import static de.dentareport.imports.dampsoft.convert.Helper.sortSurfaces;
import static de.dentareport.utils.Keys.*;
import static org.assertj.core.api.Assertions.assertThat;

public class InfoConvertTest {

    @Test
    public void it_gets_billingcode_from_info_string() {
        assertThat(InfoConvert.convertToBillingCode("12abcdef345", "LG")).isEqualTo("ABCDEF");
        assertThat(InfoConvert.convertToBillingCode("12abcdef345", "LK")).isEqualTo("ABCDEF");
        assertThat(InfoConvert.convertToBillingCode("12abcdef345", "XX")).isEqualTo("");
        assertThat(InfoConvert.convertToBillingCode("12  cd  345", "LG")).isEqualTo("CD");
        assertThat(InfoConvert.convertToBillingCode("12äöü   345", "LG")).isEqualTo("ÄÖÜ");
    }

    @Test
    public void it_gets_quantity_from_info_string() {
        assertThat(InfoConvert.convertToQuantity("                       7 ", "LG")).isEqualTo("7");
        assertThat(InfoConvert.convertToQuantity("                       7 ", "LK")).isEqualTo("7");
        assertThat(InfoConvert.convertToQuantity("                       7 ", "Foo")).isEqualTo("");
        assertThat(InfoConvert.convertToQuantity("                       x ", "LG")).isEqualTo("");
    }

    @Test
    public void it_gets_surfaces_from_info_string() {
        assertThat(InfoConvert.convertToSurfaces("        c         ", "Foo")).isEqualTo("");
        assertThat(InfoConvert.convertToSurfaces("        c         ", "LG")).isEqualTo(SURFACE_CERVIKAL);
        assertThat(InfoConvert.convertToSurfaces("        c         ", "LK")).isEqualTo(SURFACE_CERVIKAL);
        assertThat(InfoConvert.convertToSurfaces("           c      ", "LK")).isEqualTo(SURFACE_CERVIKAL);
        assertThat(InfoConvert.convertToSurfaces("        C         ", "LK")).isEqualTo(SURFACE_CERVIKAL);
        assertThat(InfoConvert.convertToSurfaces("        i         ", "LG")).isEqualTo(SURFACE_OKKLUSAL);
        assertThat(InfoConvert.convertToSurfaces("        m         ", "LG")).isEqualTo(SURFACE_MESIAL);
        assertThat(InfoConvert.convertToSurfaces("        o         ", "LG")).isEqualTo(SURFACE_OKKLUSAL);
        assertThat(InfoConvert.convertToSurfaces("        p         ", "LG")).isEqualTo(SURFACE_LINGUAL);
        assertThat(InfoConvert.convertToSurfaces("        b         ", "LG")).isEqualTo(SURFACE_VESTIBULAER);
        assertThat(InfoConvert.convertToSurfaces("        d         ", "LG")).isEqualTo(SURFACE_DISTAL);
        assertThat(InfoConvert.convertToSurfaces("        z         ", "LG")).isEqualTo(SURFACE_CERVIKAL);
        assertThat(InfoConvert.convertToSurfaces("        la        ", "LG")).isEqualTo(SURFACE_VESTIBULAER);
        assertThat(InfoConvert.convertToSurfaces("        li        ", "LG")).isEqualTo(SURFACE_LINGUAL);
        assertThat(InfoConvert.convertToSurfaces("        inc       ", "LG")).isEqualTo(SURFACE_OKKLUSAL);
        assertThat(InfoConvert.convertToSurfaces("        mp        ", "LG")).isEqualTo(sortSurfaces(String.format("%s,%s", SURFACE_LINGUAL, SURFACE_MESIAL)));
        assertThat(InfoConvert.convertToSurfaces("        pli       ", "LG")).isEqualTo(SURFACE_LINGUAL);
        assertThat(InfoConvert.convertToSurfaces("        inclila   ", "LG")).isEqualTo(sortSurfaces(String.format("%s,%s,%s", SURFACE_LINGUAL, SURFACE_OKKLUSAL, SURFACE_VESTIBULAER)));
    }

    @Test
    public void it_gets_tooth_from_info_string() {
        assertThat(InfoConvert.convertToTooth("11 ", "Foo")).isEqualTo("");
        assertThat(InfoConvert.convertToTooth("11 ", "LG")).isEqualTo("11");
        assertThat(InfoConvert.convertToTooth("11 ", "LK")).isEqualTo("11");
        assertThat(InfoConvert.convertToTooth("XX ", "LK")).isEqualTo("");
        assertThat(InfoConvert.convertToTooth("19 ", "LK")).isEqualTo("");
        assertThat(InfoConvert.convertToTooth("1  ", "LK")).isEqualTo("");
    }
}