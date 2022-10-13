package com.ivank.fraui.db;

import com.bedivierre.eloquent.DB;
import com.ivank.fraui.settings.AppConfig;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
    static DB connector;

    private ConnectDB() {
    }

    public static DB getConnector() {
        if(connector == null)
            init();
        try{
            if(!connector.isConnected())
                connector.connect();
        } catch (Exception ex) {ex.printStackTrace();}

        return connector;
    }

    public static void init() {
        connector = new DB(
                AppConfig.getInstance().getConnection().host,
                AppConfig.getInstance().getConnection().database,
                AppConfig.getInstance().getConnection().username,
                AppConfig.getInstance().getConnection().password
        );
    }

    public static Connection getConnectorClearSQL() {
        StringBuilder connectionUrl;
        Connection connection = null;

        try {
            connectionUrl = new StringBuilder(
                    "jdbc:mysql://" +
                            AppConfig.getInstance().getConnection().host +
                            ":3306/" +
                            AppConfig.getInstance().getConnection().database
            );
            connection = DriverManager.getConnection(
                    String.valueOf(connectionUrl),
                    AppConfig.getInstance().getConnection().username,
                    AppConfig.getInstance().getConnection().password
            );
        } catch (Exception ex) {ex.printStackTrace();}

        return connection;
    }
}
