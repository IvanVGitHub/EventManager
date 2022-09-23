package com.ivank.fraui.settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class AppConfig {
    private static String configFile = "src/main/resources/config.json";
    private static AppConfig instance;

    public static AppConfig getInstance() {
        if (instance == null)
            loadConfig();

        return instance;
    }

    //fields
    private ArrayList<String> pluginsIsSlct = new ArrayList<>();
    private ArrayList<String> camerasIsSlct = new ArrayList<>();

    public ArrayList<SettingsCamera> getCameras() {
        return cameras;
    }

    private ArrayList<SettingsCamera> cameras = new ArrayList<>();
    private SettingsConnection connection = new SettingsConnection();
    private int eventLimit;

    public SettingsCamera getCameraByName(String name){
        for (SettingsCamera c: getCameras()) {
            if(c.name.equals(name))
                return c;
        }
        return null;
    }
    public SettingsCamera getCameraById(int id){
        for (SettingsCamera c: getCameras()) {
            if(c.id == id)
                return c;
        }
        return null;
    }

    public ArrayList<String> getPluginsIsSlct() {
        return pluginsIsSlct;
    }
    public void setPluginsIsSlct(int idCamera, ArrayList<String> pluginsIsSlct) {
        SettingsCamera sc = this.getCameraById(idCamera);
        if(sc != null)
            sc.plugins = pluginsIsSlct;
    }
    public ArrayList<String> getCamerasIsSlct() {
        return camerasIsSlct;
    }
    public void setCamerasIsSlct(ArrayList<String> camerasIsSlct) {
        this.camerasIsSlct = camerasIsSlct;
    }
    public ArrayList<SettingsCamera> getCameraSettings() {
        return cameras;
    }
    public SettingsConnection getConnection() {
        return connection;
    }
    public int getEventLimit() {
        return eventLimit;
    }
    public void setEventLimit(int eventLimit) {
        this.eventLimit = eventLimit;
    }
    //end of fields

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
