package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;

import java.util.ArrayList;

public class QueryPlugins {
    private static ArrayList<String> listPlaginId = new ArrayList<>();
    private static ArrayList<String> listPlaginsOfCamera = new ArrayList<>();

    public static ArrayList<String> getListPlaginsOfCamera(int idCamera) {
        try {
            //query to MYSQL
            QueryBuilder<ModelCameraPlugins> q = ConnectDB.getConnector().query(ModelCameraPlugins.class);
            ResultSet<ModelCameraPlugins> result = q.where(
                    "camera_id",
                    idCamera
            ).get();
            for (ModelCameraPlugins event : result) {
                listPlaginsOfCamera.add(String.valueOf(event.plugin_id));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listPlaginsOfCamera;
    }

    public static ArrayList<String> getListPlaginId() {
        listPlaginId.clear();

        try {
            //query to MYSQL
            QueryBuilder<ModelPlugins> query = ConnectDB.getConnector().query(ModelPlugins.class);
            ResultSet<ModelPlugins> result = query.get();
            for (ModelPlugins event : result) {
                listPlaginId.add(String.valueOf(event.id));
            }
        } catch (Exception ex) {
            //shows line with error in console
            ex.printStackTrace();
        }

        return listPlaginId;
    }
}
