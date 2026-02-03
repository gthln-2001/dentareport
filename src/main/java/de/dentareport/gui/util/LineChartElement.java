package de.dentareport.gui.util;

import com.google.common.collect.Iterables;
import de.dentareport.Translate;
import de.dentareport.utils.number.DoubleUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LineChartElement {

    private List<XYSeries> lines = new ArrayList<>();
    private String title;
    private String xLabel;
    private String yLabel;
    private String id;
    private boolean translateLegend = false;
    private boolean autoMinY = false;

    private final Translate translate;

    public LineChartElement() {
        this.translate = new Translate();
    }

    public JPanel create() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (XYSeries line : lines) {
            if (translateLegend) {
                line.setKey(translate.translate(line.getKey().toString()));
            }
            dataset.addSeries(line);
        }
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                xLabel,
                yLabel,
                dataset
        );
        XYPlot plot = chart.getXYPlot();


        // -----------------------------
        // FlatLaf-style design
        // -----------------------------
        chart.setBackgroundPaint(java.awt.Color.WHITE);
        chart.getLegend().setBackgroundPaint(new java.awt.Color(240, 240, 240));
        plot.setBackgroundPaint(new java.awt.Color(240, 240, 240));
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(false);
        java.awt.Color grid = new java.awt.Color(220, 220, 220);
        plot.setDomainGridlinePaint(grid);
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        xAxis.setAxisLinePaint(java.awt.Color.GRAY);
        yAxis.setAxisLinePaint(java.awt.Color.GRAY);
        xAxis.setTickMarkPaint(java.awt.Color.GRAY);
        yAxis.setTickMarkPaint(java.awt.Color.GRAY);
        chart.setBorderVisible(false);
        java.awt.Font axisLabelFont = new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18);
        java.awt.Font tickFont = new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18);
        java.awt.Font legendFont = new java.awt.Font("Dialog", java.awt.Font.PLAIN, 20);
        xAxis.setLabelFont(axisLabelFont);
        yAxis.setLabelFont(axisLabelFont);
        xAxis.setTickLabelFont(tickFont);
        yAxis.setTickLabelFont(tickFont);
        if (chart.getLegend() != null) {
            chart.getLegend().setItemFont(legendFont);
        }


        configureYAxis(plot);
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new java.awt.Dimension(1200, 480));
        if (id != null) {
            panel.putClientProperty("id", id);
        }
        return panel;
    }

    public LineChartElement lines(List<XYSeries> value) {
        this.lines = value;
        return this;
    }

    public LineChartElement title(String value) {
        this.title = value;
        return this;
    }

    public LineChartElement xLabel(String value) {
        this.xLabel = value;
        return this;
    }

    public LineChartElement yLabel(String value) {
        this.yLabel = value;
        return this;
    }

    public LineChartElement autoMinY() {
        this.autoMinY = true;
        return this;
    }

    public LineChartElement id(String value) {
        this.id = value;
        return this;
    }

    public LineChartElement translateLegend() {
        this.translateLegend = true;
        return this;
    }

    private void configureYAxis(XYPlot plot) {
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        if (autoMinY) {
            yAxis.setLowerBound(calculateMinY());
        } else {
            yAxis.setLowerBound(0);
        }
    }

    private double calculateMinY() {
        double minY = 1.0;
        for (XYSeries line : lines) {
            minY = Math.min(minY, lastY(line));
        }
        return minY;
    }

    private double lastY(XYSeries line) {
        int lastIndex = line.getItemCount() - 1;
        return DoubleUtils.roundDownToTenth(
                line.getY(lastIndex).doubleValue()
        );
    }
}
