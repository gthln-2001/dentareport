package de.dentareport.imports.dampsoft.convert;

import de.dentareport.utils.Keys;

import java.util.*;

// TODO: TEST?
public class DampsoftKeys {

    public static final Map<String, String> EVIDENCE_FILLING = evidenceFilling();
    public static final List<String> EVIDENCE_MILKTEETH = evidenceMilkteeth();
    public static final Map<String, String> EVIDENCE_SEALING = evidenceSealing();
    public static final Map<String, String> EVIDENCE_STATUS = evidenceStatus();
    public static final Map<String, String> SURFACES = surfaces();
    public static final Set<String> SURFACE_KEYS = SURFACES.keySet();

    private static List<String> evidenceMilkteeth() {
        List<String> ret = new ArrayList<>();
        ret.add("A");
        ret.add("B");
        ret.add("F");
        return ret;
    }

    private static Map<String, String> evidenceSealing() {
        Map<String, String> ret = new HashMap<>();
        ret.put(" ", Keys.EVIDENCE_SEALING__NOT_SEALED);
        ret.put("U", Keys.EVIDENCE_SEALING__SEALING_NECESSARY);
        ret.put("V", Keys.EVIDENCE_SEALING__SEALED);
        return ret;
    }

    private static Map<String, String> evidenceStatus() {
        Map<String, String> ret = new HashMap<>();
        ret.put(" ", Keys.EVIDENCE_STATUS__SOUND);
        ret.put("b", Keys.EVIDENCE_STATUS__BRUECKENGLIED);
        ret.put("d", Keys.EVIDENCE_STATUS__IM_DURCHBRUCH);
        ret.put("e", Keys.EVIDENCE_STATUS__ERSETZT);
        ret.put("f", Keys.EVIDENCE_STATUS__FEHLEND);
        ret.put("i", Keys.EVIDENCE_STATUS__IMPLANTAT);
        ret.put("k", Keys.EVIDENCE_STATUS__KRONE);
        ret.put("l", Keys.EVIDENCE_STATUS__GESCHLOSSEN);
        ret.put("t", Keys.EVIDENCE_STATUS__TELESKOPKRONE);
        ret.put(")", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_RECHTS);
        ret.put("(", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_LINKS);
        return ret;
    }

    private static Map<String, String> surfaces() {
        Map<String, String> codes = new LinkedHashMap<>();
        codes.put("inc", Keys.SURFACE_OKKLUSAL);
        codes.put("li", Keys.SURFACE_LINGUAL);
        codes.put("i", Keys.SURFACE_OKKLUSAL);
        codes.put("c", Keys.SURFACE_CERVIKAL);
        codes.put("m", Keys.SURFACE_MESIAL);
        codes.put("o", Keys.SURFACE_OKKLUSAL);
        codes.put("p", Keys.SURFACE_LINGUAL);
        codes.put("b", Keys.SURFACE_VESTIBULAER);
        codes.put("d", Keys.SURFACE_DISTAL);
        codes.put("la", Keys.SURFACE_VESTIBULAER);
        codes.put("z", Keys.SURFACE_CERVIKAL);
        return codes;
    }

    private static Map<String, String> evidenceFilling() {
        Map<String, String> ret = new HashMap<>();
        ret.put("A", Keys.FILLING__NO_FILLING);
        ret.put("B", Keys.FILLING__MATERIAL_1);
        ret.put("AB", Keys.FILLING__MATERIAL_1_EXTERNAL);
        ret.put("D", Keys.FILLING__MATERIAL_2);
        ret.put("AD", Keys.FILLING__MATERIAL_2_EXTERNAL);
        ret.put("E", Keys.FILLING__MATERIAL_3);
        ret.put("AE", Keys.FILLING__MATERIAL_3_EXTERNAL);
        ret.put("F", Keys.FILLING__MATERIAL_4);
        ret.put("AF", Keys.FILLING__MATERIAL_4_EXTERNAL);
        ret.put("I", Keys.FILLING__MATERIAL_5);
        ret.put("AI", Keys.FILLING__MATERIAL_5_EXTERNAL);
        ret.put("K", Keys.FILLING__MATERIAL_6);
        ret.put("AK", Keys.FILLING__MATERIAL_6_EXTERNAL);
        ret.put("L", Keys.FILLING__MATERIAL_7);
        ret.put("AL", Keys.FILLING__MATERIAL_7_EXTERNAL);
        ret.put("M", Keys.FILLING__MATERIAL_8);
        ret.put("AM", Keys.FILLING__MATERIAL_8_EXTERNAL);
        return ret;
    }
}
