package com.ivank.fraui.db;

import com.bedivierre.eloquent.annotations.NotSqlField;
import com.bedivierre.eloquent.model.DBModel;

public class TestModel extends DBModel {
    public int id;
    public String name;
    public String description;
    public String address;

    @NotSqlField
    public TestModel d;

    @Override
    public String getTable() {
        return "Test001";
    }
    public static void test(TestModel t){
        t.name = "fff";
    }
    public TestModel(String address){

        this.address = address;
    }

    public TestModel(){
    }

    public TestModel(TestModel another){
        this.name = another.name;
        this.description = another.description;
        this.address = another.address;
    }

}
