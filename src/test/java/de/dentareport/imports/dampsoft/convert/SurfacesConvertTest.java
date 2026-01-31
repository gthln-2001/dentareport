package de.dentareport.imports.dampsoft.convert;

import org.junit.jupiter.api.Test;

import static de.dentareport.utils.Keys.*;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class SurfacesConvertTest {

    @Test
    public void it_converts_surface() {
        assertThat(SurfacesConvert.convert("")).isEqualTo("");
        assertThat(SurfacesConvert.convert("c")).isEqualTo(SURFACE_CERVIKAL);
        assertThat(SurfacesConvert.convert("C")).isEqualTo(SURFACE_CERVIKAL);
        assertThat(SurfacesConvert.convert("i")).isEqualTo(SURFACE_OKKLUSAL);
        assertThat(SurfacesConvert.convert("m")).isEqualTo(SURFACE_MESIAL);
        assertThat(SurfacesConvert.convert("o")).isEqualTo(SURFACE_OKKLUSAL);
        assertThat(SurfacesConvert.convert("p")).isEqualTo(SURFACE_LINGUAL);
        assertThat(SurfacesConvert.convert("b")).isEqualTo(SURFACE_VESTIBULAER);
        assertThat(SurfacesConvert.convert("d")).isEqualTo(SURFACE_DISTAL);
        assertThat(SurfacesConvert.convert("z")).isEqualTo(SURFACE_CERVIKAL);
        assertThat(SurfacesConvert.convert("la")).isEqualTo(SURFACE_VESTIBULAER);
        assertThat(SurfacesConvert.convert("li")).isEqualTo(SURFACE_LINGUAL);
        assertThat(SurfacesConvert.convert("inc")).isEqualTo(SURFACE_OKKLUSAL);
        assertThat(SurfacesConvert.convert("mp")).isEqualTo(Helper.sortSurfaces(String.format("%s,%s", SURFACE_LINGUAL, SURFACE_MESIAL)));
        assertThat(SurfacesConvert.convert("pli")).isEqualTo(SURFACE_LINGUAL);
        assertThat(SurfacesConvert.convert("inclila")).isEqualTo(Helper.sortSurfaces(String.format("%s,%s,%s", SURFACE_LINGUAL, SURFACE_OKKLUSAL, SURFACE_VESTIBULAER)));
    }
}