package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;

import java.util.ArrayList;

public class QueryCameras {
    private static ArrayList<ModelCamera> listNamesCameras = new ArrayList<>();
    public static ArrayList<ModelCamera> getListNamesCameras() {
        listNamesCameras.clear();

        try {
            //query to MYSQL
            QueryBuilder<ModelCamera> query = ConnectDB.getConnector().query(ModelCamera.class);
            ResultSet<ModelCamera> result = query.get();
            listNamesCameras.addAll(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listNamesCameras;
    }
    public static void setListNamesCameras(ArrayList<ModelCamera> listNamesCameras) {
        QueryCameras.listNamesCameras = listNamesCameras;
    }

    private static ArrayList<String> listEventCamera = new ArrayList<>();
    public static ArrayList<String> getListEventCamera(int i) {
        listEventCamera.clear();

        try {
            //query to MYSQL
            ModelCamera camera = listNamesCameras.get(i);
            QueryBuilder<ModelEvent> q = ConnectDB.getConnector().query(ModelEvent.class);
            ResultSet<ModelEvent> result = q.where("camera_id", camera.id).get();
            for (ModelEvent event : result) {
                listEventCamera.add(String.valueOf(event.id));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listEventCamera;
    }
    public static void setListEventCamera(ArrayList<String> listEventCamera) {
        QueryCameras.listEventCamera = listEventCamera;
    }
}
