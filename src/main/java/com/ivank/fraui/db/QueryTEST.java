package com.ivank.fraui.db;

import com.ivank.fraui.settings.AppConfig;

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
    private static Connection getConnectorClearSQL() {
        StringBuilder connectionUrl;
        Connection connection = null;

        try {
            connectionUrl = new StringBuilder(
                    "jdbc:mysql://" +
                            AppConfig.getInstance().getConnection().host +
                            ":3306/" +
                            AppConfig.getInstance().getConnection().database
            );
            connection = DriverManager.getConnection(
                    String.valueOf(connectionUrl),
                    AppConfig.getInstance().getConnection().username,
                    AppConfig.getInstance().getConnection().password
            );
        } catch (Exception ex) {ex.printStackTrace();}

        return connection;
    }

    //список записей времени создания события
    private static ArrayList<String> listTimeStampEvents = new ArrayList<>();

    //проверка наличия хотя бы одной записи image у события
    public static ArrayList<String> getListTimeStampEvents(int camera_id) {
        ResultSet resultSet = null;
        listTimeStampEvents.clear();

        try (Statement statement = getConnectorClearSQL().createStatement()) {

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
        } catch (SQLException ex) {ex.printStackTrace();}

        return listTimeStampEvents;
    }

    //первое изображение события при помощи "чистого" SQL запроса
    public static ImageIcon getEventFirstImage(int event_id) {
        ResultSet resultSet = null;

        try (Statement statement = getConnectorClearSQL().createStatement()) {

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
        } catch (SQLException ex) {ex.printStackTrace();}

        return null;
    }
}
