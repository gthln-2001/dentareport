package de.dentareport.evaluations.meta.dependencies;

import com.google.common.collect.ImmutableMap;
import de.dentareport.utils.Keys;

import java.util.Map;

public class Dependencies {

    private Map<String, Dependency> dependencies = ImmutableMap.<String, Dependency>builder()
            .put(Keys.GUI_TEXT_NO_DEPENDENCY,
                    create(
                            Keys.GUI_TEXT_NO_DEPENDENCY,
                            "")
            )
            .put(Keys.GUI_TEXT_AGE_START_OBSERVATION,
                    create(
                            Keys.GUI_TEXT_AGE_START_OBSERVATION,
                            "groups__of__age_at_event_start_observation",
                            "age__at__event_start_observation")
            )
            .put(Keys.GUI_TEXT_TOOTHCOUNT_IN_JAW,
                    create(
                            Keys.GUI_TEXT_TOOTHCOUNT_IN_JAW,
                            "groups__of__toothcount_in_jaw_of_evidence_01_position_first_after_date_start_observation",
                            "toothcount_in_jaw__of__evidence_01_position_first_after_date_start_observation")
            )
            .put(Keys.GUI_TEXT_DMFT,
                    create(
                            Keys.GUI_TEXT_DMFT,
                            "groups__of__dmft_of_evidence_01_position_last_before_date_start_observation",
                            "dmft__of__evidence_01_position_last_before_date_start_observation")
            )
            .put(Keys.GUI_TEXT_GENDER,
                    create(Keys.GUI_TEXT_GENDER, "gender")
            )
            .put(Keys.GUI_TEXT_ENDSTAENDIGER_PFEILER,
                    create(
                            Keys.GUI_TEXT_ENDSTAENDIGER_PFEILER,
                            "endstaendigkeit__of__evidence_01_position_first_after_date_start_observation")
            )
            .put(Keys.GUI_TEXT_TOOTHCONTACTS,
                    create(
                            Keys.GUI_TEXT_TOOTHCONTACTS,
                            "toothcontacts__of__evidence_01_position_first_after_date_start_observation")
            )
            .put(Keys.GUI_TEXT_TOOTHTYPE,
                    create(
                            Keys.GUI_TEXT_TOOTHTYPE,
                            "toothtype")
            ).build();

    public Dependency dependency(String name) {
        return dependencies.get(name);
    }

    public String groupColumn(String name) {
        return dependency(name).groupColumn();
    }

    public String orderColumn(String name) {
        return dependency(name).orderColumn();
    }

    private Dependency create(String name,
                              String groupColumn) {
        return create(name, groupColumn, groupColumn);
    }

    private Dependency create(String name,
                              String groupColumn,
                              String orderColumn) {
        return new Dependency(
                name,
                groupColumn,
                orderColumn
        );
    }
}
