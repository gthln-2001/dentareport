package de.dentareport.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class PointTest {

    private Point point;

    @BeforeEach
    public void setUp() {
        this.point = new Point(3.4, 5.6);
    }

    @Test
    public void it_has_x_value() {
        assertThat(point.x()).isEqualTo(3.4);
    }

    @Test
    public void it_has_y_value() {
        assertThat(point.y()).isEqualTo(5.6);
    }
}