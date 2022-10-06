package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;
import com.ivank.fraui.settings.AppConfig;
import com.ivank.fraui.utils.MyDB;

import java.util.ArrayList;

public class QueryNEWCamera {
    //список моделей всех камер
    private static ArrayList<ModelCamera> listModelCameras = new ArrayList<>();
    //список моделей камер, сохранённых в локальных настроках приложения, т. е. их выбрали для отслеживания событий
    private static ArrayList<ModelCamera> listModelCamerasIsSelect = new ArrayList<>();
    //список имён всех камер
    private static ArrayList<String> listCameraName = new ArrayList<>();
    //список имён камер, сохранённых в локальных настроках приложения, т. е. их выбрали для отслеживания событий
    private static ArrayList<String> listCameraNameIsSelect = new ArrayList<>();

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

    public static ArrayList<ModelCamera> getListModelCamerasIsSelect() {
        listModelCamerasIsSelect.clear();

        try {
            ArrayList<String> camerasIsSlct = AppConfig.getInstance().getCamerasIsSlct();
            for (String event : camerasIsSlct) {
                //query to MYSQL
                ResultSet<ModelCamera> result = MyDB.cameraQuery("camera_name", event).get();

                listModelCamerasIsSelect.addAll(result);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listModelCamerasIsSelect;
    }

    //список имён всех камер
    public static ArrayList<String> getListCameraName() {
        listCameraName.clear();

        try {
            for (ModelCamera event : getListModelCameras()) {
                listCameraName.add(String.valueOf(event.camera_name));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listCameraName;
    }

    //список имён камер, сохранённых в локальных настроках приложения, т. е. их выбрали для отслеживания событий
    public static ArrayList<String> getListCameraNameIsSelect() {
        listCameraNameIsSelect.clear();

        try {
            for (ModelCamera event : getListModelCameras()) {
                listCameraNameIsSelect.add(String.valueOf(event.camera_name));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listCameraNameIsSelect;
    }
}
