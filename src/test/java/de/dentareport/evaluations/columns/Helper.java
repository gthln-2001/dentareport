package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableMap;
import de.dentareport.models.CaseData;

import java.util.HashMap;
import java.util.Map;

public class Helper {

    public static final String TEST_TOOTH = "some_toothnumber";

    public static CaseData testCase() {
        return new CaseData(TEST_TOOTH);
    }

    public static CaseData testCase(ImmutableMap<String, String> values) {
        CaseData testCase = testCase();
        for (Map.Entry<String, String> value: values.entrySet()) {
            testCase.setString(value.getKey(), value.getValue());
        }
        return testCase;
    }

    public static Map<String, String> options() {
        return new HashMap<>();
    }
}
