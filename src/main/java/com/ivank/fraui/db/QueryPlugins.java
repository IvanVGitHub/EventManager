package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;

import java.util.ArrayList;

public class QueryPlugins {
    private static ArrayList<String> listPluginId = new ArrayList<>();
    private static ArrayList<String> listPluginsOfCamera = new ArrayList<>();

    public static ArrayList<String> getListPluginsOfCamera(int idCamera) {
        try {
            //query to MYSQL
            QueryBuilder<ModelCameraPlugins> q = ConnectDB.getConnector().query(ModelCameraPlugins.class);
            ResultSet<ModelCameraPlugins> result = q.where(
                    "camera_id",
                    idCamera
            ).get();
            for (ModelCameraPlugins event : result) {
                listPluginsOfCamera.add(String.valueOf(event.plugin_id));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listPluginsOfCamera;
    }

    public static ArrayList<String> getListPluginIdALL() {
        listPluginId.clear();

        try {
            //query to MYSQL
            QueryBuilder<ModelPlugins> query = ConnectDB.getConnector().query(ModelPlugins.class);
            ResultSet<ModelPlugins> result = query.get();
            for (ModelPlugins event : result) {
                listPluginId.add(String.valueOf(event.id));
            }
        } catch (Exception ex) {
            //shows line with error in console
            ex.printStackTrace();
        }

        return listPluginId;
    }
}
