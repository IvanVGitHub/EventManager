package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;

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
