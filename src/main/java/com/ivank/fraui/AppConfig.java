package com.ivank.fraui;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {
    //for connect to DB
    public static String host;
    public static String database;
    public static String username;
    public static String password;
    //длина выборки событий (полученных из БД) для группы событий
    public static int limitEvent;

    public static void loadProperties() {
        try {
            //файл, который хранит свойства нашего проекта
            File file = new File("src/main/resources/config.properties");

            //создаём объект Properties и загружаем в него данные из файла.
            Properties properties = new Properties();
            properties.load(new FileReader(file));

            //получаем значения свойств из объекта Properties
            host = properties.getProperty("host");
            database = properties.getProperty("database");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            limitEvent = Integer.parseInt(properties.getProperty("limitEvent"));

            //получаем числовое значение из объекта Properties

        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
    }
}
