package de.dentareport.evaluations.meta.dependencies;

import de.dentareport.utils.Keys;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class DependenciesTest {

    @Test
    public void it_gets_dependency() {
        Dependencies dependencies = new Dependencies();

        assertThat(dependencies.dependency(Keys.GUI_TEXT_AGE_START_OBSERVATION)).isInstanceOf(Dependency.class);
    }

    @Test
    public void it_gets_group_column() {
        Dependencies dependencies = new Dependencies();

        assertThat(dependencies.groupColumn(Keys.GUI_TEXT_AGE_START_OBSERVATION)).isInstanceOf(String.class);
    }

    @Test
    public void it_gets_order_column() {
        Dependencies dependencies = new Dependencies();

        assertThat(dependencies.orderColumn(Keys.GUI_TEXT_AGE_START_OBSERVATION)).isInstanceOf(String.class);
    }
}