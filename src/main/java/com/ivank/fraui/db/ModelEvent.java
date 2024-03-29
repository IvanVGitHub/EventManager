package com.ivank.fraui.db;

import com.bedivierre.eloquent.model.DBModel;
import java.sql.Timestamp;

public class ModelEvent extends DBModel {
    public int id;
    public String uuid_session;
    public String uuid;
    public int camera_id;
    public String plugin_id;
    public String data;
    public String time;
    public int processing;

    @Override
    public String getTable() {
        return "event";
    }

    public ModelEvent() {
    }
}
