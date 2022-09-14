package com.ivank.fraui.db;

import com.bedivierre.eloquent.DB;
import com.ivank.fraui.AppConfig;

public class ConnectDB {
    static DB connector;

    public static DB getConnector() {
        return connector;
    }

    public static void init(){
        connector = new DB(AppConfig.host, AppConfig.database, AppConfig.username, AppConfig.password);
    }

    private ConnectDB() {
    }
}
