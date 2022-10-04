package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;

import java.util.ArrayList;

public class QueryNEWEvent {
    private static ArrayList<ModelCamera> listModelCameras = new ArrayList<>();

    //список всех камер в таблице camera
    public static ArrayList<ModelCamera> getListModelCameras() {
        listModelCameras.clear();

        try {
            QueryBuilder<ModelCamera> query = ConnectDB.getConnector().query(ModelCamera.class);
            listModelCameras = query.get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listModelCameras;
    }

    //список моделей конкретной камеры в таблице event
    public static ResultSet<ModelNEWEvent> getModelEventsCamera(int idCamera) {
        try {
            QueryBuilder<ModelNEWEvent> query = ConnectDB.getConnector().query(ModelNEWEvent.class).where("camera_id", idCamera);

            return query.get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
