package com.ivank.fraui.db;

import com.bedivierre.eloquent.QueryBuilder;
import com.bedivierre.eloquent.ResultSet;
import com.ivank.fraui.settings.AppConfig;

import java.util.ArrayList;

public class QueryCamera {
    //список моделей всех камер
    private static ArrayList<ModelCamera> listModelCameras = new ArrayList<>();
    //список моделей камер, сохранённых в локальных настройках приложения, т. е. их выбрали для отслеживания событий
    private static ArrayList<ModelCamera> listModelCamerasIsSelect = new ArrayList<>();
    //список имён всех камер
    private static ArrayList<String> listCameraName = new ArrayList<>();
    //список имён камер, сохранённых в локальных настройках приложения, т. е. их выбрали для отслеживания событий
    private static ArrayList<String> listCameraNameIsSelect = new ArrayList<>();

    //список моделей всех камер в таблице camera
    public static ArrayList<ModelCamera> getListModelCameras() {
        listModelCameras.clear();

        try {
            QueryBuilder<ModelCamera> query = ConnectDB.getConnector().query(ModelCamera.class);
            listModelCameras = query.get();
        } catch (Exception ex) {ex.printStackTrace();}

        return listModelCameras;
    }

    //список моделей камер, которые пометили для отслеживания
    public static ArrayList<ModelCamera> getListModelCamerasIsSelect() {
        listModelCamerasIsSelect.clear();

        try {
            ArrayList<String> camerasIsSlct = AppConfig.getInstance().getListSelectedCameras();
            for (String item : camerasIsSlct) {
                ResultSet<ModelCamera> result = ConnectDB.getConnector().query(ModelCamera.class)
                        .where("camera_name", item)
                        .get();
                listModelCamerasIsSelect.addAll(result);
            }
        } catch (Exception ex) {ex.printStackTrace();}

        return listModelCamerasIsSelect;
    }

    //модель камеры по её id
    public static ModelCamera getModelCameraById(int id) {
        try {
            String stringSQL = "SELECT * FROM camera WHERE id = " + id;
            java.sql.ResultSet result = ConnectDB.getConnector().executeRaw(stringSQL);

            ModelCamera modelCamera = new ModelCamera();
            while (result.next()) {
                modelCamera.id = result.getInt("id");
                modelCamera.host = result.getString("host");
                modelCamera.path = result.getString("path");
                modelCamera.username = result.getString("username");
                modelCamera.password = result.getString("password");
                modelCamera.width = result.getInt("width");
                modelCamera.height = result.getInt("height");
                modelCamera.framerate = result.getInt("framerate");
                modelCamera.camera_name = result.getString("camera_name");
            }

            return modelCamera;
        } catch (Exception ex) {ex.printStackTrace();}

        return null;
    }

    //список имён всех камер
    public static ArrayList<String> getListCameraName() {
        listCameraName.clear();

        try {
            for (ModelCamera item : getListModelCameras()) {
                listCameraName.add(String.valueOf(item.camera_name));
            }
        } catch (Exception ex) {ex.printStackTrace();}

        return listCameraName;
    }

    //список имён камер, сохранённых в локальных настройках приложения, т. е. их выбрали для отслеживания событий
    public static ArrayList<String> getListCameraNameIsSelect() {
        listCameraNameIsSelect.clear();

        try {
            for (ModelCamera item : getListModelCamerasIsSelect()) {
                listCameraNameIsSelect.add(String.valueOf(item.camera_name));
            }
        } catch (Exception ex) {ex.printStackTrace();}

        return listCameraNameIsSelect;
    }
}
