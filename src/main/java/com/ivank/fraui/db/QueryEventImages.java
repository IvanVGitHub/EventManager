package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;

import javax.swing.*;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.StringJoiner;

public class QueryEventImages {
    //список изображений события (event)
    private static ArrayList<ImageIcon> listEventImages = new ArrayList<>();

    //список моделей конкретного событая в таблице eventImages
    public static ArrayList<ModelEventImages> getListModelEventImages(int event_id) {
        try {
            QueryBuilder<ModelEventImages> query = ConnectDB.getConnector().query(ModelEventImages.class)
                    .where("event_id", event_id);

            return query.get();
        } catch (Exception ex) {ex.printStackTrace();}

        return null;
    }

    //проверка наличия хотя бы одной записи image у события
    public static boolean getBoolEventHaveImage(int event_id) {
        boolean bool = false;

        try {
            bool = ConnectDB.getConnector().query(ModelEventImages.class)
                    .where("event_id", event_id)
                    .whereIsNotNull("image")
                    .exists();
        } catch (Exception ex) {ex.printStackTrace();}

        return bool;
    }

    //первое изображение события
    public static ImageIcon getEventFirstImage(int event_id) {
        try {
            ModelEventImages result = ConnectDB.getConnector().query(ModelEventImages.class)
                    .where("event_id", event_id)
                    .first();

            if (result != null) {
                byte[] byteImageBase64 = Base64.getDecoder().decode(result.image);
                ImageIcon imageIcon = new ImageIcon(byteImageBase64);

                return imageIcon;
            } else {
                return null;
            }
        } catch (Exception ex) {ex.printStackTrace();}

        return null;
    }

    //список изображений конкретного события
    public static ArrayList<ImageIcon> getListEventImages(int event_id) {
        listEventImages.clear();

        try {
            for (ModelEventImages unit : getListModelEventImages(event_id)) {
                byte[] byteImageBase64 = Base64.getDecoder().decode(unit.image);
                listEventImages.add(new ImageIcon(byteImageBase64));
            }
        } catch (Exception ex) {ex.printStackTrace();}

        return listEventImages;
    }

    //список первых изображений из списка id событий (event_id) на чистом SQL
    public static ArrayList<ImageIcon> getListEventImagesSQL(ArrayList<Integer> listIndexEventsId) {
        java.sql.ResultSet resultSet = null;
        listEventImages.clear();
        StringBuilder sb = new StringBuilder();

        try (Statement statement = ConnectDB.getConnectorClearSQL().createStatement()) {
            //создаём особым образом отформатированную строку из списка
            StringJoiner joiner = new StringJoiner(", ");
            for (int i : listIndexEventsId)
                joiner.add(Integer.toString(i));
            String value = joiner.toString();

            sb.append("SELECT eventImages.id, event_id, image\n" +
                    "FROM eventImages\n" +
                    "    INNER JOIN (\n" +
                    "    SELECT MIN(id)\n" +
                    "        AS id\n" +
                    "    FROM eventImages\n" +
                    "    WHERE event_id IN (")
                    .append(value)
                    .append(")\n" +
                            "    GROUP BY event_id\n" +
                            "    ) subquery\n" +
                            "        ON eventImages.id = subquery.id\n" +
                            "ORDER BY event_id DESC;");

            // Create and execute a SELECT SQL statement.
            String stringSQL = String.valueOf(sb);
            resultSet = statement.executeQuery(stringSQL);
            //обязательный цикл, чтобы получить результаты из запроса и присвоить их переменным
            while (resultSet.next()) {
                if (resultSet.getString("image") != null) {
                    byte[] byteImageBase64 = Base64.getDecoder().decode(resultSet.getString("image"));
                    listEventImages.add(new ImageIcon(byteImageBase64));
                } else {
                    listEventImages.add(null);
                }
            }
        } catch (Exception ex) {ex.printStackTrace();}

        return listEventImages;
    }
}
