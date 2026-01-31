package de.dentareport.imports.dampsoft.dampsoft_files;

//import de.dentareport.gui.ProgressUpdate;
import de.dentareport.utils.db.Db;
import mockit.Mocked;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


// TODO: TEST?
public class FirstAndLastVisitTest {

    private FirstAndLastVisit firstAndLastVisit;
    @Mocked
    Db mockDb;

    @BeforeEach
    public void setUp() throws Exception {
        firstAndLastVisit = new FirstAndLastVisit();
    }
    // TODO: Remove test (temporarly added to have test in class)
    @Test
    public void testSomething() {
        Assertions.assertThat(true).isTrue();
    }

    // TODO: Fix tests
//    @Test
//    public void it_returns_empty_field_if_there_is_no_data_for_patient(
//            @Mocked ProgressUpdate mockProgressUpdate) {
//
//        new Expectations() {{
//            mockDb.firstAndLastVisits("evidences_01");
//            result = new HashMap<>();
//            mockDb.firstAndLastVisits("treatments", FirstAndLastVisit.conditionsTreatments());
//            result = new HashMap<>();
//        }};
//
//        firstAndLastVisit.importFile();
//
//        assertThat(FirstAndLastVisit.firstVisit("42")).isEqualTo("");
//        assertThat(FirstAndLastVisit.lastVisit("42")).isEqualTo("");
//        assertThat(FirstAndLastVisit.last01("42")).isEqualTo("");
//    }

    // TODO: Fix tests
//    @Test
//    public void it_gets_first_and_last_visit_and_last_01(@Mocked ProgressUpdate mockProgressUpdate) {
//        new Expectations() {{
//            mockDb.firstAndLastVisits("evidences_01");
//            result = testEvidences();
//            mockDb.firstAndLastVisits("treatments", FirstAndLastVisit.conditionsTreatments());
//            result = testTreatments();
//        }};
//
//        firstAndLastVisit.importFile();
//
//        assertThat(FirstAndLastVisit.firstVisit("1")).isEqualTo("2000-10-01");
//        assertThat(FirstAndLastVisit.lastVisit("1")).isEqualTo("2010-10-01");
//        assertThat(FirstAndLastVisit.last01("1")).isEqualTo("");
//
//        assertThat(FirstAndLastVisit.firstVisit("2")).isEqualTo("2000-11-01");
//        assertThat(FirstAndLastVisit.lastVisit("2")).isEqualTo("2010-11-01");
//        assertThat(FirstAndLastVisit.last01("2")).isEqualTo("2010-11-01");
//
//        assertThat(FirstAndLastVisit.firstVisit("3")).isEqualTo("1990-10-01");
//        assertThat(FirstAndLastVisit.lastVisit("3")).isEqualTo("1999-10-01");
//        assertThat(FirstAndLastVisit.last01("3")).isEqualTo("1995-10-01");
//
//        assertThat(FirstAndLastVisit.firstVisit("4")).isEqualTo("1990-10-01");
//        assertThat(FirstAndLastVisit.lastVisit("4")).isEqualTo("1999-10-01");
//        assertThat(FirstAndLastVisit.last01("4")).isEqualTo("1999-10-01");
//
//        assertThat(FirstAndLastVisit.firstVisit("5")).isEqualTo("1991-10-01");
//        assertThat(FirstAndLastVisit.lastVisit("5")).isEqualTo("1994-10-01");
//        assertThat(FirstAndLastVisit.last01("5")).isEqualTo("1994-10-01");
//
//        assertThat(FirstAndLastVisit.firstVisit("99")).isEqualTo("");
//        assertThat(FirstAndLastVisit.lastVisit("99")).isEqualTo("");
//        assertThat(FirstAndLastVisit.last01("99")).isEqualTo("");
//    }

    private Map<String, Map<String, String>> testTreatments() {
        Map<String, Map<String, String>> ret = new HashMap<>();

        Map<String, String> data_1 = new HashMap<>();
        data_1.put("first_visit", "2000-10-01");
        data_1.put("last_visit", "2010-10-01");
        ret.put("1", data_1);

        Map<String, String> data_3 = new HashMap<>();
        data_3.put("first_visit", "1990-10-01");
        data_3.put("last_visit", "1999-10-01");
        ret.put("3", data_3);

        Map<String, String> data_4 = new HashMap<>();
        data_4.put("first_visit", "1994-10-01");
        data_4.put("last_visit", "1995-10-01");
        ret.put("4", data_4);

        Map<String, String> data_5 = new HashMap<>();
        data_5.put("first_visit", "1991-10-01");
        data_5.put("last_visit", "1992-10-01");
        ret.put("5", data_5);

        return ret;
    }

    private Map<String, Map<String, String>> testEvidences() {
        Map<String, Map<String, String>> ret = new HashMap<>();

        Map<String, String> data_2 = new HashMap<>();
        data_2.put("first_visit", "2000-11-01");
        data_2.put("last_visit", "2010-11-01");
        ret.put("2", data_2);

        Map<String, String> data_3 = new HashMap<>();
        data_3.put("first_visit", "1994-10-01");
        data_3.put("last_visit", "1995-10-01");
        ret.put("3", data_3);

        Map<String, String> data_4 = new HashMap<>();
        data_4.put("first_visit", "1990-10-01");
        data_4.put("last_visit", "1999-10-01");
        ret.put("4", data_4);

        Map<String, String> data_5 = new HashMap<>();
        data_5.put("first_visit", "1993-10-01");
        data_5.put("last_visit", "1994-10-01");
        ret.put("5", data_5);

        return ret;
    }
}