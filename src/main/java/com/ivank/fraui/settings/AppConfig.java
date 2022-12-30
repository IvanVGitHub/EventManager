package com.ivank.fraui.settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class AppConfig {
    //приложение было разработано под разрешение 1920*1080, поэтому под другие разрешения подстраиваем в коэффициенте
    private static double scale = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 1080;
//    private static double scale = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 500;
    public static double getScale() {return scale;}

    private static String configFile = "config.json";
    private static AppConfig instance;

    public static AppConfig getInstance() {
        if (instance == null)
            loadConfig();

        return instance;
    }

    //поля
    private ArrayList<String> listSelectedCameras = new ArrayList<>();
    private ArrayList<SettingsCamera> listCameras = new ArrayList<>();
    private SettingsConnection connection = new SettingsConnection();
    private SettingsLabelSize labelSize = new SettingsLabelSize();
    public SettingsLabelSize getLabelSize() {
        return labelSize;
    }
    public void setLabelSize(int width, int height) {
        this.labelSize.width = width;
        this.labelSize.height = height;
    }
    private int eventLimit;
    public ArrayList<String> getListSelectedCameras() {
        //загружаем актуальные json данные
        loadConfig();

        return listSelectedCameras;
    }
    public void setListSelectedCameras(ArrayList<String> listSelectedCameras) {
        this.listSelectedCameras = listSelectedCameras;
    }
    public SettingsConnection getConnection() {
        return connection;
    }
    public int getEventLimit() {
        //загружаем актуальные json данные
        loadConfig();

        return eventLimit;
    }
    public void setEventLimit(int eventLimit) {
        this.eventLimit = eventLimit;
    }
    //конец полей

    public ArrayList<SettingsCamera> getListCameras() {
        //загружаем актуальные json данные
        loadConfig();

        return listCameras;
    }

    public SettingsCamera getCameraByName(String name) {
        for (SettingsCamera c : getListCameras()) {
            if (c.name.equals(name))
                return c;
        }

        return null;
    }

    public SettingsCamera getCameraById(int id) {
        for (SettingsCamera c : getListCameras()) {
            if (c.id == id)
                return c;
        }

        return null;
    }

    public ArrayList<String> getListSelectedPlugins(int idCamera) {
        //загружаем актуальные json данные
        loadConfig();

        SettingsCamera c = this.getCameraById(idCamera);
        if (c != null)
            return c.plugins;
        return null;
    }

    //устанавливает статус установленного/снятого флага с чекбокса
    public void setListSelectedPlugins(int idCamera, ArrayList<String> listSelectedPlugins) {
        for (SettingsCamera settings:listCameras) {
            if(settings.id == idCamera){
                settings.plugins = listSelectedPlugins;
                break;
            }
        }
    }

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
        } catch (Exception ex) {ex.printStackTrace();}

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
        } catch (Exception ex) {ex.printStackTrace();}
    }
}
