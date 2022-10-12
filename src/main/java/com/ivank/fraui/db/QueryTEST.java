package com.ivank.fraui.db;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;

//запросы ан "чистом" SQL
public class QueryTEST {
    //список записей времени создания события
    private static ArrayList<String> listTimeStampEvents = new ArrayList<>();

    //проверка наличия хотя бы одной записи image у события
    public static ArrayList<String> getListTimeStampEvents(int camera_id) {
        String connectionUrl = "jdbc:mysql://172.20.3.231:3306/test";
        ResultSet resultSet = null;
        listTimeStampEvents.clear();

        try (Connection connection = DriverManager.getConnection(connectionUrl, "ivanUser", "Qwerty!@#456");
             Statement statement = connection.createStatement();) {

            // Create and execute a SELECT SQL statement.
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM event WHERE EXISTS(SELECT * FROM eventImages WHERE event.id = eventImages.event_id AND image IS NOT NULL) AND camera_id = ").append(camera_id).append(" ORDER BY time DESC;");
            String stringSql = String.valueOf(sb);
            resultSet = statement.executeQuery(stringSql);
            //обязательный цикл, чтобы получить результаты из запроса и присвоить их переменным
            while (resultSet.next()) {
                listTimeStampEvents.add(resultSet.getString("time"));
            }

            return listTimeStampEvents;
        } catch (SQLException e) {e.printStackTrace();}

        return listTimeStampEvents;
    }

    //первое изображение события при помощи "чистого" SQL запроса
    public static ImageIcon getEventFirstImage(int event_id) {
        String connectionUrl = "jdbc:mysql://172.20.3.231:3306/test";
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl, "ivanUser", "Qwerty!@#456");
             Statement statement = connection.createStatement();) {
//            ConnectDB.getConnector().executeRaw("SELECT * FROM table");

            // Create and execute a SELECT SQL statement.
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT image from eventImages WHERE event_id = ").append(event_id).append(" LIMIT 1;");
            String stringSql = String.valueOf(sb);
            resultSet = statement.executeQuery(stringSql);
            String stringImage = "";
            //обязательный цикл, чтобы получить результаты из запроса и присвоить их переменным
            while (resultSet.next()) {
                stringImage = resultSet.getString("image");
            }

            byte[] byteImageBase64 = Base64.getDecoder().decode(stringImage);
            ImageIcon imageIcon = new ImageIcon(byteImageBase64);

            return imageIcon;
        } catch (SQLException e) {e.printStackTrace();}

        return null;
    }
}
