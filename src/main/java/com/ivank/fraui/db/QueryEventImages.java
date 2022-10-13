package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Base64;

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

    //список изображений из нескольких событий
    public static ArrayList<ImageIcon> getListEventImages(ArrayList<Integer> listIndexEventsId) {
        listEventImages.clear();
        ModelEventImages result;

        try {
            for (int i = 0; i > listIndexEventsId.size(); i++) {
                result = ConnectDB.getConnector().query(ModelEventImages.class)
                        .where("event_id", listIndexEventsId.get(i))
                        .first();
                if (result != null) {
                    byte[] byteImageBase64 = Base64.getDecoder().decode(result.image);
                    listEventImages.add(new ImageIcon(byteImageBase64));
                } else {
                    listEventImages.add(null);
                }
            }
        } catch (Exception ex) {ex.printStackTrace();}

        return listEventImages;
    }
}
