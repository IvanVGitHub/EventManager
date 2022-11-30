package com.ivank.fraui.settings;

import com.bedivierre.eloquent.model.DBModel;
import com.ivank.fraui.db.ConnectDB;

public class SettingsDefault extends DBModel {
    public String id;
    public String code;
    public String data;

    @Override
    public String getTable() {
        return "settings";
    }

    public static SettingsDefault getByCode(String code) {
        try {
            return ConnectDB.getConnector().where(
                    SettingsDefault.class,
                    "code",
                    code
            ).first();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    //получить настройку по имени image_options
    public static String getImageOptions() {
        SettingsDefault defaultImage = getByCode("image_options");

        return defaultImage == null ? "" : defaultImage.data;
    }

    //получить настройку по имени image_all_img_events
    public static String getImageAllImgEvents() {
        SettingsDefault defaultImage = getByCode("image_all_img_events");

        return defaultImage == null ? "" : defaultImage.data;
    }

    //получить настройку по имени image_default_event
    public static String getImageDefault() {
        SettingsDefault defaultImage = getByCode("image_default_event");

        return defaultImage == null ? "" : defaultImage.data;
    }

    //получить настройку по имени image_live_view
    public static String getImageLiveView() {
        SettingsDefault defaultImage = getByCode("image_live_view");

        return defaultImage == null ? "" : defaultImage.data;
    }

    //получить настройку по имени image_unwrap
    public static String getImageUnwrap() {
        SettingsDefault defaultImage = getByCode("image_unwrap");

        return defaultImage == null ? "" : defaultImage.data;
    }
}
