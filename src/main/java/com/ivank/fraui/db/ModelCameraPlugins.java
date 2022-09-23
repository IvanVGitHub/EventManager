package com.ivank.fraui.db;

import com.bedivierre.eloquent.model.DBModel;

public class ModelCameraPlugins extends DBModel {
    public String id;
    public String camera_id;
    public String plugin_id;
    public String params;

    @Override
    public String getTable() {
        return "cameraPlugins";
    }

    public ModelCameraPlugins() {
    }
}
