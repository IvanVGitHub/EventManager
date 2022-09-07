package com.ivank.fraui.db;

import com.bedivierre.eloquent.model.DBModel;

public class ModelCameraEvent extends DBModel {
    public int id;
    public String uuid;
    public String camera_id;
    public String photo;


    @Override
    public String getTable() {
        return "event";
    }



    public ModelCameraEvent() {
    }

}
