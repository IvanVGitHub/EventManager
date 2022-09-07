package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;

import java.util.ArrayList;

public class QueryCameras {
    public static ArrayList<ModelCameras> listNamesCameras = new ArrayList<>();
    public static ArrayList<String> listEventCamera = new ArrayList<>();
    public static void listCameras() {
        try {
            //query to MYSQL
            QueryBuilder<ModelCameras> query = ConnectDB.getConnector().query(ModelCameras.class);
            ResultSet<ModelCameras> result1 = query.get();
            listNamesCameras.clear();
            listNamesCameras.addAll(result1);

            //query to MYSQL
            ModelCameras camera = listNamesCameras.get(0);
            QueryBuilder<ModelCameraEvent> q = ConnectDB.getConnector().query(ModelCameraEvent.class);
            ResultSet<ModelCameraEvent> result2 = q.where("camera_id", camera.id).get();
            listEventCamera.clear();
            for (ModelCameraEvent id : result2) {
                listEventCamera.add(String.valueOf(id));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
