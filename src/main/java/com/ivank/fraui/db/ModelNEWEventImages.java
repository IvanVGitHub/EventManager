package com.ivank.fraui.db;

import com.bedivierre.eloquent.model.DBModel;

public class ModelNEWEventImages extends DBModel {
    public int id;
    public String uuid;
    public int event_id;
    public String image;

    @Override
    public String getTable() {
        return "NEWeventImages";
    }

    public ModelNEWEventImages() {
    }
}
