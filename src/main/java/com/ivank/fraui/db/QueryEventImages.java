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

    //список изображений конкретного события (все кадры)
    public static ArrayList<ImageIcon> getListEventImages(int event_id) {
        listEventImages.clear();

        try {
            String stringSQL = "SELECT image FROM eventImages " +
                    "WHERE id IN (" +
                    "SELECT id FROM eventImages " +
                    "WHERE event_id = " + event_id + ");";

            ResultSet result = ConnectDB.getConnector().executeRaw(stringSQL);

            while (result.next()) {
                byte[] byteImageBase64 = Base64.getDecoder().decode(result.getString("image"));
                listEventImages.add(new ImageIcon(byteImageBase64));
            }
        } catch (Exception ex) {ex.printStackTrace();}

        return listEventImages;
    }

    //список изображений конкретного события (каждый n-кадр, реализация с "простым" запросом)
    /*public static ArrayList<ImageIcon> getListEventImages(int event_id, int step) {
        listEventImages.clear();

        try {
            UtilsAny.logHeapSize("\n\n========\nBefore loading from mysql");

            String stringSQL = "SELECT id FROM eventImages WHERE event_id = " + event_id;
            ResultSet result = ConnectDB.getConnector().executeRaw(stringSQL);

            ArrayList<String> ids = new ArrayList<>();
            int counter = 1;
            while (result.next()) {
                if (counter != step) {
                    counter++;
                    continue;
                }
                ids.add(result.getString("id"));
                counter = 1;
            }

            StringBuilder query = new StringBuilder();
            query.append("SELECT id, image FROM eventImages WHERE id IN (");
            query.append(String.join(", ", ids));
            query.append(");");
            stringSQL = query.toString();

            result = ConnectDB.getConnector().executeRaw(stringSQL);

            while (result.next()) {
                byte[] byteImageBase64 = Base64.getDecoder().decode(result.getString("image"));
                UtilsAny.logHeapSize("After transfer Base64 to byte[]");
                listEventImages.add(new ImageIcon(byteImageBase64));
                UtilsAny.logHeapSize("After transfer from byte to ImageIcon");
            }
            //очищаем память
            ids.clear();
            UtilsAny.logHeapSize("========\nAfter transfer of all pictures from Base64");
        } catch (Exception ex) {ex.printStackTrace();}

        return listEventImages;
    }*/

    //список изображений конкретного события (каждый n-кадр, реализация со "сложным" запросом)
    public static ArrayList<ImageIcon> getListEventImages(int event_id, int step) {
        listEventImages.clear();

        try {
            String stringSQL = "WITH CTE_Name (row_num, id) AS (" +
            "SELECT ROW_NUMBER() OVER () row_num, id FROM eventImages " +
            "WHERE event_id = " + event_id + ") " +
            "SELECT image FROM eventImages " +
            "WHERE id IN (SELECT id FROM CTE_Name WHERE row_num % " + step + " = 1);";

            ResultSet result = ConnectDB.getConnector().executeRaw(stringSQL);

            while (result.next()) {
                byte[] byteImageBase64 = Base64.getDecoder().decode(result.getString("image"));
                listEventImages.add(new ImageIcon(byteImageBase64));
            }
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
