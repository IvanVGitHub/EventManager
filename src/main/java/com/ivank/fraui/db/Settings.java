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

    public static Settings getByCode(String code){
        try {
            return ConnectDB.getConnector().where(Settings.class, "code", code).first();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //получить настройку по имени default_image
    public static String getDefaultImage(){
        Settings defaultImage = getByCode("default_image");
        return defaultImage == null ? "" : defaultImage.data;
    }
}
