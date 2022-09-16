package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;

import java.util.ArrayList;

public class QueryCameras {
    private static ArrayList<ModelCamera> listCameras = new ArrayList<>();
    private static ArrayList<String> listEventCamera = new ArrayList<>();
    public static ArrayList<ModelCamera> getListCameras() {
        listCameras.clear();

        try {
            //query to MYSQL
            QueryBuilder<ModelCamera> query = ConnectDB.getConnector().query(ModelCamera.class);
            ResultSet<ModelCamera> result = query.get();
            listCameras.addAll(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listCameras;
    }

    public static void setListCameras(ArrayList<ModelCamera> listCameras) {
        QueryCameras.listCameras = listCameras;
    }
    public static ArrayList<String> getListEventCamera(int i) {
        listEventCamera.clear();

        try {
            //query to MYSQL
            ModelCamera camera = listCameras.get(i);
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
