package com.ivank.fraui;

import com.ivank.fraui.utils.GraphDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;

import static com.ivank.fraui.db.QueryCamera.getModelCameraById;

public class WindowGraphWorkIntervalCamera extends JFrame
{
    static String TITLE = "График распределения событий по времени ";

    public WindowGraphWorkIntervalCamera(int idCamera) {
        super(TITLE);
        final XYDataset dataset = GraphDataset.createDataset(idCamera);
        GraphDataset.getGraph();
        final JFreeChart chart = createChart(dataset, getModelCameraById(idCamera).camera_name);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(560, 480));
        chartPanel.setMouseZoomable(true, false);
        setContentPane(chartPanel);

        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setVisible(true);
    }

    private JFreeChart createChart(XYDataset dataset, String cameraName) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart (
                "График распределения событий по времени для камеры: \"" + cameraName + "\"", null, "посетители",
                dataset, true, true, false);

        chart.setBackgroundPaint(Color.WHITE);

        XYPlot plot = chart.getXYPlot();

        plot.setBackgroundPaint(new Color(232, 232, 232));
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        //перекрестие на пИке
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        //устанавливаем границы по оси Y
//        plot.getRangeAxis().setRange(-5, 5);
//        //устанавливаем нижнее поле для оси (в процентах от диапазона оси)
//        plot.getRangeAxis().setLowerMargin(10);
//        plot.getDomainAxis().setLowerMargin(10);
//        //устанавливаем верхнее поле для оси (в процентах от диапазона оси)
//        plot.getRangeAxis().setUpperMargin(10);
//        plot.getDomainAxis().setUpperMargin(10);

        //скрытие осевых линий
        //x
        plot.getDomainAxis().setAxisLineVisible(false);
        //y
        plot.getRangeAxis().setAxisLineVisible(false);

        plot.getRenderer().setSeriesPaint(2, new Color(64, 255, 64));
//        // Определение временной оси
//        DateAxis axis = (DateAxis) plot.getDomainAxis();
//        // Формат отображения осевых меток
//        axis.setDateFormatOverride(new SimpleDateFormat("dd.MM"));\chart.repaint();
        //задаём точки на графике, чтобы они отображались
        XYLineAndShapeRenderer xyLineAndShapeRenderer = (XYLineAndShapeRenderer) plot.getRenderer();
        xyLineAndShapeRenderer.setBaseShapesVisible(true);
        plot.setRenderer(xyLineAndShapeRenderer);

        return chart;
    }
}
