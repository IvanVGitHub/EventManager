package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;
import com.ivank.fraui.settings.AppConfig;
import com.ivank.fraui.utils.MyDB;

import java.util.ArrayList;

public class QueryNEWEvent {
    private static ArrayList<ModelNEWEvent> listModelEventCamera = new ArrayList<>();
    private static ArrayList<ModelCamera> listCameras = new ArrayList<>();

    public static ArrayList<ModelCamera> getListModelCameras() {
        listCameras.clear();

        try {
            ArrayList<String> camerasIsSlct = AppConfig.getInstance().getCamerasIsSlct();
            for (String event : camerasIsSlct)
            {
                //query to MYSQL
                ResultSet<ModelCamera> result = MyDB.cameraQuery("camera_name", event).get();

                listCameras.addAll(result);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listCameras;
    }

    public static ArrayList<ModelNEWEvent> getListModelEventsCamera(int i) {
        listModelEventCamera.clear();

        try {
            //query to MYSQL
            ModelCamera camera = getListModelCameras().get(i);
            QueryBuilder<ModelNEWEvent> query = ConnectDB.getConnector().query(ModelNEWEvent.class);
            ResultSet<ModelNEWEvent> result = query.get();

            listModelEventCamera.addAll(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listModelEventCamera;
    }
}
