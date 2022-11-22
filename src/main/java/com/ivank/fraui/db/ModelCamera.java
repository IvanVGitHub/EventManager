package com.ivank.fraui.db;

import com.bedivierre.eloquent.model.DBModel;

public class ModelCamera extends DBModel {
    public int id;
    public String host;
    public String path;
    public String username;
    public String password;
    public int width;
    public int height;
    public int framerate;
    public String camera_name;

    @Override
    public String getTable() {
        return "camera";
    }

    public ModelCamera(String camera_name, int w, int h) {
        this.camera_name = camera_name;
        this.width = w;
        this.height = h;
    }

    public ModelCamera() {
    }

    public ModelCamera(ModelCamera another) {
        this.camera_name = another.camera_name;
    }
}
