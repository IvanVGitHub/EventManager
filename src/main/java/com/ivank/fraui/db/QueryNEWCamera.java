package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;

import java.util.ArrayList;

public class QueryNEWCamera {
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
}
