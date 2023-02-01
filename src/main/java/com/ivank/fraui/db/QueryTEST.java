package com.ivank.fraui.db;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

//запросы на чистом SQL
public class QueryTEST {
    //первое изображение События
    public static ImageIcon getEventFirstImage(int event_id) {
        ImageIcon imageIcon = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT image FROM eventImages ")
                    .append("WHERE event_id = " + event_id + " ")
                    .append("LIMIT 1;");
            String stringSQL = sb.toString();
            ResultSet result = ConnectDB.getConnector().executeRaw(stringSQL);
            String stringImage = "";
            //обязательный цикл, чтобы получить результаты из запроса и присвоить их переменным
            while (result.next()) {
                stringImage = result.getString("image");
            }

            byte[] byteImageBase64 = Base64.getDecoder().decode(stringImage);
            imageIcon = new ImageIcon(byteImageBase64);

            return imageIcon;
        } catch (Exception ex) {ex.printStackTrace();}

        return imageIcon;
    }
}
