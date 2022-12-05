package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.ivank.fraui.utils.UtilsAny;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;

public class QueryEventImages {
    //список изображений события (event)
    private static ArrayList<ImageIcon> listEventImages = new ArrayList<>();

    //список моделей конкретного события
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
            String stringSQL = "SELECT image FROM eventImages " +
                    "WHERE id IN (" +
                    "SELECT id FROM eventImages " +
                    "WHERE event_id = " + event_id + ");";

            UtilsAny.logHeapSize("\n\n========\nBefore loading from mysql");
            ResultSet result = ConnectDB.getConnector().executeRaw(stringSQL);

            while (result.next()) {
                byte[] byteImageBase64 = Base64.getDecoder().decode(result.getString("image"));
                UtilsAny.logHeapSize("After transfer Base64 to byte[]");
                listEventImages.add(new ImageIcon(byteImageBase64));
                UtilsAny.logHeapSize("After transfer from byte to ImageIcon");
            }
            UtilsAny.logHeapSize("========\nAfter transfer of all pictures from Base64");
        } catch (Exception ex) {ex.printStackTrace();}

        return listEventImages;
    }

    //список первых изображений из списка event_id (id событий)
    public static ArrayList<ImageIcon> getListEventFirstImages(ArrayList<Integer> listIndexEventsId) {
        listEventImages.clear();
        if (listIndexEventsId.isEmpty())
            return listEventImages;

        try {
            String values = StringUtils.join(listIndexEventsId.toArray(), ", ");

            String stringSQL = "SELECT image FROM eventImages " +
                    "INNER JOIN (" +
                    "SELECT MIN(id) AS id FROM eventImages " +
                    "WHERE event_id IN (" + values + ") " +
                    "GROUP BY event_id) " +
                    "subquery ON eventImages.id = subquery.id " +
                    "ORDER BY event_id DESC;";

            ResultSet result = ConnectDB.getConnector().executeRaw(stringSQL);

            while (result.next()) {
                if (result.getString("image") != null) {
                    byte[] byteImage = Base64.getDecoder().decode(result.getString("image"));
                    listEventImages.add(new ImageIcon(byteImage));
                } else {
                    listEventImages.add(null);
                }
            }
        } catch (Exception ex) {ex.printStackTrace();}

        return listEventImages;
    }
}
