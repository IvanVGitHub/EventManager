package com.ivank.fraui.db;

import com.bedivierre.eloquent.model.DBModel;

public class ModelPlugins extends DBModel {
    public String id;
    public String name;
    public String description;
    public String params;

    @Override
    public String getTable() {
        return "plugins";
    }

    public ModelPlugins() {
    }
}
