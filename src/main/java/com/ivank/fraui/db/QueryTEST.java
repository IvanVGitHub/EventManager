package com.ivank.fraui.db;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

public class QueryTEST {
    //первое изображение события
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
            while (resultSet.next()) {
                stringImage = resultSet.getString("image");
            }

            byte[] byteImageBase64 = Base64.getDecoder().decode(stringImage);
            ImageIcon imageIcon = new ImageIcon(byteImageBase64);

            return imageIcon;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
