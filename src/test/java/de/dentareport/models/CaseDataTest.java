package de.dentareport.models;

import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class CaseDataTest {

    private CaseData caseData;

    @BeforeEach
    public void setUp() {
        this.caseData = new CaseData("42");
    }

    @Test
    public void it_has_tooth() {
        assertThat(caseData.tooth()).isEqualTo("42");
    }

    @Test
    public void it_has_string_data() {
        caseData.setString("foo", "bar");

        assertThat(caseData.string("foo")).isEqualTo("bar");
    }

    @Test
    public void it_throws_exception_when_trying_to_access_string_data_that_has_not_been_set() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                caseData.string("not_set"));
    }

    @Test
    public void it_has_event_data(@Mocked EventInterface mockEventInterface) {
        caseData.setEvent("foo", mockEventInterface);

        assertThat(caseData.event("foo")).isEqualTo(mockEventInterface);
    }

    @Test
    public void it_gets_date_of_event(@Mocked EventInterface mockEventInterface) {
        new Expectations() {{
            mockEventInterface.date();
            result = "some-date";
        }};

        caseData.setEvent("foo", mockEventInterface);

        assertThat(caseData.dateOfEvent("foo")).isEqualTo("some-date");
    }

    @Test
    public void it_gets_endstaendigkeit_of_event(@Mocked EventInterface mockEventInterface) {
        new Expectations() {{
            mockEventInterface.endstaendigkeit("22");
            result = "foobar";
        }};

        caseData.setEvent("foo", mockEventInterface);

        assertThat(caseData.endstaendigkeitOfEvent("foo", "22")).isEqualTo("foobar");
    }

    @Test
    public void it_gets_toothcontacts_of_event(@Mocked EventInterface mockEventInterface) {
        new Expectations() {{
            mockEventInterface.toothcontacts("22");
            result = "foobar";
        }};

        caseData.setEvent("foo", mockEventInterface);

        assertThat(caseData.toothcontactsOfEvent("foo", "22")).isEqualTo("foobar");
    }

    @Test
    public void it_gets_toothcount_in_jaw_of_event(@Mocked EventInterface mockEventInterface) {
        new Expectations() {{
            mockEventInterface.toothcountInJaw("22");
            result = "foobar";
        }};

        caseData.setEvent("foo", mockEventInterface);

        assertThat(caseData.toothcountInJawOfEvent("foo", "22")).isEqualTo("foobar");
    }

    @Test
    public void it_gets_insurance_of_event(@Mocked EventInterface mockEventInterface) {
        new Expectations() {{
            mockEventInterface.insurance();
            result = "some-insurance";
        }};

        caseData.setEvent("foo", mockEventInterface);

        assertThat(caseData.insuranceOfEvent("foo")).isEqualTo("some-insurance");
    }

    @Test
    public void it_throws_exception_when_trying_to_access_event_data_that_has_not_been_set() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                caseData.event("not_set"));
    }

    @Test
    public void it_has_eventlist_data(@Mocked Eventlist mockEventlist) {
        caseData.setEventlist("foo", mockEventlist);
        assertThat(caseData.eventlist("foo")).isEqualTo(mockEventlist);
    }

    @Test
    public void it_throws_exception_when_trying_to_access_eventlist_data_that_has_not_been_set() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                caseData.eventlist("not_set"));
    }

    @Test
    public void it_gets_events_from_eventlist(@Mocked Eventlist mockEventlist,
                                              @Mocked EventInterface mockEventinterface) {
        new Expectations() {{
           mockEventlist.event("42");
        }};

        caseData.setEventlist("foo", mockEventlist);

        assertThat(caseData.eventFromEventlist("foo", "42")).isEqualTo(mockEventinterface);
    }
}