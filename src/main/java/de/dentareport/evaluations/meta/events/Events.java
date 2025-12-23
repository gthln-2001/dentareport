package de.dentareport.evaluations.meta.events;

import com.google.common.collect.ImmutableMap;
import de.dentareport.utils.Keys;

import java.util.Map;

public class Events {

    private Map<String, Event> events = ImmutableMap.<String, Event>builder()
            .put(Keys.GUI_TEXT_TOOTHLOSS,
                    create(Keys.GUI_TEXT_TOOTHLOSS,
                            "censored__of__treatment_position_first_with_" + Keys.EXTRACTION + "_after_date_start_observation",
                            "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + Keys.EXTRACTION + "_after_date_start_observation__or_until__date_end_search_period"))
            .put(Keys.GUI_TEXT_REZEMENTIERUNG,
                    create(Keys.GUI_TEXT_REZEMENTIERUNG,
                            "censored__of__treatment_position_first_with_" + Keys.REZEMENTIERUNG + "_after_date_start_observation",
                            "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + Keys.REZEMENTIERUNG + "_after_date_start_observation__or_until__date_end_search_period"))
            .put(Keys.GUI_TEXT_ENDODONTICS,
                    create(Keys.GUI_TEXT_ENDODONTICS,
                            "censored__of__treatment_position_first_with_" + Keys.VITE_TREP_WK + "_after_date_start_observation",
                            "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + Keys.VITE_TREP_WK + "_after_date_start_observation__or_until__date_end_search_period"))
            .put(Keys.GUI_TEXT_WURZELSTIFT,
                    create(Keys.GUI_TEXT_WURZELSTIFT,
                            "censored__of__treatment_position_first_with_" + Keys.WURZELSTIFT + "_after_date_start_observation",
                            "count_days__from__date_start_observation__until__date_of_treatment_position_first_with_" + Keys.WURZELSTIFT + "_after_date_start_observation__or_until__date_end_search_period"
                    ))
            .build();

    public Event event(String name) {
        return events.get(name);
    }

    private Event create(String name,
                         String censoredColumn,
                         String intervalColumn) {
        return new Event(
                name,
                censoredColumn,
                intervalColumn
        );
    }
}
