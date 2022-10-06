package com.ivank.fraui.db;

import com.bedivierre.eloquent.DB;
import com.ivank.fraui.settings.AppConfig;

public class ConnectDB {
    static DB connector;

    public static DB getConnector() {
        if(connector == null)
            init();
        try{
            if(!connector.isConnected())
                connector.connect();
        } catch (Exception ex) {ex.printStackTrace();}

        return connector;
    }

    public static void init(){
        connector = new DB(
                AppConfig.getInstance().getConnection().host,
                AppConfig.getInstance().getConnection().database,
                AppConfig.getInstance().getConnection().username,
                AppConfig.getInstance().getConnection().password
        );
    }

    private ConnectDB() {
    }
}
