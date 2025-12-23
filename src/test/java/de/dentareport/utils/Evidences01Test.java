package de.dentareport.utils;

import de.dentareport.models.Evidence01;
import de.dentareport.models.Evidence01Interface;
import de.dentareport.models.NullEvidence01;
import de.dentareport.models.RawData;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Evidences01Test {

    @Mocked
    RawData mockRawData;

    @BeforeEach
    public void setUp() {
        new Expectations() {{
            mockRawData.evidences01();
            result = testEvidences01();
        }};
    }

    @Test
    public void it_gets_first_evidence() {
        Evidence01Interface result = Evidences01.first(mockRawData);
        assertThat(result.date()).isEqualTo("1984-01-01");
    }

    @Test
    public void it_returns_null_evidence_if_first_evidence_does_not_exist() {
        new Expectations() {{
            mockRawData.evidences01();
            result = new ArrayList<>();
        }};

        Evidence01Interface result = Evidences01.first(mockRawData);
        assertThat(result).isInstanceOf(NullEvidence01.class);
    }

    @Test
    public void it_gets_first_evidence_from() {
        Evidence01Interface result = Evidences01.firstFrom(mockRawData, "1985-01-01");
        assertThat(result.date()).isEqualTo("1985-01-01");
    }

    @Test
    public void it_returns_null_evidence_if_first_evidence_from_does_not_exist() {
        Evidence01Interface result = Evidences01.firstFrom(mockRawData, "1992-01-01");
        assertThat(result).isInstanceOf(NullEvidence01.class);
    }

    @Test
    public void it_gets_first_evidence_after() {
        Evidence01Interface result = Evidences01.firstAfter(mockRawData, "1985-01-01");
        assertThat(result.date()).isEqualTo("1986-01-01");
    }

    @Test
    public void it_returns_null_evidence_if_first_evidence_after_does_not_exist() {
        Evidence01Interface result = Evidences01.firstAfter(mockRawData, "1991-01-01");
        assertThat(result).isInstanceOf(NullEvidence01.class);
    }

    @Test
    public void it_gets_first_evidence_before() {
        Evidence01Interface result = Evidences01.firstBefore(mockRawData, "1985-01-01");
        assertThat(result.date()).isEqualTo("1984-01-01");
    }

    @Test
    public void it_returns_null_evidence_if_first_evidence_before_does_not_exists() {
        Evidence01Interface result = Evidences01.firstBefore(mockRawData, "1984-01-01");
        assertThat(result).isInstanceOf(NullEvidence01.class);
    }

    @Test
    public void it_gets_first_evidence_until() {
        Evidence01Interface result = Evidences01.firstUntil(mockRawData, "1984-01-01");
        assertThat(result.date()).isEqualTo("1984-01-01");
    }

    @Test
    public void it_returns_null_evidence_if_first_evidence_until_does_not_exist() {
        Evidence01Interface result = Evidences01.firstUntil(mockRawData, "1983-01-01");
        assertThat(result).isInstanceOf(NullEvidence01.class);
    }

    @Test
    public void it_gets_first_evidence_from_before() {
        Evidence01Interface result = Evidences01.firstFromBefore(mockRawData, "1985-01-01", "1987-01-01");
        assertThat(result.date()).isEqualTo("1985-01-01");
    }

    @Test
    public void it_returns_null_evidence_if_first_evidence_from_before_does_not_exist() {
        Evidence01Interface result = Evidences01.firstFromBefore(mockRawData, "1983-01-01", "1984-01-01");
        assertThat(result).isInstanceOf(NullEvidence01.class);
    }

    @Test
    public void it_gets_first_evidence_from_until() {
        Evidence01Interface result = Evidences01.firstFromUntil(mockRawData, "1984-01-01", "1986-01-01");
        assertThat(result.date()).isEqualTo("1984-01-01");
    }

    @Test
    public void it_returns_null_evidence_if_first_evidence_from_until_does_not_exist() {
        Evidence01Interface result = Evidences01.firstFromUntil(mockRawData, "1982-01-01", "1983-01-01");
        assertThat(result).isInstanceOf(NullEvidence01.class);
    }

    @Test
    public void it_gets_first_evidence_after_before() {
        Evidence01Interface result = Evidences01.firstAfterBefore(mockRawData, "1984-01-01", "1986-01-01");
        assertThat(result.date()).isEqualTo("1985-01-01");
    }

    @Test
    public void it_returns_null_evidence_if_first_evidence_after_before_does_not_exist() {
        Evidence01Interface result = Evidences01.firstAfterBefore(mockRawData, "1984-01-01", "1985-01-01");
        assertThat(result).isInstanceOf(NullEvidence01.class);
    }

    @Test
    public void it_gets_first_evidence_after_until() {
        Evidence01Interface result = Evidences01.firstAfterUntil(mockRawData, "1985-01-01", "1987-01-01");
        assertThat(result.date()).isEqualTo("1986-01-01");
    }

    @Test
    public void it_returns_null_evidence_if_first_evidence_after_until_does_not_exist() {
        Evidence01Interface result = Evidences01.firstAfterUntil(mockRawData, "1985-01-01", "1985-12-31");
        assertThat(result).isInstanceOf(NullEvidence01.class);
    }

    @Test
    public void it_gets_last_evidence() {
        Evidence01Interface result = Evidences01.last(mockRawData);
        assertThat(result.date()).isEqualTo("1991-01-01");
    }

    @Test
    public void it_returns_null_evidence_if_last_evidence_does_not_exist() {
        new Expectations() {{
            mockRawData.evidences01();
            result = new ArrayList<>();
        }};

        Evidence01Interface result = Evidences01.last(mockRawData);
        assertThat(result).isInstanceOf(NullEvidence01.class);
    }

    @Test
    public void it_gets_last_evidence_from() {
        Evidence01Interface result = Evidences01.lastFrom(mockRawData, "1985-01-01");
        assertThat(result.date()).isEqualTo("1991-01-01");
    }

    @Test
    public void it_returns_null_evidence_if_last_evidence_from_does_not_exist() {
        Evidence01Interface result = Evidences01.lastFrom(mockRawData, "1991-01-02");
        assertThat(result).isInstanceOf(NullEvidence01.class);
    }

    @Test
    public void it_gets_last_evidence_after() {
        Evidence01Interface result = Evidences01.lastAfter(mockRawData, "1984-01-01");
        assertThat(result.date()).isEqualTo("1991-01-01");
    }

    @Test
    public void it_returns_null_evidence_if_last_evidence_after_does_not_exist() {
        Evidence01Interface result = Evidences01.lastAfter(mockRawData, "1991-01-01");
        assertThat(result).isInstanceOf(NullEvidence01.class);
    }

    @Test
    public void it_gets_last_evidence_before() {
        Evidence01Interface result = Evidences01.lastBefore(mockRawData, "1986-01-01");
        assertThat(result.date()).isEqualTo("1985-01-01");
    }

    @Test
    public void it_returns_null_evidence_if_last_evidence_before_does_not_exists() {
        Evidence01Interface result = Evidences01.lastBefore(mockRawData, "1984-01-01");
        assertThat(result).isInstanceOf(NullEvidence01.class);
    }

    @Test
    public void it_gets_last_evidence_until() {
        Evidence01Interface result = Evidences01.lastUntil(mockRawData, "1986-01-01");
        assertThat(result.date()).isEqualTo("1986-01-01");
    }

    @Test
    public void it_returns_null_evidence_if_last_evidence_until_does_not_exist() {
        Evidence01Interface result = Evidences01.lastUntil(mockRawData, "1983-01-01");
        assertThat(result).isInstanceOf(NullEvidence01.class);
    }

    @Test
    public void it_gets_last_evidence_from_before() {
        Evidence01Interface result = Evidences01.lastFromBefore(mockRawData, "1985-01-01", "1988-01-01");
        assertThat(result.date()).isEqualTo("1987-01-01");
    }

    @Test
    public void it_returns_null_evidence_if_last_evidence_from_before_does_not_exist() {
        Evidence01Interface result = Evidences01.lastFromBefore(mockRawData, "1983-01-01", "1984-01-01");
        assertThat(result).isInstanceOf(NullEvidence01.class);
    }

    @Test
    public void it_gets_last_evidence_from_until() {
        Evidence01Interface result = Evidences01.lastFromUntil(mockRawData, "1985-01-01", "1988-01-01");
        assertThat(result.date()).isEqualTo("1988-01-01");
    }

    @Test
    public void it_returns_null_evidence_if_last_evidence_from_until_does_not_exist() {
        Evidence01Interface result = Evidences01.lastFromUntil(mockRawData, "1982-01-01", "1983-01-01");
        assertThat(result).isInstanceOf(NullEvidence01.class);
    }

    @Test
    public void it_gets_last_evidence_after_before() {
        Evidence01Interface result = Evidences01.lastAfterBefore(mockRawData, "1986-01-01", "1989-01-01");
        assertThat(result.date()).isEqualTo("1988-01-01");
    }

    @Test
    public void it_returns_null_evidence_if_last_evidence_after_before_does_not_exist() {
        Evidence01Interface result = Evidences01.lastAfterBefore(mockRawData, "1991-01-01", "1992-01-01");
        assertThat(result).isInstanceOf(NullEvidence01.class);
    }

    @Test
    public void it_gets_last_evidence_after_until() {
        Evidence01Interface result = Evidences01.lastAfterUntil(mockRawData, "1986-01-01", "1989-01-01");
        assertThat(result.date()).isEqualTo("1989-01-01");
    }

    @Test
    public void it_returns_null_evidence_if_last_evidence_after_until_does_not_exist() {
        Evidence01Interface result = Evidences01.lastAfterUntil(mockRawData, "1991-01-01", "1992-01-01");
        assertThat(result).isInstanceOf(NullEvidence01.class);
    }

    @Test
    public void it_counts_evidences() {
        String result = Evidences01.count(mockRawData);
        assertThat(result).isEqualTo("8");
    }

    @Test
    public void it_counts_evidences_from() {
        String result = Evidences01.countFrom(mockRawData, "1985-01-01");
        assertThat(result).isEqualTo("7");
    }

    @Test
    public void it_counts_evidences_after() {
        String result = Evidences01.countAfter(mockRawData, "1985-01-01");
        assertThat(result).isEqualTo("6");
    }

    @Test
    public void it_counts_evidences_before() {
        String result = Evidences01.countBefore(mockRawData, "1989-01-01");
        assertThat(result).isEqualTo("5");
    }

    @Test
    public void it_counts_evidences_until() {
        String result = Evidences01.countUntil(mockRawData, "1989-01-01");
        assertThat(result).isEqualTo("6");
    }

    @Test
    public void it_counts_evidences_from_before() {
        String result = Evidences01.countFromBefore(mockRawData, "1985-01-01", "1990-01-01");
        assertThat(result).isEqualTo("5");
    }

    @Test
    public void it_counts_evidences_from_until() {
        String result = Evidences01.countFromUntil(mockRawData, "1985-01-01", "1990-01-01");
        assertThat(result).isEqualTo("6");
    }

    @Test
    public void it_counts_evidences_after_before() {
        String result = Evidences01.countAfterBefore(mockRawData, "1985-01-01", "1989-01-01");
        assertThat(result).isEqualTo("3");
    }

    @Test
    public void it_counts_evidences_after_until() {
        String result = Evidences01.countAfterUntil(mockRawData, "1985-01-01", "1989-01-01");
        assertThat(result).isEqualTo("4");
    }

    private List<Evidence01> testEvidences01() {
        List<Evidence01> ret = new ArrayList<>();

        ret.add(new Evidence01("1984-01-01", "", "", "", "", "", "", "", "", "", "", new HashMap<>(), new HashMap<>(), new HashMap<>()));
        ret.add(new Evidence01("1985-01-01", "", "", "", "", "", "", "", "", "", "", new HashMap<>(), new HashMap<>(), new HashMap<>()));
        ret.add(new Evidence01("1986-01-01", "", "", "", "", "", "", "", "", "", "", new HashMap<>(), new HashMap<>(), new HashMap<>()));
        ret.add(new Evidence01("1987-01-01", "", "", "", "", "", "", "", "", "", "", new HashMap<>(), new HashMap<>(), new HashMap<>()));
        ret.add(new Evidence01("1988-01-01", "", "", "", "", "", "", "", "", "", "", new HashMap<>(), new HashMap<>(), new HashMap<>()));
        ret.add(new Evidence01("1989-01-01", "", "", "", "", "", "", "", "", "", "", new HashMap<>(), new HashMap<>(), new HashMap<>()));
        ret.add(new Evidence01("1990-01-01", "", "", "", "", "", "", "", "", "", "", new HashMap<>(), new HashMap<>(), new HashMap<>()));
        ret.add(new Evidence01("1991-01-01", "", "", "", "", "", "", "", "", "", "", new HashMap<>(), new HashMap<>(), new HashMap<>()));

        return ret;
    }
}