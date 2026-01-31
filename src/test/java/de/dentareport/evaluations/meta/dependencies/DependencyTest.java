package de.dentareport.evaluations.meta.dependencies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class DependencyTest {

    private Dependency dependency;

    @BeforeEach
    public void setUp() {
        dependency = new Dependency("name_of_dependency", "group_by_foo", "order_by_bar");
    }

    @Test
    public void it_has_name() {
        assertThat(dependency.name()).isEqualTo("name_of_dependency");
    }

    @Test
    public void it_has_group_column() {
        assertThat(dependency.groupColumn()).isEqualTo("group_by_foo");
    }

    @Test
    public void it_has_order_column() {
        assertThat(dependency.orderColumn()).isEqualTo("order_by_bar");
    }
}