package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;

import java.util.ArrayList;

public class QueryCameras {
    public static ArrayList<ModelCamera> listNamesCameras = new ArrayList<>();
    public static ArrayList<String> listEventCamera = new ArrayList<>();
    public static void listCameras() {
        try {
            //query to MYSQL
            QueryBuilder<ModelCamera> query = ConnectDB.getConnector().query(ModelCamera.class);
            ResultSet<ModelCamera> result1 = query.get();
            listNamesCameras.clear();
            listNamesCameras.addAll(result1);

            //query to MYSQL
            ModelCamera camera = listNamesCameras.get(0);
            QueryBuilder<ModelEvent> q = ConnectDB.getConnector().query(ModelEvent.class);
            ResultSet<ModelEvent> result2 = q.where("camera_id", camera.id).get();
            listEventCamera.clear();
            for (ModelEvent id : result2) {
                listEventCamera.add(String.valueOf(id));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
