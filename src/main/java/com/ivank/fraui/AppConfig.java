package com.ivank.fraui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ivank.fraui.settings.CameraSettings;
import com.ivank.fraui.settings.ConnectionSettings;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class AppConfig {
    private static String configFile = "src/main/resources/config.json";

    public static AppConfig getInstance() {
        if (instance == null)
            loadConfig();
        return instance;
    }
    private static AppConfig instance;

    //fields
    private ArrayList<CameraSettings> camera = new ArrayList<>();
    private ArrayList<String> camerasIsSlct = new ArrayList<>();
    private ConnectionSettings connection = new ConnectionSettings();
    private int eventLimit;

    public ArrayList<CameraSettings> getCamera() {
        return camera;
    }
    public ConnectionSettings getConnection() {
        return connection;
    }
    public ArrayList<String> getCamerasView() {
        return camerasIsSlct;
    }
    public void setCamerasView(ArrayList<String> camerasIsSlct) {
        this.camerasIsSlct = camerasIsSlct;
    }
    public int getEventLimit() {
        return eventLimit;
    }
    public void setEventLimit(int eventLimit) {
        this.eventLimit = eventLimit;
    }
    ///end of fields

    public static AppConfig loadConfig() {
        return loadConfig(true);
    }
    public static AppConfig loadConfig(boolean reload) {
        try {
            File file = new File(configFile);
            Gson gson = new Gson();
            if (file.exists()) {
                AppConfig conf = gson.fromJson(new FileReader(file), AppConfig.class);
                if (reload)
                    instance = conf;
                return conf;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new AppConfig();
    }
    public static void saveConfig() {
        if (instance == null)
            return;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(instance);
            File file = new File(configFile);
            FileWriter wr = new FileWriter(file);
            wr.write(json);
            wr.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
