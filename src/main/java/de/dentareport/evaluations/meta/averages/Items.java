package de.dentareport.evaluations.meta.averages;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.dentareport.utils.Keys;

import java.util.List;
import java.util.Map;

public class Items {

    public List<Map<String, String>> perPatient() {
        return ImmutableList.of(
                ImmutableMap.of(
                        "column", "case_count_for_patient",
                        "unit", Keys.UNIT_NUMBER,
                        "name", Keys.TEXT_CASE_COUNT_FOR_PATIENT
                )
        );
    }

    public List<Map<String, String>> perCase() {
        return ImmutableList.of(
                ImmutableMap.of(
                        "column", "count_days__from__date_start_observation__until__date_end_observation",
                        "unit", Keys.UNIT_DAYS,
                        "name", Keys.TEXT_OBSERVATION_PERIOD
                ),
                ImmutableMap.of(
                        "column", "age__at__event_start_observation",
                        "unit", Keys.UNIT_YEARS,
                        "name", Keys.TEXT_AGE_START_OBSERVATION
                ),
                ImmutableMap.of(
                        "column", "dmft__of__evidence_01_position_last_before_date_start_observation",
                        "unit", Keys.UNIT_DMFT,
                        "name", Keys.TEXT_DMFT_BEFORE_TREATMENT
                ),
                ImmutableMap.of(
                        "column", "toothcount_in_jaw__of__evidence_01_position_first_after_date_start_observation",
                        "unit", Keys.UNIT_NUMBER,
                        "name", Keys.TEXT_TOOTHCOUNT_IN_JAW
                )
        );
    }
}
