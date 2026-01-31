package de.dentareport.evaluations.meta.dependencies;

import de.dentareport.utils.Keys;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class AvailableDependenciesTest {

    @Test
    public void it_gets_list_of_available_dependencies(@Mocked Dependencies mockDependencies,
                                                       @Mocked Dependency mockDependency) {

        new Expectations() {{
            mockDependencies.dependency(anyString);
        }};

        AvailableDependencies availableDependencies = new AvailableDependencies(Keys.EVALUATION_TYPE_TELESCOPIC_CROWN);


        assertThat(availableDependencies.dependencies()).isInstanceOf(List.class);
        assertThat(availableDependencies.dependencies().get(0)).isInstanceOf(Dependency.class);
    }
}