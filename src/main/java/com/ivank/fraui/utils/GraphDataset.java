package com.ivank.fraui.utils;

import com.ivank.fraui.db.QueryCameraStatus;
import com.ivank.fraui.db.QueryEvent;
import org.apache.commons.lang.time.DateUtils;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class GraphDataset
{
    //тест
    public static void getGraph() {
        Calendar date = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        //получаем дату в привычном формате
//        System.out.println(dateFormat.format(date.getTime()));
        //переводим дату в миллисекунды
        System.out.println(date.getTimeInMillis());
        date.add(Calendar.HOUR, 1);
//        System.out.println(dateFormat.format(date.getTime()));
        System.out.println(date.getTimeInMillis());

//    public static Map<Integer, String> getGraph() {
        Map<Integer, String> states = new HashMap<Integer, String>();
        states.put(1, "Poland");
        states.put(2, "France");
        states.put(3, "Spain");
        states.put(4, "Italy");
        states.put(5, "Russia");

        // получим объект по ключу 2
        String first = states.get(2);
        System.out.println(first);
        // получим весь набор ключей
        Set<Integer> keys = states.keySet();
        // получить набор всех значений
        Collection<String> values = states.values();
        //заменить элемент
        states.replace(1, "Germany");
        // удаление элемента по ключу 2
        states.remove(3);
        // перебор элементов
        for (Map.Entry<Integer, String> item : states.entrySet()) {
            System.out.printf("Key: %d  Value: %s \n", item.getKey(), item.getValue());
        }

//        return states;
    }

    //создаём кривые
    public static XYDataset createDataset(int idCamera)
    {
        //создаём диаграмму
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        //список сессий
        ArrayList<UUID> listUUID = new ArrayList<>(QueryCameraStatus.getListSessionsCamera(idCamera));

        //перебираем все сессии для построения кривых на диаграмме
        for (int i = 0; i < listUUID.size(); i++) {
            //список дат в одной сессии
            ArrayList<Timestamp> listDate = new ArrayList<>(QueryCameraStatus.getListTimesSession(listUUID.get(i)));

            //почасовое количество Событий
            ArrayList<Integer> listValue = QueryEvent.getCountEventsEveryHourSessionCamera(listUUID.get(i));
            Map<Timestamp, Integer> dateAndValue = QueryEvent.getMapEventsEveryHourSessionCamera(listUUID.get(i));

            //если Событий не найдено
            if (listValue == null || listValue.isEmpty())
                continue;

            //создаём кривую
            TimeSeries timeSeries = new TimeSeries("Сессия: " + listUUID.get(i));

            //<-- тестовый блок
            //округляем временнУю метку до часа
            Date firstDate = DateUtils.truncate(listDate.get(0), Calendar.HOUR);
            Date lastDate = DateUtils.truncate(listDate.get(listDate.size() - 1), Calendar.HOUR);
//            timeSeries.add(new Millisecond(firstDate), 1);

            //если События случались чаще, чем в одном часе
            if (listDate.size() > 1) {
                try {
//                    timeSeries.add(new Millisecond(lastDate), 10);
                }
                catch (Exception e) {
                    int test = 0;
                }
            }

            //пустая почасовая последовательность данных
            TreeMap<Timestamp, Double> freeDateAndValue = new TreeMap<>();
//            Random rand = new Random();
            //берём начальное время и прибавляем по часу (3_600_000 мс), пока не наступит конечное время сессии
            //заносим все значения, начиная с первой даты, потом все ровные промежутки по часу, потом последнюю дату
            for (long start = 0; start < lastDate.getTime(); start += 3_600_000) {
                //заносим первую дату
                if (start == 0) {
                    freeDateAndValue.put(listDate.get(0), (double) 0);
                    timeSeries.add(new Millisecond(listDate.get(0)), 0);
                    //круглили до часа первое время, чтобы дальше прибавлять ровно по часу
                    start = DateUtils.truncate(listDate.get(0), Calendar.HOUR).getTime();
                    continue;
                }

                freeDateAndValue.put(new Timestamp(start), (double) 0);
                timeSeries.add(new Millisecond(new Timestamp(start)), 0);
            }
            freeDateAndValue.put(listDate.get(listDate.size() - 1), (double) 0);
            //сортировка по возрастанию даты и печатать в консоль
//            freeDateAndValue.entrySet().stream().sorted(Map.Entry.<Timestamp, Double>comparingByKey()).forEach(System.out::println);
            freeDateAndValue.entrySet().stream().forEach(e -> System.out.println(e.getKey() + ":" + e.getValue()));
            //--> тестовый блок

//            timeSeries.add(new Millisecond(listDate.get(0)), listValue.get(0));
            timeSeries.add(new Millisecond(listDate.get(listDate.size() - 1)), listValue.get(listValue.size() - 1));

            //добавляем кривую на диаграмму
            dataset.addSeries(timeSeries);
        }

/*        TimeSeries s1 = new TimeSeries("Камера: " + "'4 сыра общий план'");
        TimeSeries s2 = new TimeSeries("Камера: ");
        TimeSeries s3 = new TimeSeries("Камера: ");
        s1.add(new Day(1, 12, 2022), 3);
        s1.add(new Day(2, 12, 2022), 3);
        s1.add(new Day(3, 12, 2022), 3);
        s2.add(new Day(12, 12, 2022), 3);
        s2.add(new Day(13, 12, 2022), 3);
        s2.add(new Day(14, 12, 2022), 3);
        s3.add(new Day(16, 12, 2022), 3);
        s3.add(new Day(17, 12, 2022), 3);
        s3.add(new Day(18, 12, 2022), 4);
        dataset.addSeries(s1);
        dataset.addSeries(s2);
        dataset.addSeries(s3);*/

/*
        TimeSeries s1 = new TimeSeries("Камера '4 сыра общий план'");
        TimeSeries s2 = new TimeSeries("");
        TimeSeries s3 = new TimeSeries("");
        s1.add(new Day(1, 12, 2022), 58.0824);
        s1.add(new Day(2, 12, 2022), 57.1161);
        s1.add(new Day(3, 12, 2022), 57.1640);
        s1.add(new Day(4, 12, 2022), 56.5258);
        s1.add(new Day(5, 12, 2022), 56.2603);
        s1.add(new Day(6, 12, 2022), 56.2603);
        s1.add(new Day(7, 12, 2022), 56.2603);
        s1.add(new Day(8, 12, 2022), 6.2603);
        s1.add(new Day(9, 12, 2022), 5.2603);
        s1.add(new Day(10, 12, 2022), 55.2603);
        s1.add(new Day(11, 12, 2022), 54.2603);

        s2.add(new Day(12, 12, 2022), 56.2603);
        s2.add(new Day(13, 12, 2022), 56.2603);
        s2.add(new Day(14, 12, 2022), 56.2603);
        s2.add(new Day(15, 12, 2022), 7.2603);

        s3.add(new Day(16, 12, 2022), 8.2603);
        s3.add(new Day(17, 12, 2022), 56.2603);
        s3.add(new Day(18, 12, 2022), 56.2603);
        s3.add(new Day(19, 12, 2022), 56.2603);
        s3.add(new Day(20, 12, 2022), 56.2603);
        s3.add(new Day(21, 12, 2022), 56.2603);
        s3.add(new Day(22, 12, 2022), 5.2603);
        s3.add(new Day(23, 12, 2022), 2.2603);
        s3.add(new Day(24, 12, 2022), 52.2603);
        s3.add(new Day(25, 12, 2022), 56.2603);
        s3.add(new Day(26, 12, 2022), 35.2603);
        s3.add(new Day(27, 12, 2022), 69.2603);
        s3.add(new Day(28, 12, 2022), 56.2603);
        s3.add(new Day(29, 12, 2022), 9.2603);
        s3.add(new Day(30, 12, 2022), 6.2603);
        s3.add(new Day(31, 12, 2022), 40.2603);

        dataset.addSeries(s1);
        dataset.addSeries(s2);
        dataset.addSeries(s3);
*/
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
