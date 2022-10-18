package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.StringJoiner;

public class QueryEventImages {
    //список изображений события (event)
    private static ArrayList<ImageIcon> listEventImages = new ArrayList<>();

    //список моделей конкретного событая
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

    //список первых изображений из списка event_id (id событий)
    public static ArrayList<ImageIcon> getListEventImagesSQL(ArrayList<Integer> listIndexEventsId) {
        listEventImages.clear();

        try {
            String values = StringUtils.join(listIndexEventsId.toArray(), ", ");

            StringBuilder sb = new StringBuilder();
            sb.append("SELECT image FROM eventImages ")
                    .append("INNER JOIN (")
                    .append("SELECT MIN(id) AS id FROM eventImages ")
                    .append("WHERE event_id IN (").append(values).append(") ")
                    .append("GROUP BY event_id) subquery ")
                    .append("ON eventImages.id = subquery.id ")
                    .append("ORDER BY event_id DESC;");

            String stringSQL = sb.toString();
            ResultSet result = ConnectDB.getConnector().executeRaw(stringSQL);

            while (result.next()) {
                if (result.getString("image") != null) {
                    byte[] byteImageBase64 = Base64.getDecoder().decode(result.getString("image"));
                    listEventImages.add(new ImageIcon(byteImageBase64));
                } else {
                    listEventImages.add(null);
                }
            }
        } catch (Exception ex) {ex.printStackTrace();}

        return listEventImages;
    }
}
