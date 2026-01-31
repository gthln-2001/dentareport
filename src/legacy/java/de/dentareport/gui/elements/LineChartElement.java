package de.dentareport.gui.elements;

import com.google.common.collect.Iterables;
import de.dentareport.Translate;
import de.dentareport.utils.number.DoubleUtils;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: TEST?
public class LineChartElement {

    private List<XYChart.Series<Number, Number>> lines = new ArrayList<>();
    private String title;
    private String xLabel;
    private String yLabel;
    private String id;
    private Boolean translateLegend = false;
    private Boolean autoMinY = false;
    private final Translate translate;

    public LineChartElement() {
        this.translate = new Translate();
    }

    public LineChart<Number, Number> create() {
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis(), yAxis());
        lineChart.setCreateSymbols(false);
        lineChart.setPrefWidth(700);
        lineChart.setTitle(title);
        lineChart.setId(id);

        for (XYChart.Series<Number, Number> line : lines) {
            if (translateLegend) {
                line.setName(translate.translate(line.getName()));
            }
            lineChart.getData().addAll(Collections.singletonList(line));
        }

        return lineChart;
    }

    public LineChartElement lines(List<XYChart.Series<Number, Number>> value) {
        lines = value;
        return this;
    }

    public LineChartElement title(String value) {
        title = value;
        return this;
    }

    public LineChartElement xLabel(String value) {
        xLabel = value;
        return this;
    }

    public LineChartElement yLabel(String value) {
        yLabel = value;
        return this;
    }

    public LineChartElement autoMinY() {
        autoMinY = true;
        return this;
    }

    public LineChartElement id(String value) {
        id = value;
        return this;
    }

    public LineChartElement translateLegend() {
        translateLegend = true;
        return this;
    }

    private NumberAxis yAxis() {
        final NumberAxis yAxis = new NumberAxis(minY(), 1, 0.1);
        yAxis.setLabel(yLabel);
        return yAxis;
    }

    private double minY() {
        if (!autoMinY) {
            return 0;
        }
        return calculateMinY();
    }

    private double calculateMinY() {
        Double minY = 1.0;
        for (XYChart.Series<Number, Number> line : lines) {
            minY = Math.min(minY, lastY(line));
        }
        return minY;
    }

    private Double lastY(XYChart.Series<Number, Number> line) {
        return DoubleUtils.roundDownToTenth(
                Iterables.getLast(line.getData())
                        .getYValue()
                        .doubleValue());
    }

    private NumberAxis xAxis() {
        final NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(xLabel);
        return xAxis;
    }
}
