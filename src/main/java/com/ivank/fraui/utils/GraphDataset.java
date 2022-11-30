package com.ivank.fraui.utils;

import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class GraphDataset
{
    public static XYDataset createDataset()
    {
        final TimeSeries s1 = new TimeSeries("Камера '4 сыра общий план'");
        s1.add(new Day(1, 10, 2022), 58.0824);
        s1.add(new Day(2, 10, 2022), 57.1161);
        s1.add(new Day(3, 10, 2022), 57.1640);
        s1.add(new Day(4, 10, 2022), 56.5258);
        s1.add(new Day(5, 10, 2022), 56.2603);
        s1.add(new Day(6, 10, 2022), 56.2603);
        s1.add(new Day(7, 10, 2022), 56.2603);
        s1.add(new Day(8, 10, 2022), 6.2603);
        s1.add(new Day(9, 10, 2022), 5.2603);
        s1.add(new Day(10, 10, 2022), 55.2603);
        s1.add(new Day(11, 10, 2022), 54.2603);

        final TimeSeries s2 = new TimeSeries("");
        s2.add(new Day(12, 10, 2022), 56.2603);
        s2.add(new Day(13, 10, 2022), 56.2603);
        s2.add(new Day(14, 10, 2022), 56.2603);
        s2.add(new Day(15, 10, 2022), 7.2603);

        final TimeSeries s3 = new TimeSeries("");
        s3.add(new Day(16, 10, 2022), 8.2603);
        s3.add(new Day(17, 10, 2022), 56.2603);
        s3.add(new Day(18, 10, 2022), 56.2603);
        s3.add(new Day(19, 10, 2022), 56.2603);
        s3.add(new Day(20, 10, 2022), 56.2603);
        s3.add(new Day(21, 10, 2022), 56.2603);
        s3.add(new Day(22, 10, 2022), 5.2603);
        s3.add(new Day(23, 10, 2022), 2.2603);
        s3.add(new Day(24, 10, 2022), 52.2603);
        s3.add(new Day(25, 10, 2022), 56.2603);
        s3.add(new Day(26, 10, 2022), 35.2603);
        s3.add(new Day(27, 10, 2022), 69.2603);
        s3.add(new Day(28, 10, 2022), 56.2603);
        s3.add(new Day(29, 10, 2022), 9.2603);
        s3.add(new Day(30, 10, 2022), 6.2603);
        s3.add(new Day(31, 10, 2022), 40.2603);

        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s1);
        dataset.addSeries(s2);
        dataset.addSeries(s3);
        return dataset;
    }
    public static Hour getHour(final int value)
    {
        return new Hour(value, getDay());
    }
    public static Day getDay()
    {
        return new Day(15, 8, 2017);
    }
    public static XYDataset createSuppliersBids()
    {
        final Hour hour  = getHour(1);
        final Hour hour1 = getHour(1);
        final Hour hour2 = (Hour) hour1.next();

        final TimeSeries series1 = new TimeSeries("Поставщик 1");
        series1.add(new Minute(13, hour), 200.0);
        series1.add(new Minute(14, hour), 195.0);

        final TimeSeries series2 = new TimeSeries("Поставщик 2");
        series2.add(new Minute(25, hour1), 185.0);
        series2.add(new Minute( 0, hour2), 175.0);
        series2.add(new Minute( 5, hour2), 170.0);

        TimeSeriesCollection result = new TimeSeriesCollection();
        result.addSeries(series1);
        result.addSeries(series2);
        return result;
    }
}
