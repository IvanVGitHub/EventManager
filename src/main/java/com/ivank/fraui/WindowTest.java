package com.ivank.fraui;

import com.ivank.fraui.utils.Dataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.ApplicationFrame;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.text.SimpleDateFormat;

public class WindowTest extends ApplicationFrame
{

    private static final long serialVersionUID = 1L;
    static String TITLE = "Курс валюты, цена нефти марки Brent";

    public WindowTest() {

        super("График тестовый");
        final XYDataset dataset = Dataset.createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 480));
        chartPanel.setMouseZoomable(true, false);
        setContentPane(chartPanel);

        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setVisible(true);
    }

    private JFreeChart createChart(final XYDataset dataset)
    {
        JFreeChart chart = ChartFactory.createTimeSeriesChart (
                "Валюта, нефть с 11.05.2017 по 25.05.2017", null, null,
                dataset, true, true, false);

        chart.setBackgroundPaint(Color.white);

        XYPlot plot = chart.getXYPlot();

        plot.setBackgroundPaint(new Color(232, 232, 232));
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint (Color.lightGray);
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
    public static void main(final String[] args)
    {
    }
}
