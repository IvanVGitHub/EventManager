package com.ivank.fraui.db;


import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;

//запросы на чистом SQL
public class QueryTEST {
    //первое изображение события на чистом SQL
    public static ImageIcon getEventFirstImage(int event_id) {
        ResultSet resultSet = null;

        try (Statement statement = ConnectDB.getConnectorClearSQL().createStatement()) {

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
