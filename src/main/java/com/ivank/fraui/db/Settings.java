package com.ivank.fraui.db;

import com.bedivierre.eloquent.model.DBModel;

public class Settings extends DBModel {
    public String id;
    public String code;
    public String data;

    @Override
    public String getTable() {
        return "settings";
    }

    public static Settings getByCode(String code) {
        try {
            return ConnectDB.getConnector().where(Settings.class, "code", code).first();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //получить настройку по имени image_options
    public static String getLabelOptions() {
        Settings defaultImage = getByCode("image_options");
        return defaultImage == null ? "" : defaultImage.data;
    }

    //получить настройку по имени image_options
    public static String getButtonAllImgEvents() {
        Settings defaultImage = getByCode("image_all_img_events");
        return defaultImage == null ? "" : defaultImage.data;
    }

    //получить настройку по имени default_image
    public static String getImageDefault() {
        Settings defaultImage = getByCode("image_default_event");
        return defaultImage == null ? "" : defaultImage.data;
    }
}
