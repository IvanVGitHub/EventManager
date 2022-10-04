package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Base64;

public class QueryNEWEventImages {
    private static ArrayList<ImageIcon> listEventImagesCamera = new ArrayList<>();

    //список моделей конкретного событая в таблице eventImages
    public static ResultSet<ModelNEWEventImages> getListModelEventImages(int event_id) {
        ResultSet<ModelNEWEventImages> result = null;

        try {
            QueryBuilder<ModelNEWEventImages> query = ConnectDB.getConnector().query(ModelNEWEventImages.class).where("event_id", event_id);
            result = query.get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    //список изображений конкретного события
    public static ArrayList<ImageIcon> getListEventImages(int event_id) {
        listEventImagesCamera.clear();

        for (ModelNEWEventImages event : getListModelEventImages(event_id)) {
            byte[] byteImageBase64 = Base64.getDecoder().decode(event.image);
            listEventImagesCamera.add(new ImageIcon(byteImageBase64));
        }

        return listEventImagesCamera;
    }
}
