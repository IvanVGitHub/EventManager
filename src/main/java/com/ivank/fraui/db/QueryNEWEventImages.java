package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;
import com.ivank.fraui.settings.SettingsDefault;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Base64;

public class QueryNEWEventImages {
    //список изображений события (event)
    private static ArrayList<ImageIcon> listEventImages = new ArrayList<>();

    //список моделей конкретного событая в таблице eventImages
    public static ArrayList<ModelNEWEventImages> getListModelEventImages(int event_id) {
        try {
            QueryBuilder<ModelNEWEventImages> query = ConnectDB.getConnector().query(ModelNEWEventImages.class).where("event_id", event_id);

            return query.get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    //первое изображение события
    public static ImageIcon getEventFirstImage(int event_id) {
        try {
            ModelNEWEventImages result = ConnectDB.getConnector().query(ModelNEWEventImages.class).where(
                    "event_id",
                    event_id
            ).first();

            byte[] byteImageBase64 = Base64.getDecoder().decode(result.image);

            return new ImageIcon(byteImageBase64);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    //список изображений конкретного события
    public static ArrayList<ImageIcon> getListEventImages(int event_id) {
        listEventImages.clear();

        for (ModelNEWEventImages event : getListModelEventImages(event_id)) {
            byte[] byteImageBase64 = Base64.getDecoder().decode(event.image);
            listEventImages.add(new ImageIcon(byteImageBase64));
        }

        return listEventImages;
    }
}
