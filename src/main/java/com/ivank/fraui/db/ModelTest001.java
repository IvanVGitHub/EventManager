package com.ivank.fraui.db;

import com.bedivierre.eloquent.annotations.NotSqlField;
import com.bedivierre.eloquent.model.DBModel;

public class ModelTest001 extends DBModel {
    public int id;
    public String name;
    public String description;
    public String address;

    @NotSqlField
    public ModelTest001 d;

    @Override
    public String getTable() {
        return "test001";
    }

    public static void test(ModelTest001 t) {
        t.name = "fff";
    }

    public ModelTest001(String address) {
        this.address = address;
    }

    public ModelTest001() {
    }

    public ModelTest001(ModelTest001 another) {
        this.name = another.name;
        this.description = another.description;
        this.address = another.address;
    }
}
