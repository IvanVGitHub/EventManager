package com.ivank.fraui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class Properties {
    public static void loadProperties() {
        try {
            //файл, который хранит свойства нашего проекта
            File file = new File("src/main/resources/config.properties");

            //создаем объект Properties и загружаем в него данные из файла.
            Properties properties = new Properties();
            properties.load(new FileReader(file));

            //получаем значения свойств из объекта Properties
            String email = properties.getProperty("email");
            String directory = properties.getProperty("directory");

            //получаем числовое значение из объекта Properties
            int maxFileSize = Integer.parseInt(properties.getProperty("max.size", "10000"));

        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
    }
}
