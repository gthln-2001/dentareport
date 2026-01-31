package de.dentareport.evaluations.meta.dependencies;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.dentareport.utils.Keys;

import java.util.List;
import java.util.Map;

// TODO: TEST?
public class AvailableDependencies {

    private String evaluationType;
    private Dependencies dependencies;

    public AvailableDependencies(String evaluationType) {
        this.evaluationType = evaluationType;
        this.dependencies = new Dependencies();
    }

    public List<Dependency> dependencies() {
        return availableDependencies().get(evaluationType);
    }

    private Map<String, List<Dependency>> availableDependencies() {
        return ImmutableMap.of(
                Keys.EVALUATION_TYPE_FILLING, ImmutableList.of(
                        dependencies.dependency(Keys.GUI_TEXT_NO_DEPENDENCY),
                        dependencies.dependency(Keys.GUI_TEXT_AGE_START_OBSERVATION),
                        dependencies.dependency(Keys.GUI_TEXT_TOOTHCOUNT_IN_JAW),
                        dependencies.dependency(Keys.GUI_TEXT_DMFT),
                        dependencies.dependency(Keys.GUI_TEXT_GENDER),
                        dependencies.dependency(Keys.GUI_TEXT_ENDSTAENDIGER_PFEILER),
                        dependencies.dependency(Keys.GUI_TEXT_TOOTHCONTACTS),
                        dependencies.dependency(Keys.GUI_TEXT_TOOTHTYPE)
                ),
                Keys.EVALUATION_TYPE_TELESCOPIC_CROWN, ImmutableList.of(
                        dependencies.dependency(Keys.GUI_TEXT_NO_DEPENDENCY),
                        dependencies.dependency(Keys.GUI_TEXT_AGE_START_OBSERVATION),
                        dependencies.dependency(Keys.GUI_TEXT_TOOTHCOUNT_IN_JAW),
                        dependencies.dependency(Keys.GUI_TEXT_DMFT),
                        dependencies.dependency(Keys.GUI_TEXT_GENDER),
                        dependencies.dependency(Keys.GUI_TEXT_ENDSTAENDIGER_PFEILER),
                        dependencies.dependency(Keys.GUI_TEXT_TOOTHCONTACTS),
                        dependencies.dependency(Keys.GUI_TEXT_TOOTHTYPE)
                )
        );
    }
}
