package de.dentareport.gui.elements;

import de.dentareport.Translate;
import de.dentareport.gui.BaseFxElementTest;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
// TODO: TEST?
public class LineChartElementTest extends BaseFxElementTest {

    @Mock
    private Translate mockTranslate;

    private LineChartElement lineChartElement;

    @BeforeEach
    public void setUp() {
        lineChartElement = new LineChartElement();
    }

    @Test
    public void it_creates_line_chart() {
        LineChart<Number, Number> lineChart = lineChartElement.create();

        assertThat(lineChart).isInstanceOf(LineChart.class);
    }

    @Test
    public void it_sets_line_chart_title() {
        LineChart<Number, Number> lineChart = lineChartElement.title("foo title").create();

        assertThat(lineChart.getTitle()).isEqualTo("foo title");
    }

    @Test
    public void it_sets_label_for_x_axis() {
        LineChart<Number, Number> lineChart = lineChartElement.xLabel("foo").create();

        assertThat(lineChart.getXAxis().getLabel()).isEqualTo("foo");
    }

    @Test
    public void it_sets_label_for_y_axis() {
        LineChart<Number, Number> lineChart = lineChartElement.yLabel("bar").create();

        assertThat(lineChart.getYAxis().getLabel()).isEqualTo("bar");
    }

    @Test
    public void it_sets_id() {
        LineChart<Number, Number> lineChart = lineChartElement.id("some-id").create();

        assertThat(lineChart.getId()).isEqualTo("some-id");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void it_adds_lines_to_chart() {
        XYChart.Series<Number, Number> line1 = new XYChart.Series<>();
        XYChart.Series<Number, Number> line2 = new XYChart.Series<>();
        XYChart.Series<Number, Number> line3 = new XYChart.Series<>();

        List<XYChart.Series<Number, Number>> lines = new ArrayList<>();
        lines.add(line1);
        lines.add(line3);

        LineChart<Number, Number> lineChart = lineChartElement.lines(lines).create();

        ObservableList<XYChart.Series<Number, Number>> data = lineChart.getData();
        assertThat(data).contains(line1);
        assertThat(data).doesNotContain(line2);
        assertThat(data).contains(line3);
    }

    // TODO: Fix test
//    @Test
//    public void it_translates_line_names_for_legend() {
//
//        when(mockTranslate.translate("foo")).thenReturn("translated foo");
//
//        XYChart.Series<Number, Number> line = new XYChart.Series<>();
//        line.setName("foo");
//
//        List<XYChart.Series<Number, Number>> lines = new ArrayList<>();
//        lines.add(line);
//
//        LineChart<Number, Number> lineChart = lineChartElement.lines(lines).translateLegend().create();
//
//        ObservableList<XYChart.Series<Number, Number>> data = lineChart.getData();
//        assertThat(data.get(0).getName()).isEqualTo("translated foo");
//    }
}