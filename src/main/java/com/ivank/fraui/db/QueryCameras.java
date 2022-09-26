package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;
import com.ivank.fraui.settings.AppConfig;
import com.ivank.fraui.utils.MyDB;

import java.util.ArrayList;

public class QueryCameras {
    private static ArrayList<ModelCamera> listCameras = new ArrayList<>();
    private static ArrayList<String> listEventCamera = new ArrayList<>();
    private static ArrayList<String> listCameraName = new ArrayList<>();
    private static ArrayList<String> listCameraNameALL = new ArrayList<>();

    public static ArrayList<ModelCamera> getListMdlCamerasALL() {
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

    public static ArrayList<ModelCamera> getListMdlCameras() {
        listCameras.clear();

        try {
            //query to MYSQL
            ArrayList<String> camerasIsSlct = AppConfig.getInstance().getCamerasIsSlct();
            for (String event : camerasIsSlct)
            {
                ResultSet<ModelCamera> result = MyDB.cameraQuery("camera_name", event).get();

                listCameras.addAll(result);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listCameras;
    }

    public static ArrayList<String> getListEventCamera(int i) {
        listEventCamera.clear();

        try {
            //query to MYSQL
            ModelCamera camera = getListMdlCameras().get(i);
            QueryBuilder<ModelEvent> q = ConnectDB.getConnector().query(ModelEvent.class);
            ResultSet<ModelEvent> result = q.where(
                    "camera_id",
                    camera.id
            ).get();
            for (ModelEvent event : result) {
                listEventCamera.add(String.valueOf(event.id));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listEventCamera;
    }

    public static ArrayList<String> getListCameraName() {
        listCameraName.clear();

        try {
            for (ModelCamera event : getListMdlCameras()) {
                listCameraName.add(String.valueOf(event.camera_name));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listCameraName;
    }

    public static ArrayList<String> getListCameraNameALL() {
        listCameraNameALL.clear();

        try {
            for (ModelCamera event : getListMdlCamerasALL()) {
                listCameraNameALL.add(String.valueOf(event.camera_name));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listCameraNameALL;
    }
}
