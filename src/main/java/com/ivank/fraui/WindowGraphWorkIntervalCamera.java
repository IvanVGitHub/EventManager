package com.ivank.fraui;

import com.ivank.fraui.utils.GraphDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class WindowGraphWorkIntervalCamera extends JFrame
{
    static String TITLE = "График работы камеры и количества событий";

    public WindowGraphWorkIntervalCamera(int idCamera) {
        super(TITLE);
        final XYDataset dataset = GraphDataset.createDataset(idCamera);
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 480));
        chartPanel.setMouseZoomable(true, false);
        setContentPane(chartPanel);

        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setVisible(true);
    }

    private JFreeChart createChart(final XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart (
                "Распределение событий по времени с 01.10.2022 по 31.10.2022", null, null,
                dataset, true, true, false);

        chart.setBackgroundPaint(Color.WHITE);

        XYPlot plot = chart.getXYPlot();

        plot.setBackgroundPaint(new Color(232, 232, 232));
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint (Color.LIGHT_GRAY);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible (true);

        // Скрытие осевых линий
        ValueAxis vaxis = plot.getDomainAxis();
        vaxis.setAxisLineVisible (false);
        vaxis = plot.getRangeAxis();
        vaxis.setAxisLineVisible (false);

        plot.getRenderer().setSeriesPaint(2, new Color(64, 255, 64));
        // Определение временной оси
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        // Формат отображения осевых меток
        axis.setDateFormatOverride(new SimpleDateFormat("dd.MM"));

        return chart;
    }
}
