package com.ivank.fraui.db;

import com.bedivierre.eloquent.annotations.NotSqlField;
import com.bedivierre.eloquent.model.DBModel;

public class ModelCameras extends DBModel {
    public int id;
    public String camera_name;
    public String address;
    public int width;
    public int height;

    @NotSqlField
    public ModelCameras d;

    @Override
    public String getTable() {
        return "camera";
    }

    public ModelCameras(String camera_name, int w, int h) {
        this.camera_name = camera_name;
        this.width = w;
        this.height = h;
    }

    public ModelCameras(String camera_name) {
        this(camera_name, 1024, 800);
    }

    public ModelCameras() {
    }

    public ModelCameras(ModelCameras another) {
        this.camera_name = another.camera_name;
    }
}
